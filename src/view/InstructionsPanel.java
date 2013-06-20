package view;


import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import javax.swing.GroupLayout;
import javax.swing.JPanel;

public class InstructionsPanel extends JPanel {
	private static String SAVE_VALUE_STRING = "Trykk Space for å lagre verdiene i Excelfil";
	private static String INFO_STRING = "Ved bruk av innleser må man først konstruere en excelfil med dato (kolonne A) og " +
			"tid (kolonne B). Lokaliser riktig bildemappe og excelfil. Trykk fortsett. " +
			"Avleste verdier vil bli lagret (Kolonne C og D) i en kopi av excelfilen etter man har avsluttet";
	private static String DEACTIVATE_STRING = "Trykk L for å deaktivere målelinjene";
	private static String LEFT_STRING = "Gå frem et bilde uten lagring ved å trykke venstre piltast";
	private static String RIGHT_STRING = "Trykk høyre piltast får å gå tilbake et bilde";
	private static String CHANGE_MEASURE_STRING = "Bestem nye måleverdier. Trykk på valg i meny."
														+ " Velg deretter <endre måleverdier>";
	
	
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
