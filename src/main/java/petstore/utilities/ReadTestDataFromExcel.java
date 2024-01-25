package petstore.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class ReadTestDataFromExcel {
	
	@DataProvider(name="getData")
	public Object[][] getDataFromExcel() throws IOException
	{
		String excelFilePath = System.getProperty("user.dir")+ File.separator + "data" + File.separator + "UserModuleData.xlsx";
		
		FileInputStream fis= new FileInputStream(excelFilePath);
		XSSFWorkbook wb =new XSSFWorkbook(fis);
		XSSFSheet sheet=wb.getSheetAt(0);
		int rowCount=sheet.getLastRowNum();
		int colCount=sheet.getRow(0).getLastCellNum();
		System.out.println("Total number of Rows: "+rowCount+" Total number of Columns: "+colCount);
		Object[][] data =new Object[rowCount][1];
		for(int i=0;i<rowCount;i++)
		{
			HashMap<String,String> dataMap= new HashMap<String,String>();
			for(int j=0;j<colCount;j++)
			{
				dataMap.put(sheet.getRow(0).getCell(j).getStringCellValue(),sheet.getRow(i+1).getCell(j).getStringCellValue());
			}
			data[i][0]=dataMap;
		}
		wb.close();
		return data;
	}
	
	@DataProvider(name="getStoreData")
	public Object[][] getDataFromExcel2() throws IOException
	{
		String excelFilePath = System.getProperty("user.dir")+ File.separator + "data" + File.separator + "StoreModuleData.xlsx";
		
		FileInputStream fis= new FileInputStream(excelFilePath);
		XSSFWorkbook wb =new XSSFWorkbook(fis);
		XSSFSheet sheet=wb.getSheetAt(0);
		int rowCount=sheet.getLastRowNum();
		int colCount=sheet.getRow(0).getLastCellNum();
		System.out.println("Total number of Rows: "+rowCount+" Total number of Columns: "+colCount);
		Object[][] data =new Object[rowCount][1];
		for(int i=0;i<rowCount;i++)
		{
			HashMap<String,String> dataMap= new HashMap<String,String>();
			for(int j=0;j<colCount;j++)
			{
				dataMap.put(sheet.getRow(0).getCell(j).getStringCellValue(),sheet.getRow(i+1).getCell(j).getStringCellValue());
			}
			data[i][0]=dataMap;
		}
		wb.close();
		return data;
	}
	
	@DataProvider(name="getPetData")
	public Object[][] getDataFromExcel3() throws IOException
	{
		String excelFilePath = System.getProperty("user.dir")+ File.separator + "data" + File.separator + "PetModuleData.xlsx";
		
		FileInputStream fis= new FileInputStream(excelFilePath);
		XSSFWorkbook wb =new XSSFWorkbook(fis);
		XSSFSheet sheet=wb.getSheetAt(0);
		int rowCount=sheet.getLastRowNum();
		int colCount=sheet.getRow(0).getLastCellNum();
		System.out.println("Total number of Rows: "+rowCount+" Total number of Columns: "+colCount);
		Object[][] data =new Object[rowCount][1];
		for(int i=0;i<rowCount;i++)
		{
			HashMap<String,String> dataMap= new HashMap<String,String>();
			for(int j=0;j<colCount;j++)
			{
				dataMap.put(sheet.getRow(0).getCell(j).getStringCellValue(),sheet.getRow(i+1).getCell(j).getStringCellValue());
			}
			data[i][0]=dataMap;
		}
		wb.close();
		return data;
	}
	

}
