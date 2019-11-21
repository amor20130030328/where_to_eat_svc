package com.gy.service.impl;

import com.gy.vo.ImportResponseVO;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import com.baomidou.mybatisplus.plugins.Page;
import com.gy.entity.Food;
import com.gy.mapper.FoodMapper;
import com.gy.service.IFoodService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gy.utils.CommonUtils;
import com.gy.utils.ExcelUtil;
import com.gy.utils.NumberUtil;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 范炳杰
 * @since 2019-11-18
 */
@Service
public class FoodServiceImpl extends ServiceImpl<FoodMapper, Food> implements IFoodService {

    @Autowired
    private FoodMapper foodMapper;


    private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    public Page<Food> queryPage(Page<Food> page , String name){

        List<Food> list = foodMapper.queryPage( page,name );
        page.setRecords( list );
        return page;
    }

    @Override
    public Boolean addFood(Food food) {
        return foodMapper.addFood(food);
    }

    @Override
    public Food queryById(String id){
        Food food = foodMapper.queryById(id);
        return food;
    }

    @Override
    public Boolean editFood(Food food) {
        return foodMapper.editFood(food);
    }

    @Override
    public Boolean deleteFood(Integer id) {
        return foodMapper.deleteFood(id);
    }

    @Override
    public Boolean export(String name, HttpServletResponse response) {

        List<Food> list = foodMapper.queryPage(new Page<Food>(), name);

        HSSFWorkbook workbook = new HSSFWorkbook();  //创建工作簿
        Sheet sheet = workbook.createSheet();   //创建工作表
        HSSFCellStyle titleCellStyle = ExcelUtil.getHeadCellStyle(workbook, "楷体", (short)16, HSSFColorPredefined.LIGHT_GREEN.getIndex());  //标题行样式
        HSSFCellStyle infoCellStyle = ExcelUtil.getInfoCellStyle(workbook,"@");			//设置单元格样式
        String [] columnNames = {"菜单名","优先级"};
        String [] columnValues = {"name","priority"};
        Row titleRow = sheet.createRow(0);   //标题行
        //设置行高
        titleRow.setHeightInPoints(30);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("菜单栏目");
        titleCell.setCellStyle(titleCellStyle);    //设置标题行样式

        if(columnNames.length > 1 ){
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnNames.length - 1));	//合并单元格 构造参数依次表示起始行，截至行，起始列， 截至列
        }

        //列名行
        Row headRow = sheet.createRow(1);
        headRow.setHeightInPoints(23);       //设置列名行高度
        //把列名赋值到单元格
        for (int i = 0; i< columnNames.length ;i++) {
            Cell headCell = headRow.createCell(i);
            headCell.setCellValue(columnNames[i]);		//将列名赋值到单元格
            headCell.setCellStyle(titleCellStyle);      //设置标题列样式
        }

        //调整列宽度
        for (int i = 0; i< columnNames.length ;i++) {
            sheet.setColumnWidth(i, NumberUtil.getInt(7*3*256*1.2));
        }

        for(int i=0; i < list.size() ;i++){
            Food food = list.get(i);
            Row row = sheet.createRow(i+2);
            row.setHeightInPoints(23);     //设置行高
            for(int j=0;j < columnNames.length ; j++){
                Cell cell = row.createCell(j);
                cell.setCellStyle(infoCellStyle);
                String value = CommonUtils.getPropertyValue(food, columnValues[j]);
                cell.setCellValue(value);
            }
        }

        try {
            OutputStream out = response.getOutputStream();
            response.reset();
            workbook.write(out);
            workbook.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean exportTemplate(HttpServletResponse response) {

        HSSFWorkbook workbook = new HSSFWorkbook();  //创建工作簿
        Sheet sheet = workbook.createSheet();   //创建工作表
        sheet.setDefaultRowHeightInPoints(23);  //设置行默认高度
        HSSFCellStyle titleCellStyle = ExcelUtil.getHeadCellStyle(workbook, "楷体", (short)16, HSSFColorPredefined.LIGHT_GREEN.getIndex());  //标题行样式
        String [] columnNames = {"*菜单名","*优先级"};
        Row titleRow = sheet.createRow(0);   //标题行
        //设置行高
        titleRow.setHeightInPoints(30);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("菜单栏目");
        titleCell.setCellStyle(titleCellStyle);    //设置标题行样式

        if(columnNames.length > 1 ){
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnNames.length - 1));	//合并单元格 构造参数依次表示起始行，截至行，起始列， 截至列
        }

        //列名行
        Row headRow = sheet.createRow(1);
        headRow.setHeightInPoints(23);       //设置列名行高度
        //把列名赋值到单元格
        for (int i = 0; i< columnNames.length ;i++) {
            Cell headCell = headRow.createCell(i);
            headCell.setCellValue(columnNames[i]);		//将列名赋值到单元格
            headCell.setCellStyle(titleCellStyle);      //设置标题列样式
        }

        //调整列宽度
        for (int i = 0; i< columnNames.length ;i++) {
            sheet.setColumnWidth(i, NumberUtil.getInt(7*3*256*1.2));
        }

        try {
            OutputStream out = response.getOutputStream();
            response.reset();
            workbook.write(out);
            workbook.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public ImportResponseVO importExcel(HttpServletRequest request,
                                        HttpServletResponse response, InputStream inputStream) {

        ImportResponseVO importResponseVO = new ImportResponseVO();
        HSSFWorkbook workbook = null;

        try {
            workbook = new HSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            List<String> columnList = Arrays.asList("*菜单名","*优先级");
            List<String> columnNameList = Arrays.asList("name","priority");
            //模板检查
            if(!ExcelUtil.checkExcel(sheet, columnList, 1)){
                importResponseVO.setSuccess(false);
                importResponseVO.setMessage("导入模板错误");
                if(workbook != null){
                    workbook.close();
                }
                return importResponseVO;
            }

            int columnCount = sheet.getRow(1).getLastCellNum(); // 导入的列数目，即列名行的有效列数

            List<Food> insertList = new ArrayList<Food>();
            // 在需要导入的列索引中校验必填项是否填写
            for (int i = 2; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                Map<Integer, String> messageMap = new HashMap<Integer, String>();
                boolean isInsert = true;
                Food food = new Food();
                if (!ExcelUtil.isRowEmpty(row)) { // 判断空行
                    for (int c = 0; c < columnCount; c++) {
                        Cell cell = row.getCell(c);
                        String columnId = columnNameList.get(c);
                        String title = sheet.getRow(1).getCell(c).getStringCellValue();
                        if (columnList.contains(title)) { // 判断该列是否在导入范围内
                            String value = "";
                            if (title.indexOf("*") >= 0) { // 判断是否必填
                                if (cell == null) { // 判断单元格对象是否为null
                                    messageMap.put(messageMap.size() == 0 ? 0 : messageMap.size(), "第" + (i + 1) + "行列<" + title + ">为必填项，不能留空");
                                    isInsert = false;
                                } else {
                                    switch (cell.getCellTypeEnum()) {
                                        case STRING:
                                            value = cell.getStringCellValue();
                                            break;
                                        case NUMERIC:// cell为数字类型
                                            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                                Date date = cell.getDateCellValue();
                                                value = sdf.format(date);
                                            } else {
                                                DecimalFormat df = new DecimalFormat("0");
                                                value = df.format(cell
                                                        .getNumericCellValue());
                                            }
                                            break;
                                        default:
                                            break;
                                    }

                                    if ("".equals(value)) {
                                        messageMap.put(messageMap.size() == 0 ? 0 : messageMap.size(), "第" + (i + 1) + "行列<" + title + ">为必填项，不能留空");
                                        isInsert = false;
                                    }
                                }
                            }else {
                                if (cell != null) {
                                    switch (cell.getCellTypeEnum()) {
                                        case STRING:
                                            value = cell.getStringCellValue();
                                            break;
                                        case NUMERIC:// cell为数字类型
                                            if (HSSFDateUtil
                                                    .isCellDateFormatted(cell)) {
                                                Date date = cell.getDateCellValue();
                                                value = sdf.format(date);
                                            } else {
                                                DecimalFormat df = new DecimalFormat(
                                                        "0");
                                                value = df.format(cell
                                                        .getNumericCellValue());
                                            }
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }

                            if(!StringUtils.isEmpty(value)) {

                                if(isInsert) {
                                    CommonUtils.setPropertyValue(food, columnId,value);
                                }
                            }


                        }
                    }
                }

                if(isInsert) {
                    insertList.add(food);
                }
            }

            batchInsert(insertList);
            return importResponseVO;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    private Boolean batchInsert(List<Food> list) {


        int step = 20;
        int time = list.size() / step;
        int last = list.size() %step;
        if(time > 0) {
            for(int i = 0 ; i < time ; i++) {
                foodMapper.batchInsert(list.subList(step * i , (i+1) * step ));
            }
        }

        if(last > 0) {
            foodMapper.batchInsert(list.subList(time * step , time * step + last ));
        }

        return true;
    }



}
