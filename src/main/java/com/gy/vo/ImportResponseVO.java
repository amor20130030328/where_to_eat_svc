package com.gy.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author :  fanbingjie
 * @version : 1.0
 * @email :  2395590449@qq.com
 * @since : 2019-11-19 / 14:02
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
public class ImportResponseVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 是否成功
     */
    private boolean isSuccess;

    /**
     * 处理结果消息
     */
    private String message;

    /**
     * 导入成功条数
     */
    int successCount;

    /**
     * 导入失败条数
     */
    int failCount;

    /**
     * 检验消息，如果有错误就返回错误消息并存入map中，如果没错就是一个空map
     * list的index对应的是行数，map的key对应的是列数
     *  {
     *      "5": "第3行,第5列调动部门必填",
     * 		"8": "第3行,第8列调动原因必填"
     *
     *	},
     *  {
     *
     *  },
     *  {
     *     "6": "第5行第6列:调动岗位有误"
     *     "7": "第5行第7列出错:调动日期有误"
     *
     *  },
     *
     * 	...
     *
     */
    private List<Map<Integer, String>> messageInfo;


    public List<Map<Integer, String>> getMessageInfo() {
        return messageInfo;
    }

    public void setMessageInfo(List<Map<Integer, String>> messageInfo) {
        this.messageInfo = messageInfo;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }
}