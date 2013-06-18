package model;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import filter.ExcelFilter;
import javax.swing.JFileChooser;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelCommunication {
	/*
	 * Perfect place for date, time and date n time regex setting in a setting file.
	 * 
	 */
	private int dateColumn;
	private int timeColumn;
	private int excelFileNameLength;
	private File excelFile;
	private File copyFile;
	private String dateRegex = "/";
	private String timeRegex = ":";
	private Workbook workbook;
	private WritableWorkbook copy;
	private WritableSheet sheet;
	private int indexExcel = 0;
	private boolean ExcelStream;



	public ExcelCommunication(int dateColumn, int timeColumn) {
		this.dateColumn = dateColumn;
		this.timeColumn = timeColumn;
	}



	public boolean openExcelFile() {
		if(findExcelFile()) {
			this.excelFileNameLength =this.excelFile.getName().substring(0, excelFile.getName().length()-4).length();
			initExcelWrite(this.excelFile);
			this.ExcelStream = true;
			return true;
		}
		return false;
	}



	private void initExcelWrite(File excel) {
		this.copyFile = findSavePath(excel, this.excelFileNameLength);

		try {
			workbook = Workbook.getWorkbook(excel);
			copy = Workbook.createWorkbook(this.copyFile, workbook);
			sheet = copy.getSheet(0);
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	public int getIndexExcel() {
		return indexExcel;
	}



	public void setIndexExcel(int indexExcel) {
		this.indexExcel = indexExcel;
	}



	public boolean isExcelInputStreamOpen() {
		return this.ExcelStream;
	}



	public Date getDate(int row) {
		System.out.println("rad i excel " + row);
		String[] date = sheet.getWritableCell(dateColumn, row).getContents().trim().split(dateRegex);
		String[] time = sheet.getWritableCell(timeColumn, row).getContents().trim().split(timeRegex);
		System.out.println(date[0] + " " + date[1] + "  " + date[2]);
		System.out.println(time[0] + " " + time[1]);
		try {
			Date dato = new Date(Integer.parseInt(date[2])+ 100, Integer.parseInt(date[1])-1,
					Integer.parseInt(date[0]), Integer.parseInt(time[0]), Integer.parseInt(time[1]));
			return dato;
		} catch (Exception e) {
			return null;
		}
	}



	public String getCell(int column, int row) {
		return sheet.getWritableCell(column, row).getContents().trim();
	}



	public boolean setCell(int column, int row, double value) {
		Number cell = new Number(column, row, value); 

		//this.indexExcel = row; //lurer på om dette er riktig....
		try {
			sheet.addCell(cell);
		} catch (RowsExceededException e) {
			e.printStackTrace();
			return false;
		} catch (WriteException e) {
			e.printStackTrace();
			return false;
		}
		System.out.println("lagret:  " +value);
		System.out.println(sheet.getCell(column, row).getContents()	);
		return true;
	}



	public int getRowLength() {
		return sheet.getRows();
	}



	private boolean findExcelFile() {
		final JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new ExcelFilter());


		int returnVal = fc.showDialog(null, "Open excel file with timestamps");
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			this.excelFile = fc.getSelectedFile();
			return true;
		}
		//This is where a real application would open the file.
		if(returnVal == JFileChooser.CANCEL_OPTION) {
			return false;

		}
		return false;
	}


	//lagrer det som er gjort til nå og starter opp en ny fil!
	public void saveExcelFile() {
		closeAndWriteExcel();
		initExcelWrite(this.copyFile);
	}



	public void closeAndWriteExcel() {
		try {
			copy.write();
			copy.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



	private static File findSavePath( File excel, int initialFileLength) {
		String copyString = "- copy";
		int copyInt = 0;
		File copyFile;
		do {
			copyInt ++;
			copyFile = new File(excel.getParentFile().getAbsolutePath() + "\\"+
					excel.getName().substring(0, initialFileLength) + copyString+ copyInt+ ".xls");
		} while(copyFile.exists());

		return copyFile;
	}
}

