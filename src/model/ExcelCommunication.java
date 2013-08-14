package model;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import filter.ExcelFilter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

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

	public static int DATE_COLUMN = 0;
	public static int TIME_COLUMN = 1;
	public static int OG_COLUMN = 2;
	public static int WO_COLUMN =3;
	public static int FIRST_ENTRY_IN_EXCEL_FILE = 0;

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





	public ExcelCommunication() {
		this.dateColumn = DATE_COLUMN;
		this.timeColumn = TIME_COLUMN;
	}


	public void logValue(ImageFolderModel images, double valueOW, double valueOG) {
		int row = -1;
		System.out.println(images.getIndex() + "excelindex " + getIndexExcel());
		try {
			row = findMatchingRow(images.getDate(images.getIndex()));

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(row < 0) {
			JOptionPane.showMessageDialog(null,
					"Fant ikke sted i excel å skrive verdiene til. \n Sjekk excel-fil!",
					"Rad i excel ikke funnet",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		this.setCell(WO_COLUMN, row, valueOW);
		this.setCell(OG_COLUMN, row, valueOG);
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



	private void setIndexExcel(int indexExcel) {
		this.indexExcel = indexExcel;
	}


	private String getCell(int column, int row) {
		return sheet.getWritableCell(column, row).getContents().trim();
	}


	private boolean setCell(int column, int row, double value) {
		Number cell = new Number(column, row, value); 

		try {
			sheet.addCell(cell);
		} catch (RowsExceededException e) {
			e.printStackTrace();
			return false;
		} catch (WriteException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}


	public boolean isExcelInputStreamOpen() {
		return this.ExcelStream;
	}


	public Date getCurrentDate() {
		return this.getDate(this.getIndexExcel());
	}


	private Date getDate(int row) {

		String[] date = sheet.getWritableCell(dateColumn, row).getContents().trim().split(dateRegex);
		String[] time = sheet.getWritableCell(timeColumn, row).getContents().trim().split(timeRegex);
		try {
			Date dato = new Date(Integer.parseInt(date[2])+ 100, Integer.parseInt(date[1])-1,
					Integer.parseInt(date[0]), Integer.parseInt(time[0]), Integer.parseInt(time[1]));
			return dato;
		} catch (Exception e) {
			return null;
		}
	}


	private int getRowLength() {
		return sheet.getRows();
	}


	public boolean isExcelReady() {
		if(this.ExcelStream) {
			return true;
		}
		else {
			return false;
		}
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


	private int findMatchingRow(Date imageDate) throws Exception {
		int searchStartRow = this.getIndexExcel();
		if(imageDate.before(this.getDate(searchStartRow))) {
			searchStartRow = 0;
		}
		System.out.println(imageDate.toLocaleString() + "exceldate " + this.getDate(indexExcel).toLocaleString());
		for(int i = searchStartRow; i< this.getRowLength(); i++) {
			//System.out.println(i);
			if(imageDate.equals(this.getDate(i))) {
				this.setIndexExcel(i);

				return i;
			}
			else if(imageDate.before(this.getDate(i))) {
				if(i <= FIRST_ENTRY_IN_EXCEL_FILE) {
					System.out.println("Ops første entry i loggefila!");
				}
				else {
					return entryPlacementInFileDialog(this.getDate(i-1).toLocaleString(), i-1, this.getDate(i).toLocaleString(), i);	
				}
			}
			else {
				
			}
		}
		return -1;
	}


	private static int entryPlacementInFileDialog(String before, int beforeId,  String after, int afterId) {
		Object[] valg={before, after, "Ikke log verdi"};
		String message = "Finner ikke nøyaktig sted å legge verdiene i excelfila \n Hvordan vil du lagre verdi";
		int result = JOptionPane.showOptionDialog(null, message, "tittel",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, valg, valg[0]);

		if(result == JOptionPane.YES_OPTION) {
			return beforeId;
		}
		else if(result == JOptionPane.NO_OPTION) {
			return afterId;
		}
		else {
			return -1;
		}
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
}

