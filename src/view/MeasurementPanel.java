package view;



import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;








import measurer.ConfigurationManager;
import measurer.Measurer;
import model.ExcelCommunication;
import model.ImageFolderModel;

public class MeasurementPanel extends JPanel implements PropertyChangeListener {
	public final static int START_IMG = 0;
	public final static int FORWARD = 1;
	public final static int BACK = -1;
	private JPanel parent;
	private ExcelCommunication excelModel;
	private ImageFolderModel imageModel;
	private JLayeredPane layeredPane;
	private JLabel imageLabel;
	private LogPanel logPanel;
	private ThreePhasePanel threePhasePanel;
	private ConfigurationManager userDefaults;
	
	public MeasurementPanel(JPanel parent, ConfigurationManager userDefaults) {
		this.parent = parent;
		this.userDefaults = userDefaults;
		init();
		setLayout();
		
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals(Measurer.LOG)) {
			toggleLog();
		}
		else if(evt.getPropertyName().equals(Measurer.SCALE)) {
			imageModel.setScaled((Boolean)evt.getNewValue());
		}
		else if(evt.getPropertyName().equals(Measurer.CHANGE_MEASUREMENTS)) {
			changeMeasurementDialog();
		}
		else if(evt.getPropertyName().equals(Measurer.IMAGE)) {
			setBackgroundImage((ImageIcon)evt.getNewValue());
			System.out.println("nytt bilde!");
		}
		else if(evt.getPropertyName().equals(Measurer.NEW_IMAGE_FOLDER)) {
			/*if(this.excelModel != null) {
				imageModel.forwardToSuitableStartImage(excelModel.getCurrentDate());
			}
			*/
		}
	}
	
	
	
	public void setExcelModel(ExcelCommunication model) {
		this.excelModel = model;
	}
	
	public void setImageModel(ImageFolderModel model) {
		this.imageModel = model;
		this.imageModel.addPropertyChangeListener(this);
		
	}
	
	public void toggleLog() {
		this.logPanel.setVisible(!logPanel.isVisible());
	}
	
	public void changeMeasurementDialog() {
		ScaleValuesInputPane dialog = new ScaleValuesInputPane(getTPPanel().getStartValueOW(),
				getTPPanel().getEndValueOW(), getTPPanel().getStartValueOG(), getTPPanel().getEndValueOG());
		if(dialog.isMeasurementsValid()) {
			getTPPanel().setStartValueOG(dialog.getsOG());
			userDefaults.setProperty("startOW", dialog.getsOG()+"");
			getTPPanel().setEndValueOG(dialog.geteOG());
			userDefaults.setProperty("endOW", dialog.geteOG()+"");
			getTPPanel().setStartValueOW(dialog.getsOW());
			userDefaults.setProperty("startOG", dialog.getsOW()+"");
			getTPPanel().setEndValueOW(dialog.geteOW());
			userDefaults.setProperty("endOG", dialog.geteOW()+"");
			getTPPanel().repaint();
		}
	}
	

	public void setBackgroundImage(ImageIcon img) {
		if(img != null) {
			this.imageLabel.setIcon(img);
			this.repaint();
		}
	}
	
	public ThreePhasePanel getTPPanel() {
		return this.threePhasePanel;
	}
	
	private void init() {
		this.threePhasePanel = new ThreePhasePanel(userDefaults.getDoubleProperty("startOW"),
				userDefaults.getDoubleProperty("endOW"),
				userDefaults.getDoubleProperty("startOG"),
				userDefaults.getDoubleProperty("endOG"));
		
		this.threePhasePanel.setOpaque(false);	
		this.layeredPane = new JLayeredPane();
		this.addMouseListener(threePhasePanel);
		this.addMouseMotionListener(threePhasePanel);
		this.imageLabel = new JLabel();
		this.imageLabel.setBackground(Color.RED);
		setLayout();

		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(KeyEvent.VK_LEFT == e.getKeyCode()) {
					imageModel.iterate(BACK);
				
				}
				else if(KeyEvent.VK_RIGHT == e.getKeyCode()) {
					imageModel.iterate(FORWARD);
					
				}
				else if(KeyEvent.VK_SPACE == e.getKeyCode()) {
					excelModel.logValue(imageModel, getTPPanel().getValueOW(), getTPPanel().getValueOW());
					imageModel.iterate(FORWARD);
				}
				else if(KeyEvent.VK_L == e.getKeyCode()) {
					getTPPanel().deactivatePos();
				}
			}
		});	
	}
	
	private void setLayout() {
		this.setLayout(new BorderLayout());
		layeredPane = new JLayeredPane();
		logPanel = new LogPanel();
		logPanel.setBackground(Color.white);
		logPanel.setVisible(false);

		layeredPane.add(imageLabel, new Integer(0));
		layeredPane.add(threePhasePanel, new Integer(1));
		layeredPane.add(logPanel, new Integer(2));
		this.add(layeredPane, BorderLayout.CENTER);
		layeredPane.addComponentListener(new ComponentListener() {
			
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void componentResized(ComponentEvent e) {
				threePhasePanel.setBounds(0, 0, layeredPane.getWidth(), layeredPane.getHeight());
				imageLabel.setBounds(0, 0, layeredPane.getWidth(), layeredPane.getHeight());
				logPanel.setBounds(50, 50, 200, layeredPane.getHeight()-150);
				imageModel.setImageSize(layeredPane.getSize());
			}
			
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	
}
