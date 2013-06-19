package actions;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import view.CardPanel;

public class WindowClosingListener extends WindowAdapter {
	private CardPanel panel;

	public WindowClosingListener(CardPanel panel) {
		this.panel = panel;
	}
	
	public void windowClosing(WindowEvent arg0) {
		this.panel.exitApplication();	
	}
}
