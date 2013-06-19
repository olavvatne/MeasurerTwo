package view;


import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import javax.swing.GroupLayout;
import javax.swing.JPanel;

public class InstructionsPanel extends JPanel {
	private static String SAVE_VALUE_STRING = "For å lagre en verdi i excelfilen trykker man space.";
	private static String INFO_STRING = "For å kunne bruke innleseren må man lage en excel fil med dato (kolonne A) og" +
			"tid (kolonne B). Denne filen må man finne ved å trykke knappen under. Deretter må man lokalisere bildemappen og" +
			"åpne første bildet i denne. Da kan man trykke fortsett og verdier avlest fra bildene vil bli skrevet inn i kolonne C og D i exceldokumentet ";
	private static String DEACTIVATE_STRING = "For å deaktivere målelinjene trykk L";
	private static String LEFT_STRING = "For å gå frem et bilde uten å lagre trykk venstre piltast";
	private static String RIGHT_STRING = "For å gå tilbake et bilde trykk høyre piltast";
	private static String CHANGE_MEASURE_STRING = "For å endre måleverdier til menisken, trykk valg i menyen."
														+ " Velg deretter <endre måleverdier>, og bestem nye måleverdier";
	
	
	private  TextAndIconPanel saveInst = new TextAndIconPanel(SAVE_VALUE_STRING, "SPACE");
	private  TextAndIconPanel measureInst = new TextAndIconPanel(CHANGE_MEASURE_STRING, "meny");
	private  TextAndIconPanel leftInst = new TextAndIconPanel(LEFT_STRING, "LEFT");
	private  TextAndIconPanel rightInst = new TextAndIconPanel(LEFT_STRING, "RIGHT");
	private  TextAndIconPanel deactInst = new TextAndIconPanel(DEACTIVATE_STRING, "L");
	private  TextAndIconPanel infoInst = new TextAndIconPanel(INFO_STRING, "Info");
	
	protected boolean drawRoundedRectagle = true;
	protected int strokeSize = 5;
	protected Dimension arcs = new Dimension(20, 20);
	protected boolean highQuality = true;
	
	//instr må wrappe.
	//fikse layout
	//legge til mål på Instpan - objekter
	public InstructionsPanel() {
		super();
		setOpaque(false);
		setLayout();
	}

	public InstructionsPanel(boolean drawRoundedRect) {
		this();
		drawRoundedRectagle = drawRoundedRect;
	}
	
	
	public void setDrawRoundedRect(boolean value) {
		this.drawRoundedRectagle = value;
	}
	
	
	private void setLayout() {
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addComponent(infoInst)
				.addComponent(measureInst)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(rightInst)
								.addComponent(deactInst))
						.addGroup(layout.createParallelGroup()
								.addComponent(saveInst)
								.addComponent(leftInst)))
				
		);


		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(infoInst)
				.addComponent(measureInst)
				.addGroup(layout.createParallelGroup()
						.addComponent(rightInst)
						.addComponent(leftInst)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(saveInst)
						.addComponent(deactInst)
						)
				.addGroup(layout.createParallelGroup()
						)
		);
	}

	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(drawRoundedRectagle) {
        	int width = getWidth();
            int height = getHeight();
            Graphics2D graphics = (Graphics2D) g;

            //Sets antialiasing if HQ.
            if (highQuality) {
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
    			RenderingHints.VALUE_ANTIALIAS_ON);
            }

         
            //Draws the rounded opaque panel with borders.
            graphics.setColor(Color.WHITE);
            graphics.fillRoundRect(3, 3, width-5, 
    		height-5, arcs.width, arcs.height);
            graphics.setColor(Color.GRAY.brighter());
            graphics.setStroke(new BasicStroke(strokeSize));
            graphics.draw(new RoundRectangle2D.Double(3, 3, width-6, height-6, arcs.width, arcs.height));

        }
        
    }
}
