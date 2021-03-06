package model;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.Date;
import filter.ImageFilter;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import measurer.Measurer;

public class ImageFolderModel {
	
	public static final int ZERO_SECONDS_INT = 0;
	private File[] files;
	private int index = 0;
	private boolean scaled = false;
	private BufferedImage img;
	private Dimension imgSize = Toolkit.getDefaultToolkit().getScreenSize();
	private PropertyChangeSupport pcs;
	
	public ImageFolderModel() {
		pcs = new PropertyChangeSupport(this);
	}
	
	
	
	public boolean openPicturesFolder() {
		if(findPictureFiles("Open pictures")) {
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

	
	private void iterateIndex(int iterateValue) {
		this.index += iterateValue;
	}
	
	
	
	private static BufferedImage readImg(File file) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;
	}
	
	private void readIconInBackground(final File file) {
		SwingWorker<ImageIcon, Void> imageTask = new SwingWorker<ImageIcon, Void>() {

			@Override
			protected ImageIcon doInBackground() throws Exception {
				return getScaledImage(imgSize, readImg(file));
			}
			
			public void done() {
				ImageIcon icon = null;
				try {
					icon = get();
				} catch (Exception e) {
					// TODO: handle exception
				}
				//imgIcon = icon;
				if(icon != null) {
					pcs.firePropertyChange(Measurer.IMAGE, null, icon);
				}
				
			}
		};
		imageTask.execute();
	}
	
	
	public int getImageCount() {
		return this.files.length;
	}
	
	
	public void setImageSize(Dimension imgSize) {
		if(imgSize != null) {
			this.imgSize = imgSize;
			/*if(this.files != null) {
				readIconInBackground(this.files[this.index]);
				//sender oppdatert bilde til stuff
			}
			*/
		}
		
	}
	
	
	public boolean isScaled() {
		return scaled;
	}


	public void setScaled(boolean scaled) {
		this.scaled = scaled;
		readIconInBackground(this.files[this.index]);
	}
	
	
	public Date getDate(int index) {
		Metadata metadata = null;
		try {
			metadata = ImageMetadataReader.readMetadata(files[index]);
		} catch (ImageProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(metadata != null) {
			ExifSubIFDDirectory directory = metadata.getDirectory(ExifSubIFDDirectory.class);
			Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
			date.setSeconds(ZERO_SECONDS_INT);
			return date;
		}
		
		long date = files[index].lastModified();
		return new Date(date- (date%60000)); // fjerner sekundene ... like greitt da 15:01 vil f�re til at man plasseres i 1600
	}
	
	
	
	private ImageIcon getScaledImage(Dimension imgSize, BufferedImage img) {
		int width = imgSize.width;
		int height = imgSize.height;
	
		try {
			BufferedImage scaledImage = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB );
			Graphics2D gScaledImage = scaledImage.createGraphics();
			gScaledImage.setRenderingHint( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED );
	
			Dimension dim = new Dimension(width, height);
			
			if(scaled) {
				dim = getScaledDimension(img.getWidth(), img.getHeight(), dim);
			}
			gScaledImage.drawImage( img,(int) ((width-dim.getWidth())/2),
					(int)((height-dim.getHeight())/2), (int)dim.getWidth(), (int)dim.getHeight(), null ); 
			gScaledImage.dispose();

			return new ImageIcon(scaledImage);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	private static Dimension getScaledDimension( int width,int height, Dimension boundary) {

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
	
	
	
	private boolean findPictureFiles(String title) {
		File file;
		final JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new ImageFilter());


		int returnVal = fc.showDialog(null, title);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
			this.files = file.getParentFile().listFiles();
			pcs.firePropertyChange(Measurer.NEW_IMAGE_FOLDER, null, null);
			return true;
		}
		//This is where a real application would open the file.
		if(returnVal == JFileChooser.CANCEL_OPTION) {
			return false;
		}

		return false;
	}
	
	public boolean isPicturesReady() {
		if(this.files != null && this.files.length> 0 ) {
			return true;
		}
		else {
			return false;
		}
	}
	//b�r bruke bin�rs�k!
	public boolean forwardToSuitableStartImage(Date initialExcelDate) {
		Date imageDate = this.getDate(0);
		boolean suitableStartPointFound = false;
		
		if(initialExcelDate.after(imageDate)) {
			
			for(int i = this.getIndex(); i< this.getImageCount() && !suitableStartPointFound; i++) {
				imageDate = this.getDate(i);
				
				if(initialExcelDate.equals(imageDate) || initialExcelDate.before(imageDate)) {
					suitableStartPointFound = true;
					this.setIndex(i);
				}
			}
			
			if(!suitableStartPointFound) {
				JOptionPane.showMessageDialog(null,
						"Bildedato passer ikke overens med excelfil!\n velg ny mappe.",
					    "Bildedato passer ikke",
					    JOptionPane.WARNING_MESSAGE);
				this.setIndex(0);
				return false;
			}
			
		}
		return true;
	}
	
	
	public void iterate(int iterateValue) {
		this.iterateIndex(iterateValue);
		
		if(this.getIndex() >= this.getImageCount()) {
			//"Ikke flere bilder igjen i mappen"
			this.findPictureFiles("Open pictures");
			this.setIndex(0);
			// m� returne om cancel eller noe...
		}
		else if(this.getIndex() < 0) {
			this.setIndex(0);
			return;
		}
		if(iterateValue < 0) {
			//setTTPvalues(); wut
		}
		
		else{
			//logTTPvalues(); wut
		}
		
		readIconInBackground(this.files[this.index]);
	}
	
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

}
