package com.gy.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;

public class CommonUtils {


	/**
	 * 填充实体类某个字段值
	 * @param t
	 * @param property
	 * @param value
	 */
	public static <T> void  setPropertyValue(T t ,String property,String value){
		try {
			Class<? extends Object> clazz = t.getClass();    // 实体类型
			Field field = clazz.getDeclaredField(property);   //字段类型
			Class<?> propertyClazz = field.getType();
			String fieldClazzName = field.getType().getSimpleName();  //字段简单类名
			Method method = clazz.getDeclaredMethod("set"+upperCase(property),propertyClazz);     //字段对应的set方法
			Object propertyValue = value;
			switch(fieldClazzName){

				case "BigDecimal":
					propertyValue = new BigDecimal(value);
					break;

				case "Double":
					propertyValue = new Double(value);
					break;

				case "Integer":
					propertyValue = new Integer(value);
					break;
			}
			method.invoke(t, propertyValue);
		} catch (Exception e) {
			System.err.println("字段值注入失败");
		}

	}

	public static <T> String  getPropertyValue(T t ,String property){
		try {
			Class<? extends Object> clazz = t.getClass();
			Method method = clazz.getDeclaredMethod("get"+upperCase(property));
			Object obj = method.invoke(t);
			String str = null;
			if(obj instanceof String){
				str = (String) obj;
			}else if(obj instanceof Integer) {
				str = String.valueOf(obj);
			}
			return str;
		} catch (Exception e) {
		}
		return null;
	}

	private static String upperCase(String property) {
		char[] ch = property.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		return new String(ch);
	}

}
