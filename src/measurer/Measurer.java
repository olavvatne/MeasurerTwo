package measurer;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;



import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import actions.WindowClosingListener;

import view.CardPanel;
import view.MeasurementMenu;


public class Measurer {
	public static final String HELP = "Help";
	public static final String EXIT = "Exit";
	public static final String CHANGE_MEASUREMENTS = "change"; 
	public static final String SAVE = "Save"; 
	public static final String SAVEQUIT = "Quit";
	public static final String SCALE = "Scale";
	public static final String LOG = "Log";
	public static final String IMAGE = "Image";
	public static final String NEW_IMAGE_FOLDER = "newImages";
	public static final String OPTIONS = "options";
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() 
			{
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Measurer.run();
			}
		});

	}
	
	public static void run() {
		JFrame frame = new JFrame();
		MeasurementMenu menu = new MeasurementMenu();
		Dimension sz = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setMinimumSize(new Dimension((int)(sz.getWidth()/2), (int)(sz.getHeight()/2)));
		frame.setPreferredSize(sz);// må vekk for at minimering skal faktisk minimere, maximize forårsaker layout krøll med initflytting
		frame.setLocation(sz.width/10 , sz.height/10);
		frame.setTitle("innlesning");
		frame.setIconImage(new ImageIcon(Measurer.class.getResource("/measure.png")).getImage());
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); //flytter på seg bug
		frame.setJMenuBar(menu);
		CardPanel panel = new CardPanel(menu);
		frame.add(panel);
		menu.addpropertyChangeListener(panel);
		frame.addWindowListener(new WindowClosingListener(panel));
		frame.pack();
		frame.setVisible(true);
		
	}
	
}
