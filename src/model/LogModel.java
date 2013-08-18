package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Date;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

public class LogModel extends AbstractTableModel implements PropertyChangeListener  {
	
	private String[] columnNames = {"Date", "ValueOG", "ValueOW", "row"};
	protected ArrayList<Object[]> data;
	protected PropertyChangeSupport pcs;
	
	public LogModel() {
		super();
		this.data = new ArrayList<Object[]>();
		pcs = new PropertyChangeSupport(this);
	}
	
	public String getColumnName(int column) {
		if(column < this.columnNames.length)
			return this.columnNames[column];
		else
			return "No name";
	}
	@Override
	public int getColumnCount() {
		return this.columnNames.length;
	}

	@Override
	public int getRowCount() {
		if(data != null)
			return data.size();
			else 
				return -1;
	}

	
	public void appendRow(String date, double value, double value2, int excelRow) {
		Object[] object = {date, value, value2, excelRow};
		this.data.add(object);
		this.fireTableRowsInserted(this.data.size()-1, this.data.size()-1);
	}
	
	public void deleteRow(int rowInTable) {
		System.out.println(rowInTable);
		Integer rowInExcel = new Integer((int) data.get(rowInTable)[3]);
		this.data.remove(rowInTable);
		this.fireTableRowsDeleted(rowInTable, rowInTable);
		this.pcs.firePropertyChange("DELETE", null, rowInExcel );
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data.get(rowIndex)[columnIndex];
	}
	
	public void addPopertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent e) {
		
		if(e.getPropertyName().equals("ADD")) {
			this.data.add((Object[]) e.getNewValue());
			this.fireTableRowsInserted(this.data.size()-1, this.data.size()-1);
		}
		
	}

}
