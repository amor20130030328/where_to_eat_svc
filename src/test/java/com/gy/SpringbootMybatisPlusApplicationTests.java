package com.gy;

import com.baomidou.mybatisplus.plugins.Page;
import com.gy.entity.Food;
import com.gy.mapper.FoodMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringbootMybatisPlusApplicationTests {

	@Autowired
	private FoodMapper mapper;

	@Test
	void contextLoads() {
		List<Food> list = mapper.queryPage( new Page<>( 1,2 ),"" );
		for (Food food : list) {
			System.out.println(food);
		}
	}

}
