package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import measurer.Measurer;

public class MeasurementMenu extends JMenuBar {
		PropertyChangeSupport pcs;
		public MeasurementMenu() {
			this.pcs = new PropertyChangeSupport(this);
			
			JMenu fileMenu = new JMenu("Fil");
			fileMenu.setMnemonic(KeyEvent.VK_F);
			this.add(fileMenu);
			JMenuItem saveAndQuitItem = new JMenuItem("Lagre og avslutt");
			saveAndQuitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
			saveAndQuitItem.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					propertyChange(Measurer.EXIT, null, null);
				}
			});
			fileMenu.add(saveAndQuitItem);
			JMenuItem saveItem = new JMenuItem("Lagre");
			saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
			saveItem.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					propertyChange(Measurer.SAVE, null, null);
				}
			});
			fileMenu.add(saveItem);
			
			
			
			//second menu
			JMenu optionMenu = new JMenu("Valg");
			optionMenu.setMnemonic(KeyEvent.VK_V);
			this.add(optionMenu);
			JMenuItem measurementItem = new JMenuItem("Endre måleverdier");
			measurementItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
			measurementItem.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					propertyChange(Measurer.CHANGE_MEASUREMENTS, null, null);
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
					JCheckBoxMenuItem item = (JCheckBoxMenuItem)(e.getSource());
					propertyChange(Measurer.SCALE, Boolean.valueOf(!item.isSelected()), Boolean.valueOf(item.isSelected()));
				}
			});
			optionMenu.add(scaledItem);
			
			JMenuItem logItem = new JMenuItem("Vis logg");
			logItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
			logItem.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					propertyChange(Measurer.LOG, null, null);
				}
			});
			optionMenu.add(logItem);
			
			//third menu
			JMenu helpMenu = new JMenu("Hjelp");
			helpMenu.setMnemonic(KeyEvent.VK_H);
			this.add(helpMenu);
			
			JMenuItem helpItem = new JMenuItem("Hjelp");
			
			helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
			helpItem.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					
					propertyChange(Measurer.HELP, null, null);
				}
			});
			helpMenu.add(helpItem);
			
			helpMenu.setEnabled(false);
			fileMenu.setEnabled(false);
			optionMenu.setEnabled(false);
			
		}
		
		private void propertyChange(String propertyName, Object oldValue, Object newValue) {
			this.pcs.firePropertyChange(propertyName, oldValue, newValue);
		}
		
		public void addpropertyChangeListener(PropertyChangeListener listener) {
			this.pcs.addPropertyChangeListener(listener);
		}
		
}
