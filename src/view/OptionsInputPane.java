package view;


import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class OptionsInputPane {
	private JTextField fontSizeField = new JTextField(5);
	private int fontSize;
	private int state;
	public OptionsInputPane(int fontSize) {
		this.fontSize = fontSize;
		this.fontSizeField.setText(fontSize + "");
		
		JPanel windowPanel = new JPanel();
		windowPanel.add(new JLabel("Fontstørrelse"));
		windowPanel.add(fontSizeField);
		do {
			this.state = JOptionPane.showConfirmDialog(null, windowPanel, "Valg", JOptionPane.OK_CANCEL_OPTION);
			
		} while(!isCorrectValues(fontSizeField.getText()));  
	}

	public int getFontSize() {
		return fontSize;
	}

	
	
	private boolean isCorrectValues(String font) {
		try {
			this.fontSize = Integer.parseInt(font);
			
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
