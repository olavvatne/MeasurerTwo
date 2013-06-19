package view;



import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class TextAndIconPanel extends JPanel {
	private final JTextArea instruction;
	private final IconLabel icon;
	
	public TextAndIconPanel(String text, String icon) {
		this.instruction = new JTextArea(text);
		this.instruction.setEditable(false);
		this.instruction.setOpaque(false);
		this.instruction.setWrapStyleWord(true);
		this.instruction.setLineWrap(true);
		this.instruction.setFont(new Font(this.getFont().getName(), Font.PLAIN, 14));
		this.instruction.setMargin(new Insets(10,10,10,10));
		this.icon = new IconLabel(icon);
		this.instruction.setMinimumSize(new Dimension(180, 100));
		this.setOpaque(false);
		this.icon.setMinimumSize(new Dimension(100, 100));
		setLayout();
	}

	
	private void setLayout() {
		GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
        layout.setHorizontalGroup(layout.createSequentialGroup()
            	.addComponent(icon)
        		.addComponent(instruction)
            );
            
            
            layout.setVerticalGroup(layout.createParallelGroup()
            		.addComponent(icon)
            		.addComponent(instruction)
            );
	}
	
	
}
