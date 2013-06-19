package view;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.DecimalFormat;
import javax.swing.JPanel;


public class ThreePhasePanel extends JPanel implements MouseListener, MouseMotionListener {
	
	
	public static int PADDING = 50;
	private static Color LIGHT_BLUE = new Color(38, 121, 255);
	private static Color LIGHT_RED = new Color(225, 38, 38);
	private final DecimalFormat df =  new DecimalFormat("#0.00");
	private double startValueOW = 1;
	private double endValueOW = 18;
	private double startValueOG = 1;
	private double endValueOG = 19;
	private int x1OW;
	private int x2OW;
	private int x1OG;
	private int x2OG;
	private int posOW;
	private int posOG;
	private boolean isPosOWSelected = false;
	private boolean isStartOWSelected =false;
	private boolean isEndOWSelected = false;
	private boolean isPosOGSelected = false;
	private boolean isStartOGSelected =false;
	private boolean isEndOGSelected = false;
	
	public ThreePhasePanel(int x1, int x2, int x1OG, int x2OG) {
		this.x1OW = x1;
		this.x2OW = x2;
		this.x1OG = x1OG;
		this.x2OG = x2OG;
	}
	
	public void setPosOWActive(boolean value) {
		this.isPosOWSelected = value;
	}
	
	public void setPosOGActive(boolean value) {
		this.isPosOGSelected = value;
	}
	
	public void deactivatePos() {
		setPosOGActive(false);
		setPosOWActive(false);
		this.repaint();
	}
	public boolean isPosOWActive() {
		return this.isPosOWSelected;
	}
	
	public boolean isPosOGActive() {
		return this.isPosOGSelected;
	}
	
	public int getStartLineOW() {
		return this.x1OW;
	}
	
	public int getEndLineOW() {
		return this.x2OW;
	}
	
	public int getValueLineOW() {
		return this.posOW;
	}
	
	public void setValueLineOW(int value) {
		this.posOW = value;
	}
	
	public void setValueLineOG(int value) {
		this.posOG = value;
	}
	
	public double getStartValueOW() {
		return this.startValueOW;
	}
	
	public double getEndValueOW() {
		return this.endValueOW;
	}
	
	public void setStartValueOW(double start) {
		this.startValueOW = start;
	}
	
	public void setEndValueOW(double end) {
		this.endValueOW = end;
	}
	
	
	public void setStartLineOW(int pos) {
		this.x1OW = pos;
	}
	
	public void setEndLineOW(int pos) {
		this.x2OW = pos;
	}
	

	//OG getters and setters
	
	public int getStartLineOG() {
		return this.x1OG;
	}
	
	public int getEndLineOG() {
		return this.x2OG;
	}
	
	public int getValueLineOG() {
		return this.posOG;
	}
	
	public double getStartValueOG() {
		return this.startValueOG;
	}
	
	public double getEndValueOG() {
		return this.endValueOG;
	}
	
	public void setStartValueOG(double start) {
		this.startValueOG = start;
	}
	
	public void setEndValueOG(double end) {
		this.endValueOG = end;
	}
	
	public void setStartLineOG(int pos) {
		this.x1OG = pos;
	}
	
	public void setEndLineOG(int pos) {
		this.x2OG = pos;
	}
	
	public double getValueOW() {
		
		double lineInPixel = getEndLineOW() -getStartLineOW();
		double lineInValue = getEndValueOW() - getStartValueOW();
		double lineToPos = getValueLineOW() -getStartLineOW();
		System.out.println("value OW" + getEndValueOW() + " " + getStartValueOW() + "ferdig verdi"+ (((lineToPos/lineInPixel)*lineInValue)+ startValueOW));
		return ((lineToPos/lineInPixel)*lineInValue)+ startValueOW;
	}
	
	public double getValueOG() {
		double lineInPixel = getEndLineOG() -getStartLineOG();
		double lineInValue = getEndValueOG() - getStartValueOG();
		double lineToPos = getValueLineOG() -getStartLineOG();
		return ((lineToPos/lineInPixel)*lineInValue)+ startValueOG;
	}
	
	protected void paintComponent(Graphics gOrig) {
        super.paintComponent(gOrig);
        Graphics2D g = (Graphics2D) gOrig.create();
        try {
            paintOverlay(g);
        } finally {
            g.dispose();
        }
    }
	
