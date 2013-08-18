package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;








import model.LogModel;

public class LogPanel extends JPanel implements ActionListener {
	
	private JScrollPane listScroll;
	private JTable logTable;
	private JButton deleteButton, closeButton;
	private LogModel model;
	
	public LogPanel() {
		
		logTable = new JTable();
		listScroll = new JScrollPane();

		listScroll.setViewportView(logTable);
		this.deleteButton = new JButton("Slett");
		this.deleteButton.addActionListener(this);
		this.closeButton = new JButton("Lukk");
		this.closeButton.setEnabled(false);
		logTable.setFillsViewportHeight(true);
		listScroll.setMinimumSize(new Dimension(200,600));
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(5, 5, 5, 5);
		c.gridx = 0;
		c.gridy = 1;
		this.add(deleteButton, c);
		
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.LINE_END;
		c.insets = new Insets(5, 5, 5, 5);
		c.gridx = 1;
		c.gridy = 1;
		this.add(closeButton, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0, 0, 10, 0);
		c.weighty = 1;
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		this.add(listScroll, c);
	}
	
	public void setModel(LogModel model) {
		if(model != null) {
			this.logTable.setModel(model);
			this.model = model;
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		LogPanel panel = new LogPanel();
		LogModel model = new LogModel();
		panel.setModel(model);
		frame.add(panel);
		frame.setVisible(true);
		frame.setSize(new Dimension(100, 200));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.appendRow("fdgd", 34.4,1.3 , 1000);
		
		System.out.println("testLog");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(logTable.getSelectedRowCount() > 0) {
			this.model.deleteRow(logTable.getSelectedRow());
		}
		
	}
}
