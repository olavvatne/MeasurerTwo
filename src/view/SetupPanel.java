package view;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.ExcelCommunication;
import model.ImageFolderModel;



public class SetupPanel extends GradientPanel  {
	
	private JPanel parent, componentsPanel;
	private JButton openExcelButton;
	private JButton openPicturesButton;
	private JButton continueButton;
	private InstructionsPanel instructPanel = new InstructionsPanel();
	private static ImageIcon check = new ImageIcon("resources/Check-icon.png");
	private static ImageIcon cross = new ImageIcon("resources/Delete-icon.png");
	private JLabel excelAcceptPic = new JLabel(cross);
	private JLabel picturesAcceptPic = new JLabel(cross);
	private ImageFolderModel imageModel;
	private ExcelCommunication excelModel;
	
	public SetupPanel(JPanel parent) {
		super();
		this.parent = parent;
		this.setOpaque(false);
		init();
		setLayout();
	}
	
	public void setImageModel(ImageFolderModel model) {
		this.imageModel = model;
	}
	
	public void setExcelModel(ExcelCommunication model) {
		this.excelModel = model;
	}
	
	private void openExcelFile() {
		if(excelModel != null && imageModel != null) {
			if(excelModel.openExcelFile()) {
				this.excelAcceptPic.setIcon(check);
				this.openPicturesButton.requestFocus();
				if(imageModel.isPicturesReady()) {
					this.continueButton.setEnabled(true);
				}
			}
		}
	}
	
	
	private void openPicturesFile() {
		if(imageModel != null && excelModel != null) {
			if(imageModel.findPictureFiles()) {
				this.picturesAcceptPic.setIcon(check);
				this.continueButton.requestFocus();
				if(excelModel.isExcelReady()) {
					this.continueButton.setEnabled(true);
				}
			}
		}
	}
	
	
	private boolean isSetupComplete() {
		if(imageModel.isPicturesReady() && excelModel.isExcelReady()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	private void init() {
		componentsPanel = new JPanel();
		componentsPanel.setOpaque(false);
		instructPanel.setOpaque(false);
		
		openExcelButton = new JButton("Åpne Excelfil");
		openExcelButton.setOpaque(false);
		openExcelButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				openExcelFile();
			}
		});
		
		openPicturesButton = new JButton("Åpne bildefiler");
		openPicturesButton.setOpaque(false);
		openPicturesButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				openPicturesFile();
			}
		});
		
		continueButton = new JButton("Fortsett");
		continueButton.setOpaque(false);
		continueButton.setEnabled(false);
		continueButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(isSetupComplete()) {
					
					((CardPanel)parent).showMeasurementPanel();
				}
				
			}
		});
	}
	
	
	
	
	
	
	private void setLayout() {
		this.setLayout(new GridBagLayout());
		this.add(componentsPanel);
		componentsPanel.add(continueButton);
		componentsPanel.add(instructPanel);
		componentsPanel.add(openExcelButton);
		componentsPanel.add(openPicturesButton);
		
		GroupLayout layout = new GroupLayout(componentsPanel);
        componentsPanel.setLayout(layout);
        
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
        layout.setHorizontalGroup(layout.createParallelGroup()
        		.addComponent(instructPanel)
        		.addGroup(layout.createSequentialGroup()
        			.addComponent(openExcelButton)
        			.addComponent(excelAcceptPic)
        			.addGap(10, 20, 30)
                	.addComponent(openPicturesButton)
                	.addComponent(picturesAcceptPic)
                	.addGap(10, 20, 30)
                	.addComponent(continueButton))
            	
            );
            
            
            layout.setVerticalGroup(layout.createSequentialGroup()
            		.addComponent(instructPanel)
            		.addGroup(layout.createParallelGroup()
            			.addComponent(openExcelButton)
            			.addComponent(excelAcceptPic)
                    	.addComponent(openPicturesButton)
                    	.addComponent(picturesAcceptPic)
                    	.addComponent(continueButton))
            		
            );
	}
}
