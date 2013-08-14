package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.table.AbstractTableModel;

public class LogModel extends AbstractTableModel implements PropertyChangeListener  {
	
	private String[] columnNames = {"Date", "ValueOG", "ValueOW", "row"};
	protected Object[][] data;
	protected PropertyChangeSupport pcs;
	
	public LogModel() {
		super();
		pcs = new PropertyChangeSupport(this);
	}
	@Override
	public int getColumnCount() {
		return this.columnNames.length;
	}

	@Override
	public int getRowCount() {
		if(data != null)
			return data.length;
			else 
				return -1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

}
