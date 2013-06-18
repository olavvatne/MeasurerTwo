package measurer;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import view.CardPanel;


public class Measurer {

	
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
		CardPanel panel = new CardPanel(frame);
		frame.add(panel);
		frame.pack();
		Dimension sz = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setMinimumSize(new Dimension((int)(sz.getWidth()/2), (int)(sz.getHeight()/2)));
		frame.setPreferredSize(sz);
		frame.setLocation(sz.width/10 , sz.height/10);
		frame.setTitle("innlesning");
		frame.setIconImage(new ImageIcon("resources/measure.png").getImage());
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setJMenuBar(createMenuBar());
		frame.setVisible(true);
		//disable them.
		
		System.out.println(frame.getJMenuBar().getMenuCount());
		
	}
	
	private static JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("Fil");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(fileMenu);
		JMenuItem saveAndQuitItem = new JMenuItem("Lagre og avslutt");
		saveAndQuitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		saveAndQuitItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				//logic.exitApplication();
				System.out.println("exit");
			}
		});
		fileMenu.add(saveAndQuitItem);
		JMenuItem saveItem = new JMenuItem("Lagre");
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				//logic.save();
				System.out.println("save");
			}
		});
		fileMenu.add(saveItem);
		
		
		
		//second menu
		JMenu optionMenu = new JMenu("Valg");
		optionMenu.setMnemonic(KeyEvent.VK_V);
		menuBar.add(optionMenu);
		JMenuItem measurementItem = new JMenuItem("Endre måleverdier");
		measurementItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
		measurementItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				//logic.changeMeasurementDialog(threePhasePanel.getStartValueOW(), threePhasePanel.getEndValueOW(),
						//threePhasePanel.getStartValueOG(), threePhasePanel.getEndValueOG());
				System.out.println("change measurements");
			}
		});
		optionMenu.add(measurementItem);
		/*
		colorItem = new JMenuItem("Endre farge på markører maybee???"); //kanskje ikke nødvendig denne her
		colorItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		optionMenu.add(colorItem);
		*/
		JCheckBoxMenuItem scaledItem = new JCheckBoxMenuItem("Skaler bildet");
		scaledItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		scaledItem.setSelected(false);
		scaledItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				//logic.setScaled(scaledItem.isSelected());
				System.out.println("scale");
			}
		});
		optionMenu.add(scaledItem);
		
		JMenuItem logItem = new JMenuItem("Vis logg");
		logItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		logItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				//toggleLog();
				System.out.println("show log");
			}
		});
		optionMenu.add(logItem);
		
		//third menu
		JMenu helpMenu = new JMenu("Hjelp");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		menuBar.add(helpMenu);
		
		JMenuItem helpItem = new JMenuItem("Hjelp");
		
		helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		helpItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
					//logic.helpDialog();
				System.out.println("help dialog");
			}
		});
		helpMenu.add(helpItem);
		
		return menuBar;
	}
}
