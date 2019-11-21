package com.gy.utils;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.RegionUtil;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author: fanbingjie
 * @date:  2019-11-13
 * @description: 文件导入导出工具类
 */
public class ExcelUtil {

	/**
     * 向excel文件特定的工作簿添加若干行数据
     *s
     * @param sheetName    工作簿名字
     * @param columns      列名行(工作簿中的第二行)
     * @param dataList     数据列表
     * @param outputStream 输出流
     */
    public static boolean createExcel(String sheetName, String[] columns, List<List<String>> dataList, OutputStream outputStream) {
        // 创建excel表
        HSSFWorkbook wb = new HSSFWorkbook();
        // 创建excel表中的工作簿
        Sheet sheet = wb.createSheet(sheetName);
        // 标题行和列名行样式
        HSSFCellStyle headCellStyle = getHeadCellStyle(wb, "楷体", (short)16, HSSFColorPredefined.LIGHT_GREEN.getIndex());
        // 数据行样式
        HSSFCellStyle infoCellStyle = getInfoCellStyle(wb, "@");
        // 向工作簿添加标题行(工作簿中的第一行)
        Row titleRow = sheet.createRow(0);
        // 设置标题行高度
        titleRow.setHeightInPoints(30);
        // 设置标题行内容
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(sheetName);
        // 设置标题行单元格样式
        titleCell.setCellStyle(headCellStyle);
        // 合并标题行单元格，构造参数依次表示起始行、截至行、起始列、 截至列
        if(columns.length > 1){
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columns.length - 1));
        }
        // 向工作簿添加列名行(工作簿中的第二行)
        Row columnRow = sheet.createRow(1);
        // 设置列名行高度
        columnRow.setHeightInPoints(23);
        // 设置列名行内容
        for(int i = 0; i < columns.length; i++){
            Cell headCell = columnRow.createCell(i);
            headCell.setCellValue(columns[i]);
            // 设置列名行单元格样式
            headCell.setCellStyle(headCellStyle);
        }
        // 根据列名数组的长度确定需要设置多少列的宽度
        for (int i = 0; i < columns.length; i++) {
            // 设置列宽度，以256为单位，20表示设置为20个字的宽度
            sheet.setColumnWidth(i, NumberUtil.getInt(8 * 3 * 256 * 1.2));
        }
        if (dataList != null) {
            // 向工作簿添加数据列表
            for (List<String> data : dataList) {
                Row row = sheet.createRow(sheet.getLastRowNum() + 1);
                // 设置数据行高度
                row.setHeightInPoints(23);
                for (int i = 0; i < columns.length; i++) {
                    // 设置数据行内容
                    Cell cell = row.createCell(i);
                    cell.setCellValue(data.get(i));
                    // 设置数据行单元格样式
                    cell.setCellStyle(infoCellStyle);
                }
            }
        }
        try {
            // 写入文件流，只在内存中
            wb.write(outputStream);
            wb.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 设置标题行单元格格式
     * @param workbook - excel面板对象
     * @param fontType - 字体类型
     * @param fontSize - 字体大小
     * @param backgroundColor - 背景颜色
     * @return
     */
    public static HSSFCellStyle getHeadCellStyle(HSSFWorkbook workbook, String fontType, short fontSize, short backgroundColor){
        //列名添加背景色
        HSSFCellStyle titleCellStyle = workbook.createCellStyle();
        //设置背景样式
        titleCellStyle.setFillForegroundColor(backgroundColor);  //设置背景色
        titleCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleCellStyle.setAlignment(HorizontalAlignment.CENTER);  //设置水平居中
        titleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);  //设置垂直居中
        //设置边框样式
        titleCellStyle.setBorderBottom(BorderStyle.THIN);  //设置底边框
        titleCellStyle.setBottomBorderColor(HSSFColorPredefined.BLACK.getIndex());
        titleCellStyle.setBorderLeft(BorderStyle.THIN);  //设置左边框
        titleCellStyle.setLeftBorderColor(HSSFColorPredefined.BLACK.getIndex());
        titleCellStyle.setBorderRight(BorderStyle.THIN);  //设置右边框
        titleCellStyle.setRightBorderColor(HSSFColorPredefined.BLACK.getIndex());
        titleCellStyle.setBorderTop(BorderStyle.THIN);  //设置顶边框
        titleCellStyle.setTopBorderColor(HSSFColorPredefined.BLACK.getIndex());
        //设置字体样式
        HSSFFont titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints(fontSize);// 设置字体大小
        titleFont.setFontName(fontType);
        titleFont.setBold(true);
        titleCellStyle.setFont(titleFont);

        return titleCellStyle;
    }

    /**
     * 设置内容单元格格式
     * @param workbook - excel面板对象
     * @param formatStyle - @:表示文本
     * @return
     */
    public static HSSFCellStyle getInfoCellStyle(HSSFWorkbook workbook, String formatStyle){
        //单元格添加边框
        HSSFCellStyle cellStyleNormal = workbook.createCellStyle();
        cellStyleNormal.setAlignment(HorizontalAlignment.CENTER);  //设置水平居中
        cellStyleNormal.setVerticalAlignment(VerticalAlignment.CENTER);  //设置垂直居中
        cellStyleNormal.setBorderBottom(BorderStyle.THIN);
        cellStyleNormal.setBottomBorderColor(HSSFColorPredefined.BLACK.getIndex());
        cellStyleNormal.setBorderLeft(BorderStyle.THIN);
        cellStyleNormal.setLeftBorderColor(HSSFColorPredefined.BLACK.getIndex());
        cellStyleNormal.setBorderRight(BorderStyle.THIN);
        cellStyleNormal.setRightBorderColor(HSSFColorPredefined.BLACK.getIndex());
        cellStyleNormal.setBorderTop(BorderStyle.THIN);
        cellStyleNormal.setTopBorderColor(HSSFColorPredefined.BLACK.getIndex());
        //设置CELL格式  
        HSSFDataFormat format = workbook.createDataFormat();
        cellStyleNormal.setDataFormat(format.getFormat(formatStyle));

        return cellStyleNormal;
    }

    /**
     * 设置合并单元格边框
     * @param cra
     * @param sheet
     */
    public static void setRegoinBorder(CellRangeAddress cra, Sheet sheet){
        // 使用RegionUtil类为合并后的单元格添加边框
        RegionUtil.setBorderBottom(BorderStyle.THIN, cra, sheet); // 下边框
        RegionUtil.setBorderLeft(BorderStyle.THIN, cra, sheet); // 左边框
        RegionUtil.setBorderRight(BorderStyle.THIN, cra, sheet); // 有边框
        RegionUtil.setBorderTop(BorderStyle.THIN, cra, sheet); // 上边框
    }

    /**
     * 判断Excel模板是否正确，如果属性行（列名行）不为隐藏列或列名行中有单元格为空，则抛出异常
     * @param sheet
     * @param columnList - 列名数组
     * @param columnIndex - 列名行索引（从0开始）
     * @throws Exception 
     */
    public static boolean checkExcel(Sheet sheet,List<String> columnList, int columnIndex) throws Exception {
        try {
            Row signRow = sheet.getRow(columnIndex);  //获取列名行
            if(signRow.getZeroHeight()==true){  //判断是否隐藏列，高度是否为0
                return false;
            }
            int clos = signRow.getPhysicalNumberOfCells();  //得到所有的列
            //取出标记行的内容
            for (int i = 0; i < clos; i++) {
                Cell cell = signRow.getCell(i);
                if (cell == null || cell.getStringCellValue() == null || cell.getStringCellValue().equals("")) {  //判断每个单元格对象是否为空，内容是否为空
                    return false;
                }
                if (!cell.getStringCellValue().equals(columnList.get(i))) {   //判断单元格内容与模板的列元素是否一致
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("模板处理失败");
        }
    }

    /**
     * 设置下拉列
     * @param sheet
     * @param regions - 下拉列表对象
     * @param datas - 下拉数据源
     */
    public static void setComboColumn(Sheet sheet, CellRangeAddressList regions, String[] datas){
        //创建下拉列表数据
        DVConstraint constraint = DVConstraint.createExplicitListConstraint(datas);
        //绑定
        HSSFDataValidation dataValidation = new HSSFDataValidation(regions, constraint);
        sheet.addValidationData(dataValidation);
    }

    /**
     * 设置下拉列
     * @param sheet
     * @param regions - 下拉列表对象
     * @param strFormula - 数据有效性表达式
     */
    public static void setComboColumn(Sheet sheet, CellRangeAddressList regions, String strFormula){
        //创建下拉列表数据
        DVConstraint constraint = DVConstraint.createFormulaListConstraint(strFormula);
        //绑定
        HSSFDataValidation dataValidation = new HSSFDataValidation(regions, constraint);
        dataValidation.createErrorBox("提示", "输入值无效，请在下拉框中选取合适的值");
        sheet.addValidationData(dataValidation);
    }
    
    /**
     * 设置日期格式校验
     * @param sheet
     * @param regions - 下拉列表对象
     * @param strFormula - 数据有效性表达式
     */
    public static void setDataValidation(Sheet sheet, CellRangeAddressList regions, String beginDate, String endDate, String dateFormat){
        //创建下拉列表数据
        DVConstraint constraint = DVConstraint.createDateConstraint(DVConstraint.OperatorType.BETWEEN, beginDate, endDate, dateFormat);
        //绑定
        HSSFDataValidation dataValidation = new HSSFDataValidation(regions, constraint);
        dataValidation.createErrorBox("提示", "输入值无效，请输入格式为" + dateFormat + "，介于" + beginDate + "到" + endDate + "之间的日期");
        sheet.addValidationData(dataValidation);
    }
    
    /**
     * 设置并获取字体
     * @param workbook
     * @param color - 字体颜色
     * @param fontType - 字体类型
     * @param fontSize - 字体大小
     * @param isBold - 是否加粗
     * @return
     */
    public static HSSFFont getFont(HSSFWorkbook workbook, short color, String fontType, short fontSize, boolean isBold){
        HSSFFont font = (HSSFFont) workbook.createFont();
        font.setColor(color);  //设置字体颜色
        font.setFontHeightInPoints(fontSize);// 设置字体大小
        font.setFontName(fontType);  //设置字体类型
        font.setBold(isBold);  //设置字体加粗
        return font;
    }
    
    /**
     * 判断是否为空行
     * @param row
     * @return
     */
    public static boolean isRowEmpty(Row row) {
        if(row == null)
            return true;
        int cellCount = row.getLastCellNum() - row.getFirstCellNum();
        int emptyCount = 0;
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if(cell == null || cell.getCellTypeEnum() == CellType.BLANK || 
                    StringUtils.isEmpty(cell.getCellTypeEnum() == CellType.STRING ? cell.getStringCellValue() : (""+cell.getNumericCellValue())))
                emptyCount++;
        }
        if(cellCount == emptyCount){
            return true;
        }else{
            return false;
        }
     }

}
