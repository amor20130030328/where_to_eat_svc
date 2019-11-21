package com.gy.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

	  public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception { 
	        File targetFile = new File(filePath);  
	        if(!targetFile.exists()){    
	            targetFile.mkdirs();    
	        }       
	        FileOutputStream out = new FileOutputStream(filePath+fileName);
	        out.write(file);
	        out.flush();
	        out.close();
	    }
	    
	    public static void uploadFile(File rescourceFile, File targetFile) throws Exception { 
	        FileInputStream ins = new FileInputStream(rescourceFile);
	        FileOutputStream out = new FileOutputStream(targetFile);
	        byte[] b = new byte[1024];
	        int n=0;
	        while((n=ins.read(b))!=-1){
	            out.write(b, 0, n);
	        }
	        ins.close();
	        out.close();
	    }
	    
	    public static boolean uploadFile(MultipartFile file, String filePath, String fileName){
	        File targetFile = new File(filePath + fileName);
	        if(!targetFile.getParentFile().exists()){ //判断文件父目录是否存在
	            targetFile.getParentFile().mkdir();
	        }
	        try {
	            file.transferTo(targetFile);  //保存文件
	            return true;
	        } catch (IllegalStateException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	            return false;
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	            return false;
	        } 
	    }
	    
	    public static boolean uploadImage(MultipartFile file, String filePath, String fileName){
	        File targetFile = new File(filePath + fileName);
	        if(!targetFile.getParentFile().exists()){ //判断文件父目录是否存在
	            targetFile.getParentFile().mkdir();
	        }
	        try {
	            file.transferTo(targetFile);  //保存文件
	            return true;
	        } catch (IllegalStateException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	            return false;
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	            return false;
	        } 
	    }
	    
	    public static void copyFile(File fromFile,File toFile) throws IOException{
	        if(!toFile.getParentFile().exists()){ //判断文件父目录是否存在
	            toFile.getParentFile().mkdirs();
	        }
	        FileInputStream ins = new FileInputStream(fromFile);
	        FileOutputStream out = new FileOutputStream(toFile);
	        byte[] b = new byte[1024];
	        int n=0;
	        while((n=ins.read(b))!=-1){
	            out.write(b, 0, n);
	        }
	        
	        ins.close();
	        out.close();
	    }
	    
	    /**
	     * 删除文件
	     * @param targetFile
	     * @return
	     */
	    
	    public static boolean delFile(File file) {
	         if (!file.exists()) {
	             return false;
	         }
	 
	         if (file.isFile()) {
	             return file.delete();
	         } else {
	             File[] files = file.listFiles();
	             for (File f : files) {
	                 delFile(f);
	             }
	             return file.delete();
	         }
	     }
	    
	    /**
	     * 文件流转file
	     * @param ins - 文件流
	     * @param file - 目标文件
	     */
	    public static void inputStreamToFile(InputStream ins, File file) {
	        try {
	            OutputStream os = new FileOutputStream(file);
	            int bytesRead = -1;
	            byte[] buffer = new byte[4096];
	            while ((bytesRead = ins.read(buffer, 0, 4096)) != -1) {
	                os.write(buffer, 0, bytesRead);
	            }
	            os.close();
	            ins.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}
