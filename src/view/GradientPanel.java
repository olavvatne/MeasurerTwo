package view;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;

import javax.swing.JPanel;
/**
 * This class is an extension of JPanel and simply overrides the paintComponent, cast a Graphics to Graphics2D in
 * order to fill the JPanel with a linear gradient. Used by all windows in Sheepwatch to generate the background.
 * Especially useful when a JFrame is resizable, like the SheepOverview.
 * @author Olav
 *
 */
public class GradientPanel extends JPanel {
	public static final Color GRAD1 = new Color(90,175,240);
	public static final Color GRAD3 = new Color(225,238,242);
	public static final Color GRAD2= new Color(162,197,223);
 
    	protected void paintComponent(Graphics grphcs) {
    		Graphics2D g2d = (Graphics2D) grphcs;
    		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
    				RenderingHints.VALUE_ANTIALIAS_ON);
    	     float[] dist = {0f, 1.0f};
    	     Color[] colors = {GRAD3, GRAD1};
    	     RadialGradientPaint p = new RadialGradientPaint(new Point(getWidth()/2, 
    	    		 					getHeight()/2), getWidth()/2, dist, colors);
    		g2d.setPaint(p);
    		g2d.fillRect(0, 0, getWidth(), getHeight()+getHeight()/2);
    		super.paintComponent(grphcs);
    	}
}
