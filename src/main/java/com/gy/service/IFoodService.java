package com.gy.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.gy.entity.Food;
import com.baomidou.mybatisplus.service.IService;
import com.gy.utils.ExcelUtil;
import com.gy.vo.ImportResponseVO;
import com.sun.rowset.internal.Row;
import freemarker.template.utility.NumberUtil;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 范炳杰
 * @since 2019-11-18
 */
public interface IFoodService extends IService<Food> {

    /**
     * 分页查询
     * @param page
     * @param name
     * @return
     */
    Page<Food> queryPage(Page<Food> page , String name);


    /**
     * 新增菜单
     * @param food
     * @return
     */
    public Boolean addFood(Food food);

    /**
     * 修改
     * @param id
     * @return
     */
    public Food queryById(String id);

    /**
     * 修改
     * @param food
     * @return
     */
    public Boolean editFood(Food food);


    /**
     * 删除
     * @param id
     * @return
     */
    public Boolean deleteFood(Integer id);

    /**
     * 导出
     * @param name
     * @param response
     * @return
     */
    public Boolean export(String name, HttpServletResponse response) ;


    public Boolean exportTemplate(HttpServletResponse response) ;

    /**
     * 导入导出
     * @param request
     * @param response
     * @param inputStream
     * @return
     */
    public ImportResponseVO importExcel(HttpServletRequest request,
                                        HttpServletResponse response, InputStream inputStream);

}
