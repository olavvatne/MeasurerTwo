package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;

public class IconLabel extends JLabel {
	
	private JLabel textLabel;
	protected int strokeSize = 2;
	protected Dimension arcs = new Dimension(20, 20);
	protected boolean highQuality = true;
	
	public IconLabel(String text) {
		textLabel = new JLabel(text);
		textLabel.setFont(new Font(this.getFont().getFontName(), Font.BOLD, 14));
		textLabel.setForeground(Color.GRAY);
		setLayout();
	}
	

	public IconLabel(Icon icon) {
		super(icon);
	}
	
	
	private void setLayout() {
		this.setLayout(new GridBagLayout());
		this.add(textLabel);
		
		
	}
	
	 protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        int width = getWidth();
	        int height = getHeight();
	        Graphics2D graphics = (Graphics2D) g;

	        //Sets antialiasing if HQ.
	        if (highQuality) {
	            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
	        }

	     
	        //Draws the rounded opaque panel with borders.
	    
	        graphics.setColor(Color.GRAY);
	        graphics.setStroke(new BasicStroke(strokeSize));
	        graphics.draw(new RoundRectangle2D.Double(10, 10, width-20, height-20, arcs.width, arcs.height));

	    }
}
