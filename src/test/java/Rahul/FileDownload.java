package Rahul;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class FileDownload {

    public static void main(String[] args) throws IOException {
        String fruitName = "Apple";
        String updatedValue = "3565";  // The updated price value
        String FileName = "E:/Eclpise/download (2).xlsx";  // Path to your Excel file

        // Update the price in the Excel file
        int column = getCoulmnNo(FileName, "Price");  // Get the column number for "Price"
        int row = getrowNo(FileName, fruitName);  // Get the row number for the "Apple" fruit
        updatecell(FileName, updatedValue, row, column);  // Update the price in Excel

        // Upload the updated Excel file to the UI
        WebDriver driver = new ChromeDriver();
        driver.manage().window().fullscreen();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://rahulshettyacademy.com/upload-download-test/index.html");
        driver.findElement(By.id("downloadButton")).click();

        WebElement upload = driver.findElement(By.cssSelector("input[type='file']"));
        upload.sendKeys(FileName);

        // Wait for the success message after the file is uploaded
        By toastLocator = By.cssSelector(".Toastify__toast-body div:nth-child(2");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(toastLocator));

        // Now, fetch the price from the UI
        String priceColumn = driver.findElement(By.xpath("//div[text()='Price']")).getAttribute("data-column-id");
        String actualPriceFromUI = driver.findElement(By.xpath("//div[text()='" + fruitName
                + "']/parent::div/parent::div/div[@id='cell-" + priceColumn + "-undefined']")).getText();

        // Print the actual price fetched from UI for debugging
        System.out.println("Actual Price from UI: " + actualPriceFromUI);

        // Assert that the price in the UI matches the updated price in the Excel file
        Assert.assertEquals(actualPriceFromUI, updatedValue, "Updated price is not reflected in the UI correctly!");

        // Close the browser
        driver.quit();
    }

    // Get the column number for the given column name (e.g., "Price")
    public static int getCoulmnNo(String FileName, String ColName) throws IOException {
        FileInputStream fis = new FileInputStream(FileName);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet("Sheet1");
        Iterator<Row> rows = sheet.iterator();
        Row firstrow = rows.next();
        Iterator<Cell> ce = firstrow.cellIterator();

        int k = 1;
        int column = 0;

        while (ce.hasNext()) {
            Cell value = ce.next();
            if (value.getStringCellValue().equalsIgnoreCase(ColName)) {
                column = k;
            }
            k++;
        }

        workbook.close();
        fis.close();

        return column;
    }

    // Get the row number for the given text (e.g., "Apple")
    public static int getrowNo(String FileName, String Text) throws IOException {
        FileInputStream fis = new FileInputStream(FileName);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        int sheets = workbook.getNumberOfSheets();
        int rowindex = -1;

        for (int i = 0; i < sheets; i++) {
            if (workbook.getSheetName(i).equalsIgnoreCase("Sheet1")) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                Iterator<Row> rows = sheet.iterator();
                rows.next();  // Skip the header row
                int k = 1;

                while (rows.hasNext()) {
                    Row re = rows.next();
                    Iterator<Cell> cv = re.cellIterator();
                    while (cv.hasNext()) {
                        Cell cell = cv.next();
                        String cellValue = "";

                        // Check the cell type using CellType enum (String, Numeric, etc.)
                        if (cell.getCellType() == CellType.STRING) {
                            cellValue = cell.getStringCellValue();  // If it's a string
                        } else if (cell.getCellType() == CellType.NUMERIC) {
                            cellValue = String.valueOf(cell.getNumericCellValue());  // If it's numeric, convert to String
                        }

                        // Compare cell value with the input Text
                        if (cellValue.equalsIgnoreCase(Text)) {
                            rowindex = k;
                        }
                        k++;
                    }
                    if (rowindex != -1) {
                        break;
                    }
                }
            }
        }

        workbook.close();
        fis.close();

        return rowindex;
    }

    // Update the price in the Excel file
    public static boolean updatecell(String FileName, String updatedvalue, int row, int col) throws IOException {
        FileInputStream fis = new FileInputStream(FileName);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet("Sheet1");

        Row rowfield = sheet.getRow(row - 1);
        Cell Cellfield = rowfield.getCell(col);
        Cellfield.setCellValue(updatedvalue);

        FileOutputStream fos = new FileOutputStream(FileName);
        workbook.write(fos);
        workbook.close();
        fis.close();
        fos.close();

        return true;
    }
}
