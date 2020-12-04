package com.platform.service;

import com.platform.entity.Student;
import org.apache.commons.fileupload.FileItem;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * @author luxx
 */
@Service
public class ExcelService {

    public List<Student> readXlsx(FileItem file,int competitionId) throws IOException {
        InputStream is = file.getInputStream();
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        Student student = null;
        List<Student> list = new ArrayList<Student>();
        // Read the Sheet
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    student = new Student(formatCell(xssfRow.getCell(0)).toString(),
                            formatCell(xssfRow.getCell(1)).toString(),
                            formatCell(xssfRow.getCell(2)).toString(),
                            formatCell(xssfRow.getCell(3)).toString(),
                            formatCell(xssfRow.getCell(4)).toString(),
                            formatCell(xssfRow.getCell(5)).toString(),
                            formatCell(xssfRow.getCell(6)).toString(),
                            formatCell(xssfRow.getCell(7)).toString(),
                            formatCell(xssfRow.getCell(8)).toString(),
                            formatCell(xssfRow.getCell(9)).toString(),
                            formatCell(xssfRow.getCell(10)).toString(),
                            formatCell(xssfRow.getCell(11)).toString(),
                            formatCell(xssfRow.getCell(12)).toString(),
                            formatCell(xssfRow.getCell(13)).toString(),
                            formatCell(xssfRow.getCell(14)).toString(),
                            formatCell(xssfRow.getCell(15)).toString(),competitionId,false);
                    list.add(student);
                }
            }
        }
        return list;
    }

    public List<Student> readXls(FileItem file,int competitionId) throws IOException {
        InputStream is = file.getInputStream();
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
         Student student = null;
        List<Student> list = new ArrayList<Student>();
        // Read the Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                    student = new Student(formatCell(hssfRow.getCell(0)).toString(),
                            formatCell(hssfRow.getCell(1)).toString(),
                            formatCell(hssfRow.getCell(2)).toString(),
                            formatCell(hssfRow.getCell(3)).toString(),
                            formatCell(hssfRow.getCell(4)).toString(),
                            formatCell(hssfRow.getCell(5)).toString(),
                            formatCell(hssfRow.getCell(6)).toString(),
                            formatCell(hssfRow.getCell(7)).toString(),
                            formatCell(hssfRow.getCell(8)).toString(),
                            formatCell(hssfRow.getCell(9)).toString(),
                            formatCell(hssfRow.getCell(10)).toString(),
                            formatCell(hssfRow.getCell(11)).toString(),
                            formatCell(hssfRow.getCell(12)).toString(),
                            formatCell(hssfRow.getCell(13)).toString(),
                            formatCell(hssfRow.getCell(14)).toString(),
                            formatCell(hssfRow.getCell(15)).toString(),competitionId,false);
                    list.add(student);
                }
            }
        }
        return list;
    }
    public String formatCell(HSSFCell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_NUMERIC:

                //日期格式的处理
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    return sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                }
                Object inputValue = null;// 单元格值
                Long longVal = Math.round(cell.getNumericCellValue());
                Double doubleVal = cell.getNumericCellValue();
                if(Double.parseDouble(longVal + ".0") == doubleVal){   //判断是否含有小数位.0
                    inputValue = longVal;
                }
                else{
                    inputValue = doubleVal;
                }
                return String.valueOf(inputValue);

            //字符串
            case HSSFCell.CELL_TYPE_STRING:
                return cell.getStringCellValue();

            // 公式
            case HSSFCell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();

            // 空白
            case HSSFCell.CELL_TYPE_BLANK:
                return "";

            // 布尔取值
            case HSSFCell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue() + "";

            //错误类型
            case HSSFCell.CELL_TYPE_ERROR:
                return cell.getErrorCellValue() + "";
        }

        return "";
    }

    public String formatCell(XSSFCell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case XSSFCell.CELL_TYPE_NUMERIC:

                //日期格式的处理
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    return sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                }
                Object inputValue = null;// 单元格值
                Long longVal = Math.round(cell.getNumericCellValue());
                Double doubleVal = cell.getNumericCellValue();
                if(Double.parseDouble(longVal + ".0") == doubleVal){   //判断是否含有小数位.0
                    inputValue = longVal;
                }
                else{
                    inputValue = doubleVal;
                }
                return String.valueOf(inputValue);

            //字符串
            case XSSFCell.CELL_TYPE_STRING:
                return cell.getStringCellValue();

            // 公式
            case XSSFCell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();

            // 空白
            case XSSFCell.CELL_TYPE_BLANK:
                return "";

            // 布尔取值
            case XSSFCell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue() + "";

            //错误类型
            case XSSFCell.CELL_TYPE_ERROR:
                return cell.getErrorCellValue() + "";
        }

        return "";
    }
}