import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class DataDriven {
    public ArrayList<String> getData(String testCase) throws IOException {
        ArrayList<String> data = new ArrayList<String>();
        //fileInputStream
        FileInputStream file = new FileInputStream("C:\\Users\\Natan\\Downloads\\demoData.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        //select sheet
        int sheets = workbook.getNumberOfSheets();
        for(int i=0; i<sheets; i++){
            if(workbook.getSheetName(i).equalsIgnoreCase("testdata")){
                XSSFSheet sheet = workbook.getSheetAt(i);
                //find column
                Iterator<Row> rows = sheet.iterator();
                Row firstRow = rows.next();
                Iterator<Cell> ce = firstRow.cellIterator();
                int k=0;
                int column=0;
                while (ce.hasNext()){
                    Cell value = ce.next();
                    if(value.getStringCellValue().equalsIgnoreCase("tesccases")){
                        column = k;
                    }
                    k++;
                }
                //find purchase row
                while (rows.hasNext()){
                    Row r=rows.next();
                    if(r.getCell(column).getStringCellValue().equalsIgnoreCase(testCase)){
                        Iterator<Cell> cv = r.cellIterator();
                        while(cv.hasNext()){
                            Cell c = cv.next();
                            if(c.getCellType()== CellType.STRING) {
                                data.add(c.getStringCellValue());
                            }else{

                                data.add(NumberToTextConverter.toText(c.getNumericCellValue()));
                            }
                        }
                    }
                }
            }
        }
        return data;
    }

    public static void main(String[] args) throws IOException {

    }
}
