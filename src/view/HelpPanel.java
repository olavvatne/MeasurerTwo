package view;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JPanel;

public class HelpPanel extends JPanel {
	private InstructionsPanel instructPanel;
	private JPanel componentsPanel, parent;
	private JButton backButton;

	
	public HelpPanel(JPanel parent) {
		this.parent = parent;
		this.instructPanel = new InstructionsPanel(true);
		this.componentsPanel = new JPanel();
		this.backButton = new JButton("Tilbake");
		this.backButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				backButtonAction();
			}
		});
		this.setLayout(new GridBagLayout());
		this.add(componentsPanel);
		componentsPanel.add(instructPanel);
		componentsPanel.add(backButton);
		GroupLayout layout = new GroupLayout(componentsPanel);
        componentsPanel.setLayout(layout);
        
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
        layout.setHorizontalGroup(layout.createParallelGroup()
        		.addComponent(instructPanel)
        		.addComponent(backButton, Alignment.TRAILING)
            	
            );
            
            
            layout.setVerticalGroup(layout.createSequentialGroup()
            		.addComponent(instructPanel)
            		.addComponent(backButton)
            		
            );
	}
	
	public void backButtonAction() {
		((CardPanel)this.parent).showMeasurementPanel();
	}
}