	private void paintOverlay(Graphics2D g) {
		Font smallFont = new Font("Arial", Font.PLAIN, 12);
		Font bigFont = new Font("Arial", Font.BOLD, 16);
		
		g.setFont(smallFont);
		g.setStroke (new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10.0f, null, 0.0f));
    	g.setPaint(LIGHT_BLUE);
    	g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,(float)0.8f));
    	g.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	
    	//OW
    	g.drawLine(getStartLineOW(), PADDING, getStartLineOW(), this.getHeight()-PADDING);
    	g.drawLine(getEndLineOW(), PADDING, getEndLineOW(), this.getHeight()-PADDING);
    	g.drawString("Start O/W", getStartLineOW()-20, 30);
    	g.drawString("End 0/W", getEndLineOW()-20, 30);
    	
    	g.fillOval(getStartLineOW()-5, (this.getHeight()-60)/2, 10, 10);
    	g.fillOval(getEndLineOW()-5, (this.getHeight()-60)/2, 10, 10);
    	if(isPosOWSelected) {
    		g.drawString("W/O", posOW-5, 75);
    		g.setFont(bigFont);
    		g.drawString(df.format(getValueOW()), posOW-10, 90);
    		g.drawLine(posOW, 100, posOW, this.getHeight()-100);
    	}
    	
    	//OG
    	g.setFont(smallFont);
    	g.setPaint(LIGHT_RED);
    	g.drawLine(getStartLineOG(), PADDING, getStartLineOG(), this.getHeight()-PADDING);
    	g.drawLine(getEndLineOG(), PADDING, getEndLineOG(), this.getHeight()-PADDING);
    	g.drawString("Start O/G", getStartLineOG()-20, 30);
    	g.drawString("End O/G", getEndLineOG()-20, 30);
    	
    	g.fillOval(getStartLineOG()-5, (this.getHeight()-60)/2, 10, 10);
    	g.fillOval(getEndLineOG()-5, (this.getHeight()-60)/2, 10, 10);
    	if(isPosOGSelected) {
    		g.drawString("O/G", posOG-5, 75);
    		g.setFont(bigFont);
    		g.drawString(df.format(getValueOG()), posOG-10, 90);
    		g.drawLine(posOG, 100, posOG, this.getHeight()-100);
    	}
    }
	public void mouseClicked(MouseEvent e) {
		
		
	}
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mousePressed(MouseEvent e) {
		if(getStartLineOW()-10 < e.getX() && e.getX()<getStartLineOW() + 10) {
			isStartOWSelected = true;
		}
		else if(getEndLineOW()-10 < e.getX() && e.getX() < getEndLineOW()+10) {
			isEndOWSelected = true;
		}
		else if(getEndLineOG()-10 < e.getX() && e.getX() < getEndLineOG()+10) {
			isEndOGSelected = true;
		}
		else if(getStartLineOG()-10 < e.getX() && e.getX() < getStartLineOG()+10) {
			isStartOGSelected = true;
		}
		
		else if(e.getButton() == MouseEvent.BUTTON3){
			if(e.getClickCount() > 1) {
				this.isPosOWSelected = false;
			}
			else {
				this.posOW = e.getXOnScreen();
				this.isPosOWSelected = true;
			}
		}
		else if(e.getButton() == MouseEvent.BUTTON1){
			if(e.getClickCount() >1) {
				this.isPosOGSelected = false;
			}
			else {
				this.posOG = e.getXOnScreen();
				this.isPosOGSelected = true;
			}
			
		}
		
		
	}
	public void mouseReleased(MouseEvent e) {
		if(isStartOWSelected) {
			setStartLineOW(e.getXOnScreen());
			isStartOWSelected = false;
			this.repaint();
		}
		else if(isEndOWSelected) {
			setEndLineOW(e.getXOnScreen());
			isEndOWSelected = false;
			this.repaint();
		}
		else if(isEndOGSelected) {
			setEndLineOG(e.getXOnScreen());
			isEndOGSelected = false;
			this.repaint();
		}
		else if(isStartOGSelected) {
			setStartLineOG(e.getXOnScreen());
			isStartOGSelected = false;
			this.repaint();
		}
		else {
			this.repaint();
		}
			
		
	}
	public void mouseDragged(MouseEvent e) {
		if(isStartOWSelected) {
			setStartLineOW(e.getXOnScreen());
			this.repaint();
		}
		else if(isEndOWSelected) {
			setEndLineOW(e.getXOnScreen());
			this.repaint();
		}
		else if(isEndOGSelected) {
			setEndLineOG(e.getXOnScreen());
			this.repaint();
		}
		else if(isStartOGSelected) {
			setStartLineOG(e.getXOnScreen());
			this.repaint();
		}
	}
	public void mouseMoved(MouseEvent e) {
		
		
	}
	
	public boolean isThereAThreePhaseValue() {
		return this.isPosOGActive() || this.isPosOWActive();
	}
}

