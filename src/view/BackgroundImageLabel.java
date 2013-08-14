package view;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

class BackgroundImageLabel extends JLabel
{
    ImageIcon imageIcon;
    
    public BackgroundImageLabel(ImageIcon icon)
    {
        super();
        this.imageIcon = icon;
    }
    
    public void setIcon(ImageIcon icon) {
    	this.imageIcon = icon;
    }
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(imageIcon != null) {
        	 g.drawImage(imageIcon.getImage(),0,0,getWidth(),getHeight(),this);
        }
       
    }
}