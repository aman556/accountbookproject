package com.example.javaproject.accountbook;

import com.example.javaproject.accountbook.model.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Data> listData;



    public UserExcelExporter(List<Data> listData) {
        this.listData = listData;
        workbook = new XSSFWorkbook();
        sheet =workbook.createSheet("Data");
    }

    private void writeHeaderRow(){
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        Cell cell = row.createCell(0);
        cell.setCellValue("Data Id");
        cell.setCellStyle(style);

         cell = row.createCell(1);
        cell.setCellValue("Name");
        cell.setCellStyle(style);

         cell = row.createCell(2);
        cell.setCellValue("Size");
        cell.setCellStyle(style);

         cell = row.createCell(3);
        cell.setCellValue("Quantity");
        cell.setCellStyle(style);

         cell = row.createCell(4);
        cell.setCellValue("Price");
        cell.setCellStyle(style);

    }
    private void writeDataRows() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for(Data data : listData){
            Row row = sheet.createRow(rowCount++);

            Cell cell = row.createCell(0);
            cell.setCellValue(data.getId());
            sheet.autoSizeColumn(0);
            cell.setCellStyle(style);

             cell = row.createCell(1);
            cell.setCellValue(data.getCompanyName());
            sheet.autoSizeColumn(1);
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(data.getBoxsize());
            sheet.autoSizeColumn(2);
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue(data.getQuantity().toString());
            sheet.autoSizeColumn(3);
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue(data.getBoxRate().toString());
            sheet.autoSizeColumn(4);
            cell.setCellStyle(style);



        }
    }
    public void export(HttpServletResponse response) throws IOException {
        writeHeaderRow();
        writeDataRows();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
