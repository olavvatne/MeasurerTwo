package view;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class LogPanel extends JPanel {
	
	private JScrollPane listScroll;
	private JTable logTable;
	
	public LogPanel() {
		logTable = new JTable();
		listScroll = new JScrollPane();

		listScroll.setViewportView(logTable);
		listScroll.setPreferredSize(new Dimension(200, this.getHeight()));
		this.add(listScroll);
	}
	
	public void setModel() {
		
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		LogPanel panel = new LogPanel();
		frame.add(panel);
		frame.setVisible(true);
		System.out.println("testLog");
	}
}
