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



	public boolean openExcelFile() {
		if(findExcelFile()) {
			this.excelFileNameLength =this.excelFile.getName().substring(0, excelFile.getName().length()-4).length();
			initExcelWrite(this.excelFile);
			this.ExcelStream = true;
			return true;
		}
		return false;
	}

	public boolean isExcelReady() {
		if(this.ExcelStream) {
			return true;
		}
		else {
			return false;
		}
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



	private int getIndexExcel() {
		return indexExcel;
	}



	private void setIndexExcel(int indexExcel) {
		this.indexExcel = indexExcel;
	}



	public boolean isExcelInputStreamOpen() {
		return this.ExcelStream;
	}



	private Date getDate(int row) {
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



	private String getCell(int column, int row) {
		return sheet.getWritableCell(column, row).getContents().trim();
	}



	private boolean setCell(int column, int row, double value) {
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



	private int getRowLength() {
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
	
	
	private int findMatchingRow(Date imageDate) throws Exception {

		for(int i = this.getIndexExcel(); i< this.getRowLength(); i++) {
			if(imageDate.equals(this.getDate(i))) {
				this.setIndexExcel(i);
				System.out.println("setter excel index til, og returerer " + i);
				return i;
			}
			else if(imageDate.before(this.getDate(i))) {
				Object[] valg={this.getDate(i-1).toLocaleString(), this.getDate(i).toLocaleString(), "Ikke log verdi"};
				String message = "Finner ikke nøyaktig sted å legge verdiene i excelfila \n Hvordan vil du lagre verdi";
				int result = JOptionPane.showOptionDialog(null, message, "tittel",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, valg, valg[0]);
				//HER KAN DET BLI STORE FEIL OM i == 0 og man prøver å finne i -1.. .Dette må fikses!
				
				if(result == JOptionPane.YES_OPTION) {
					return i-1;
				}
				else if(result == JOptionPane.NO_OPTION) {
					return i;
				}
				else {
					return -1;
				}
			}
		}
		return -1;
	}
	
	
	public static int findFirstMatchingRow(ExcelCommunication excel, ImageFolderModel images) {
		Date imageDate = images.getDate(0);
		Date excelDate = excel.getDate(0);

		if(excelDate.after(imageDate)) {
			System.out.println("yupp");
			for(int i = images.getIndex(); i< images.getImageCount(); i++) {
				imageDate = images.getDate(i);
				if(excelDate.equals(imageDate) || excelDate.after(imageDate)) {
					System.out.println(i);
					images.setIndex(i);
					return i;
				}
			}
		}
		else {
			try {
				int j = excel.findMatchingRow(imageDate);
				if(j>=0) {
					excel.setIndexExcel(j);
					images.setIndex(j);
				}
				else {
					images.findPictureFiles();
				}
				return j;
			} catch (Exception e) {
				return -1;
				
			}
		}
		return 0;
	}
	
	
	public void logValue(ImageFolderModel images, double valueOW, double valueOG) {
			int row = -1;
			try {
				row = findMatchingRow(images.getDate(images.getIndex()));
				System.out.println("logvalue rad" +  row);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(row < 0) {
				return;
			}
			
			this.setCell(WO_COLUMN, row, valueOW);
			this.setCell(OG_COLUMN, row, valueOG);
	}
}

