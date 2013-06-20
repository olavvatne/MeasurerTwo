package view;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import measurer.Measurer;
import model.ExcelCommunication;
import model.ImageFolderModel;



public class SetupPanel extends GradientPanel  {
	
	private JPanel parent, componentsPanel;
	private JButton openExcelButton;
	private JButton openPicturesButton;
	private JButton continueButton;
	private InstructionsPanel instructPanel = new InstructionsPanel();
	private ImageIcon check = new ImageIcon(Measurer.class.getResource("/Check-icon.png"));
	private ImageIcon cross = new ImageIcon(Measurer.class.getResource("/Delete-icon.png"));
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
		SwingWorker<Boolean, Void> task = new SwingWorker<Boolean, Void>() {

			@Override
			protected Boolean doInBackground() throws Exception {
				// TODO Auto-generated method stub
				return excelModel.openExcelFile();
			}
			
			public void done() {
				boolean isReady = false;
				try {
					isReady = get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(isReady) {
					excelAcceptPic.setIcon(check);
					openPicturesButton.requestFocus();
					if(imageModel.isPicturesReady()) {
						if(isStartImage()) {
							continueButton.setEnabled(true);
						}
					}
				}
			}
		};
		
		task.execute();	
		}
	}
	
	
	private void openPicturesFile() {
		if(imageModel != null && excelModel != null) {
			if(imageModel.openPicturesFolder()) {
				this.picturesAcceptPic.setIcon(check);
				this.continueButton.requestFocus();
				if(excelModel.isExcelReady()) {
					if(isStartImage()) {
						this.continueButton.setEnabled(true);
					}
					
				}
			}
		}
	}
	
	private boolean isStartImage() {
		if(imageModel.forwardToSuitableStartImage(excelModel.getCurrentDate())) {
			return true;
		}
		else {
			this.picturesAcceptPic.setIcon(cross);
			return false;
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
