package Rahul;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Dataprovider {
	
	DataFormatter formatter=new DataFormatter();
			@Test (dataProvider="Test")
	public void Testcasesdata(String Greeting, String Communication, String id)
	{
		System.out.println(Greeting+Communication+id);
		
	}
	@DataProvider(name="Test")
public Object[][] getdata() throws IOException
{
		  FileInputStream fis = new FileInputStream("E:\\Notes\\DataDriven.xlsx");
	        XSSFWorkbook workbook = new XSSFWorkbook(fis);
	        XSSFSheet sheet=workbook.getSheetAt(0);
	       int rowscount= sheet.getPhysicalNumberOfRows();
	      XSSFRow row=sheet.getRow(0);
	     int columncount=  row.getLastCellNum();
	     Object data[][]=new Object[rowscount-1][columncount];
	     for(int i=0;i<rowscount-1;i++)
	     {
	    	 row=sheet.getRow(i+1);
	    	 for(int j=0;j<columncount;j++)
	    	 {
	    		 XSSFCell cell=row.getCell(j);
	    		 data[i][j]=formatter.formatCellValue(cell);
	    		 
	    	 }
	     
	     
	      
	       
	       
	        
	        
	     }
		return data;
	
}
}
