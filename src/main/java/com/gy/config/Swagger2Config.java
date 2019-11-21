package com.gy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author :  fanbingjie
 * @version : 1.0
 * @email :  2395590449@qq.com
 * @since : 2019-11-18 / 22:07
 * <p>
 * 快捷键 :
 * 执行 :  		alt + r   			提示补全 : 		alt+/
 * 向下移动行	alt +down		万能解错/ 生成返回值变量	alt + Enter
 * 向上移动行	alt + up			退回前一个编辑页面		alt + left
 * 进入到下一个编辑页面	alt + right
 * <p>
 * 单行注释 : 	Ctrl + /			复制代码			Ctrl + c
 * 全选		Ctrl   + a			删除一行或删除选中行    	Ctrl + d
 * 剪切		Ctrl   + x			关闭当前打开的代码栏               ctrl + w
 * 保存		Ctrl  + s			查看类的结构，类似于eclipse的 outline   Ctrl + o
 * 粘贴		Ctrl  +  v			撤销			Ctrl  + z
 * 反撤销		Ctrl  + y			打开最近修改的文件		Ctrl + E
 * 查找/替换		Ctrl +f			查找(全局)			Ctrl + h
 * <p>
 * <p>
 * 向下开始新的一行	shift + Enter
 * 如何查看原码	                Ctrl + 点击类  或   Ctrl +shift + t  (输入)
 * 向上开始新的一行		Ctl + shift + Enter
 * 快速搜索类中的错误                  Ctrl + shift + q	选择要粘贴的内容		          Ctrl + shift + v
 * 查找方法在哪里被调用	Ctrl + shift + h	关闭当前打开的所有代码栏	          Ctrl + shift + w
 * 抽取方法			Ctrl + shift + m	查看类的继承结构图		          Ctrl + shift + u
 * 打开代码所在硬盘文件夹	Ctrl + shift + x	多行注释:		 	          Ctrl + shift + /
 * 格式化代码		Ctrl + shift + F	大写转小写/ 小写转大写	          Ctrl + shift + y
 * <p>
 * 查看方法的多层重写结构           Ctrl + alt + h	添加到收藏		          Ctrl + alt + f
 * 提示方法的参数类型 		Ctrl +alt 	+ /	向下复制一行		          Ctrl + alt + down
 * <p>
 * 收起所有方法		alt + shift + c	重构；修改变量名与方法名 (rename)   alt + shift + r
 * 打开所有方法		alt + shift + x	生成构造/get/set/toString	          alt  + shift + s
 * 生成try-catch 等              	alt + shift + z	局部变量抽成成员变量	          alt + shift + f
 * <p>
 * 选中数行，整体往后移动   	tab		选中数行，整体往前移动   	          shift + tab
 * <p>
 * 查看继承关系		F4		查看文档说明		          F2
 * 查找文件			double shift
 */
@Configuration
//注解开启 swagger2 功能
@EnableSwagger2
public class Swagger2Config {

    //是否开启swagger，正式环境一般是需要关闭的
    @Value("${swagger.enabled}")
    private boolean enableSwagger;

    @Bean
    public Docket createRestApi() {
        return new Docket( DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //是否开启 (true 开启  false隐藏。生产环境建议隐藏)
                .enable(enableSwagger)
                .select()
                //扫描的路径包,设置basePackage会将包下的所有被@Api标记类的所有方法作为api
                .apis( RequestHandlerSelectors.basePackage("com.gy.web"))
                //指定路径处理PathSelectors.any()代表所有的路径
                .paths( PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //设置文档标题(API名称)
                .title("SpringBoot中使用Swagger2构建RESTful接口")
                //文档描述
                .description("接口说明")
                //服务条款URL
                .termsOfServiceUrl("http://127.0.0.1:9999/")
                //联系信息
                .contact("底层码农")
                //版本号
                .version("1.0。0")
                .build();
    }
}
