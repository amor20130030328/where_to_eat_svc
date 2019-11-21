package com.gy.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.gy.entity.Food;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 范炳杰
 * @since 2019-11-18
 */
@Mapper
public interface FoodMapper extends BaseMapper<Food> {

    /**
     * 分页查询
     * @param page
     * @param name
     * @return
     */
    List<Food> queryPage(@Param( "page" )Page<Food> page,@Param( "name" ) String name);


    /**
     * 根据主键查询
     * @param id
     * @return
     */
    Food queryById(@Param("id") String id);


    /**
     * 新增菜单
     * @param food
     * @return
     */
    Boolean addFood(Food food);


    /**
     * 批量新增
     * @param list
     * @return
     */
    Boolean batchInsert(List<Food> list);

    /**
     * 修改菜单项
     * @param food
     * @return
     */
    Boolean editFood(Food food);

    /**
     * 根据ID删除
     * @param id
     * @return
     */
    Boolean deleteFood(Integer id);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Boolean deleteByIds(List<Integer> ids);

}
