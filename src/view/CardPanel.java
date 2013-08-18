
package view;


import java.awt.CardLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import measurer.ConfigurationManager;
import measurer.Measurer;
import model.ExcelCommunication;
import model.ImageFolderModel;
import model.LogModel;

public class CardPanel extends JPanel implements PropertyChangeListener {
	
	private static String MEASUREMENT_PANEL = "Measure";
	private static String HELP_PANEL = "Help";
	private static String SETUP_PANEL = "Setup";
	
	private MeasurementMenu menu;
	private SetupPanel setupPanel;
	private MeasurementPanel measurementPanel;
	private HelpPanel helpPanel;
	private ImageFolderModel image;
	private ExcelCommunication excel;
	private LogModel logModel;
	
	public CardPanel(MeasurementMenu menu) {
		super();
		
		this.logModel = new LogModel();
		this.image = new ImageFolderModel();
		this.excel = new ExcelCommunication();
		this.excel.addPropertyChangeListener(logModel);
		this.logModel.addPopertyChangeListener(excel);
		this.menu = menu;
		this.setupPanel = new SetupPanel(this);
		this.setupPanel.setImageModel(this.image);
		this.setupPanel.setExcelModel(this.excel);
		this.measurementPanel = new MeasurementPanel(this);
		this.measurementPanel.setImageModel(this.image);
		this.measurementPanel.setExcelModel(this.excel);
		this.measurementPanel.setLogModel(logModel);
		menu.addpropertyChangeListener(measurementPanel);
		this.helpPanel = new HelpPanel(this);
		setLayout();
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals(Measurer.HELP)) {
			showHelpPanel();
		}
		else if(evt.getPropertyName().equals(Measurer.EXIT)) {
			exitApplication();
		}
		else if(evt.getPropertyName().equals(Measurer.SAVE)) {
			save();
		}
		else if(evt.getPropertyName().equals(Measurer.OPTIONS)) {
			
			//Options skal jeg putte det i measurementMenu?
			
		}
	}
	
	public void exitApplication() {
		if(excel.isExcelInputStreamOpen()) {
			excel.closeAndWriteExcel();
		}
		
		System.exit(0);
		
	}
	
	public void save() {
		excel.saveExcelFile();
		
	}
	
	public void showHelpPanel() {
		((CardLayout)this.getLayout()).show(this, HELP_PANEL);
	}
	
	public void showMeasurementPanel() {
		((CardLayout)this.getLayout()).show(this, MEASUREMENT_PANEL);
		for(int i = 0; i<menu.getMenuCount(); i++) {
			menu.getMenu(i).setEnabled(true);
		}
		this.setupPanel.setFocusable(false);
		this.measurementPanel.requestFocus();
		this.image.iterate(MeasurementPanel.START_IMG);
	}
	
	private void setLayout() {
		this.setLayout(new CardLayout());
		this.add(SETUP_PANEL, setupPanel);
		this.add(MEASUREMENT_PANEL, measurementPanel);
		this.add(HELP_PANEL, helpPanel);
	}

	
}
