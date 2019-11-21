package com.gy.web;


import com.baomidou.mybatisplus.plugins.Page;
import com.gy.entity.Food;
import com.gy.service.IFoodService;
import com.gy.utils.FileUploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 范炳杰
 * @since 2019-11-18
 */
@Api(value = "测试接口", tags = "FoodController", description = "测试接口相关")
@Controller
@RequestMapping("/food")
public class FoodController {


    @Autowired
    private IFoodService iFoodService;

    @ResponseBody
    @GetMapping("/hello")
    public String hello(){
        return "hello world";
    }

    @ApiOperation(value = "分页查询事务")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", dataType = "int", required = true, value = "当前页码", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", required = true, value = "每页条数", defaultValue = "10"),
            @ApiImplicitParam(paramType = "query", name = "name", dataType = "string", value = "事务名称"),
    })
    @ResponseBody
    @GetMapping("/pages")
    public Page<Food> queryPage(@RequestParam( "pageNum" ) int pageNum, @RequestParam("pageSize") int pageSize,@RequestParam(value = "name",required = false) String name){

        Page<Food> page =   iFoodService.queryPage( new Page<Food>( pageNum,pageSize ) , name );

        return page;
    }

    @ResponseBody
    @RequestMapping(value="/select",method=RequestMethod.GET)
    public String select(@RequestParam("pageNum") int pageNum,@RequestParam("pageSize") int pageSize,
                       @RequestParam("name") String name,HttpServletResponse response) throws Exception {
        Page<Food> page = iFoodService.queryPage(new Page<Food>(),name);

        //选择
        String food = select(page.getRecords());
        return  food;
    }


    @RequestMapping(value="/exportTemplate",method=RequestMethod.GET)
    public void exportTemplate(HttpServletRequest request,HttpServletResponse response) {
        iFoodService.exportTemplate(response);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/vnd.ms-excel");
    }

    private String select(List<Food> list) {
        List<String> menuList = new ArrayList<String>();
        for (Food food : list) {
            for(int i = 0 ; i < food.getPriority() ; i++) {
                menuList.add(food.getName());
            }
        }

        if(menuList.size() > 0) {
            int num = (int)(Math.random() * menuList.size() );
            try {
                return menuList.get(num);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return "今天适合点外卖";
    }


    @ResponseBody
    @RequestMapping(value="/addFood",method= RequestMethod.POST)
    public Boolean addFood(@RequestBody Food food,HttpServletResponse response) throws Exception {

        Boolean addFood = iFoodService.addFood(food);
        System.err.println("新增的数据源为:" + food);
        return addFood;
    }

    @ResponseBody
    @RequestMapping(value="/{id}",method=RequestMethod.GET)
    public Food queryById(@PathVariable("id") String id,HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("utf-8");
        Food food = iFoodService.queryById(id);
       return food;
    }

    @ResponseBody
    @RequestMapping(value="/editFood",method=RequestMethod.POST)
    public Boolean editFood(@RequestBody Food food,HttpServletResponse response) throws Exception {

        Boolean editFood = iFoodService.editFood(food);
       return editFood;
    }

    @ResponseBody
    @RequestMapping(value="/deleteFood/{id}",method=RequestMethod.POST)
    public Boolean deleteFood(@PathVariable("id") int id,HttpServletResponse response) throws Exception {

        Boolean deleteFood = iFoodService.deleteFood(id);
        return deleteFood;
    }

    @RequestMapping(value="/export",method=RequestMethod.GET)
    public void export(@RequestParam(value="name",required=false) String name,
                       HttpServletRequest request,
                       HttpServletResponse response) {
        iFoodService.export(name,response);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/vnd.ms-excel");
    }

    @RequestMapping(value="/importExcel",method=RequestMethod.POST)
    public void importExcel( @RequestParam("file") MultipartFile file,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        try {
            String fileName = file.getOriginalFilename();
            String filePath = System.getProperty("user.dir") + "/TempExcelFile/";
            FileUploadUtil.uploadFile(file.getBytes(), filePath, fileName);
            File tempFile = new File(filePath+fileName);
            FileInputStream inputStream = new FileInputStream(tempFile);

            iFoodService.importExcel(request, response, inputStream);

            //删除临时文件
            if(tempFile.exists()){
                tempFile.delete();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
