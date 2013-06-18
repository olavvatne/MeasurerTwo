package model;



import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import filter.ImageFilter;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

public class ImageFolderModel {
	private File[] files;
	private int index = 0;
	private boolean scaled = false;
	private BufferedImage img;
	

	
	public ImageFolderModel() {
		
	}
	
	
	
	public boolean openPicturesFile() {
		if(findPictureFiles()) {
			return true;
		}
		return false;
	}	
	
	
	public int getIndex() {
		return index;
	}



	public void setIndex(int index) {
		this.index = index;
	}

	
	
	public void iterateIndex(int iterateValue) {
		this.index += iterateValue;
	}
	
	
	
	public void readImg() {
		File file = this.files[this.index];
		try {
			this.img = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public int getImageCount() {
		return this.files.length;
	}
	
	
	
	public boolean isScaled() {
		return scaled;
	}



	public void setScaled(boolean scaled) {
		this.scaled = scaled;
	}
	
	
	
	public Date getDate(int index) {
		long date = files[index].lastModified();
		return new Date(date- (date%60000)); // fjerner sekundene ... like greitt da 15:01 vil føre til at man plasseres i 1600
	}
	
	
	
	public ImageIcon getScaledImage(int width, int height) {
		System.out.println(this.index);
		//File file = this.files[this.index];
		try {
			BufferedImage scaledImage = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB );
			Graphics2D gScaledImage = scaledImage.createGraphics();
			gScaledImage.setRenderingHint( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED );
			//BufferedImage img = ImageIO.read(file);
			Dimension dim = new Dimension(width, height);
			System.out.println(scaled);
			if(scaled) {
				dim = getScaledDimension(img.getWidth(), img.getHeight(), dim);
			}
			gScaledImage.drawImage( img,(int) ((width-dim.getWidth())/2),
					(int)((height-dim.getHeight())/2), (int)dim.getWidth(), (int)dim.getHeight(), null );   
			return new ImageIcon(scaledImage);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	public static Dimension getScaledDimension( int width,int height, Dimension boundary) {

	    int originalWidth = width;
	    int originalHeight = height;
	    int boundWidth = boundary.width;
	    int boundHeight = boundary.height;
	    int newWidth = 0;
	    int newHeight = 0;

	    // first check if we need to scale width
	    if (originalWidth > boundWidth) {
	        //scale width to fit
	        newWidth = boundWidth;
	        //scale height to maintain aspect ratio
	        newHeight = (newWidth*originalHeight)/originalWidth;
	    }

	    // then check if we need to scale even with the new height
	    if (newHeight > boundHeight) {
	        //scale height to fit instead
	        newHeight = boundHeight;
	        //scale width to maintain aspect ratio
	        newWidth = (newHeight*originalWidth)/originalHeight;
	    }

	    return new Dimension(newWidth, newHeight);
	}
	
	
	
	public boolean findPictureFiles() {
		File file;
		final JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new ImageFilter());


		int returnVal = fc.showDialog(null, "Open pictures");
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
			this.files = file.getParentFile().listFiles();
			return true;
		}
		//This is where a real application would open the file.
		if(returnVal == JFileChooser.CANCEL_OPTION) {
			return false;
		}

		return false;
	}
}
