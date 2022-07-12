package com.indi.electricity.mall.utils;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.util.CollectionUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: admin
 */
public class ExcelUtil {

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd hh:mm:ss";
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_TIME_PATTERN);


    /**
     * 数字格式，防止长数字成为科学计数法形式，或者int变为double形式
     */
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0");

    public static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        } else {
            switch (cell.getCellType()) {
                case NUMERIC:
                    //if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    if (DateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        cellValue = SIMPLE_DATE_FORMAT.format(date);
                    } else {
                        //数字型
                        cellValue = DECIMAL_FORMAT.format(cell.getNumericCellValue());
                        cellValue = cellValue.replaceAll("\\.(0)*$", "");
                    }
                    break;
                case STRING:
                    cellValue = String.valueOf(cell.getStringCellValue());
                    break;
                case BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case FORMULA:
                    cellValue = String.valueOf(cell.getCellFormula());
                    break;
                case BLANK:
                    cellValue = "";
                    break;
                case ERROR:
                    cellValue = "非法字符";
                    break;
                default:
                    cellValue = "未知类型";
            }

            return cellValue;
        }
    }

    public static List<List<Object>> getExcelData(LinkedHashMap<String, String> titles, List<Map<String, Object>> data) {
        if (CollectionUtils.isEmpty(data) || CollectionUtils.isEmpty(titles)) {
            return Collections.emptyList();
        }
        List<List<Object>> result = new ArrayList<>();
        data.forEach(map -> {
            List<Object> row = new ArrayList<>();
            titles.forEach((k, v) -> {
                Object cellData = "";
                if (map.containsKey(k)) {
                    cellData = map.get(k);
                }
                if (ObjectUtils.isEmpty(cellData)) {
                } else if (cellData instanceof Date) {
                    cellData = SIMPLE_DATE_FORMAT.format(cellData);
                } else if (cellData instanceof LocalDate) {
                    cellData = ((LocalDate) cellData).format(DateTimeFormatter.ofPattern(DATE_PATTERN));
                } else if (cellData instanceof LocalDateTime) {
                    cellData = ((LocalDateTime) cellData).format(DateTimeFormatter.ofPattern(DATE_PATTERN));
                }
                row.add(cellData);
            });
            result.add(row);

        });
        return result;
    }

    public static Workbook getWorkBook(LinkedHashMap<String, String> titles, List<List<Object>> data) {
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        // 打开压缩功能 防止占用过多磁盘
        workbook.setCompressTempFiles(true);
        Sheet sheet = workbook.createSheet();
        Row titleRow = sheet.createRow(0);
        AtomicInteger column = new AtomicInteger(0);
        titles.forEach((key, val) -> {
            Cell cell = titleRow.createCell(column.getAndIncrement());
            cell.setCellValue(val);
        });
        AtomicInteger rowIndex = new AtomicInteger(1);
        data.forEach(rowData -> {
            Row row = sheet.createRow(rowIndex.getAndIncrement());
            AtomicInteger columnIndex = new AtomicInteger(0);
            rowData.forEach(cellData -> {
                Cell cell = row.createCell(columnIndex.getAndIncrement(), CellType.STRING);
                if (cellData == null) {
                    cell.setCellValue(StringUtils.EMPTY);
                } else {
                    cell.setCellValue(cellData.toString());
                }
            });
        });
        return workbook;
    }
}
