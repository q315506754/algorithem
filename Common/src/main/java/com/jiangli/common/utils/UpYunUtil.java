package com.jiangli.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

/**
 * @author Cloudbian
 * @version createTime：2012-7-9 下午9:48:12
 */
public class UpYunUtil {
	private final static String COOS_USERNAME = "webmaster";
	private final static String COOS_USERPWD = "Cooly_321";
	private final static String COOS_SPACENAME = "cooly";
//	public final static String COOS_URL = "http://image.iamcooly.com/";
	public final static String COOS_URL = "http://cooly.b0.upaiyun.com/";

	private static UpYun cooly_upyun = null;

	/**
	 * init
	 * */
	private static void initCOOSUpYun(){
		if(null== cooly_upyun){
			cooly_upyun = new UpYun(COOS_SPACENAME,COOS_USERNAME,COOS_USERPWD);
		}
	}
	
	/**
	 * 上传图片，流传输，不自动建立目录
	 * @param filePath(云文件路径，需要加"/") file保存的文件
	 * @return boolean is success
	 * @throws Exception 保存异常
	 * */

	public static boolean saveFileToCoolyCloud(String filePath,File file) throws Exception {
		initCOOSUpYun();
		return cooly_upyun.writeFile(filePath, file);
	}


	/**
	 * 上传图片，流传输，不自动建立目录
	 * @param filePath(云文件路径，需要加"/") file保存的文件
	 * @return boolean is success
	 * @throws Exception 保存异常
	 * */
	public static boolean saveFileToCloud(String filePath,FileInputStream fileStream) throws Exception {
		initCOOSUpYun();
		return cooly_upyun.writeFile(filePath, fileStream, false);
	}
	
	/**
	 * 上传图片返回文件名
	 * @param filePath(云文件路径，不要加"/") file保存的文件
	 * @return String fileName
	 * @throws Exception 保存异常
	 * */
	public static String uploadFileAndGetFileName(String filePath,File file) throws Exception {
		initCOOSUpYun();
		String newFilePath = UpYun.md5(filePath);
		if(cooly_upyun.writeFile("/"+newFilePath, file)){
			return newFilePath;
		}
		return null;
	}
	
	/**
	 * 上传图片
	 * @param filePath(云文件路径，不要加"/") file保存的文件
	 * @return String fileName
	 * @throws Exception 保存异常
	 * */
	public static boolean uploadFile(String filePath,File file) throws Exception {
		initCOOSUpYun();
		return cooly_upyun.writeFile("/" + filePath, file);
	}

	/**
	 * 删除图片
	 * @param filePath(云文件路径)
	 * @throws Exception 删除异常
	 * */
	public static boolean deleteFileFromCloud(String filePath) throws Exception {
		initCOOSUpYun();
		return cooly_upyun.deleteFile(filePath);
	}

	/**
	 * 获取图片信息
	 * @param filePath(云文件路径)
	 * @throws Exception
	 * */
	public static Map getFileInfo(String filePath) throws Exception {
		initCOOSUpYun();
		return cooly_upyun.getFileInfo(filePath);
	}

	/**
	 * 获取文件
	 * @param filePath(需加"/"),需要存储的File对象
	 * @throws Exception
	 * */
	public static boolean getFile(String filePath,File file) throws Exception {
		initCOOSUpYun();
		return cooly_upyun.readFile(filePath, file);
	}

}
