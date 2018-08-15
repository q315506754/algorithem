package com.jiangli.db.jdbc;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.compass.gps.device.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 生成前端资源文件工具类
 * @author	zhanglikun
 * @date	2015年11月27日 下午4:53:15
 */
public class I18nResourceGenerateManager {

	public static void main(String[] args) throws IOException {
		// 创建数据源
		DataSource ds = new SingleConnectionDataSource("com.mysql.jdbc.Driver", "jdbc:mysql://192.168.9.223:3306", "root", "ablejava", true) ;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds) ;
		List<Map<String ,Object>> list = jdbcTemplate.query("SELECT * FROM test.TBL_I18N_ITEMS_TREENITY ", new ColumnMapRowMapper()) ;
		if(CollectionUtils.isEmpty(list)) return ;
		List<I18NItem> items = new ArrayList<I18NItem>() ;
		for(Map<String ,Object> map : list) {
			I18NItem item = new I18NItem() ;
			item.key = StringUtils.lowerCase(StringUtils.trim(map.get("I18N_KEY").toString())) ;
			// 获取中文值
			
			item.chinese = StringUtils.trim(map.get("CHINESE").toString()) ;
			item.chineseUnicode = UnicodeUtils.encodedUnicode(item.chinese) ;
			// 针对换行，将换行符替换为可见字符(前端使用)
			item.chinese = item.chinese.replaceAll("\r", "\\\\r") ;
			item.chinese = item.chinese.replaceAll("\n", "\\\\n") ;
			item.chinese = item.chinese.replaceAll("'", "\\\\'") ;
			// 获取英文值
			item.english = StringUtils.trim(map.get("ENGLISH") != null ? map.get("ENGLISH").toString() : null) ;
			if (item.english!=null&&item.english.length()>0) {
				item.english=item.english.substring(0, 1).toUpperCase()+item.english.substring(1);
			}else {
				item.english="";
			}
			
			// 如果英文为空，使用中文代替
			if(StringUtils.isBlank(item.english)) item.english = item.chinese ;
			item.englishUnicode = UnicodeUtils.encodedUnicode(item.english) ;
			// 针对换行，将换行符替换为可见字符(前端使用)
			item.english = item.english.replaceAll("\r", "\\\\r") ;
			item.english = item.english.replaceAll("\n", "\\\\n") ;
			item.english = item.english.replaceAll("'", "\\\\'") ;
			item.englishUnicode=item.englishUnicode.replaceAll("'", "\''") ;
			// 获取备注信息
			item.remark = map.get("REMARK") == null ? null : map.get("REMARK").toString() ;
			items.add(item) ;
		}
		
		handle(items) ;
		
	}

	/**
	 * 处理Item列表
	 * @author	zhanglikun
	 * @param items
	 */
	private static final void handle(List<I18NItem> items) {
		// 生成后端资源文件
		try {
			handleBackendFiles(items) ;
			System.out.println("后端i18n资源文件生成完成 ...");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 生成前端资源文件
		try {
			handleFrontendFiles(items) ;
			System.out.println("前端i18n资源文件生成完成 ...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成后端资源文件
	 * @author	zhanglikun
	 * @param items
	 * @throws IOException
	 */
	private static final void handleBackendFiles(List<I18NItem> items) throws IOException {
		// 生成后端资源文件
		StringBuffer chinese = new StringBuffer() ;
		StringBuffer english = new StringBuffer() ;
		for (I18NItem item : items) {
			// 输出注释
			if(StringUtils.isNotBlank(item.remark)) {
				chinese.append("# " + item.remark + "\r\n") ;
				english.append("# " + item.remark + "\r\n") ;
			}
			chinese.append(item.key + "=") ;
			english.append(item.key + "=") ;
			// 输出中文
			chinese.append(item.chineseUnicode) ;
			// 输出英文
			english.append(item.englishUnicode) ;
			chinese.append("\r\n") ;
			english.append("\r\n") ;
		}
		// 生成后端资源文件
		String base = new File("").getAbsolutePath() ;
		File dir = new File(base ,"src/main/resources/i18n") ;
		FileUtils.write(new File(dir ,"message_zh.properties"), chinese.toString() ,"utf-8");
		FileUtils.write(new File(dir ,"message_en.properties"), english.toString() ,"utf-8");
	}
	
	/**
	 * 生成前端资源文件
	 * @author	zhanglikun
	 * @param items
	 * @throws IOException
	 */
	private static final void handleFrontendFiles(List<I18NItem> items) throws IOException {
		// 前端资源文件目录

		final File i18nDirectory = new File(new File("").getAbsoluteFile() ,"/src/main/webapp/assets/js/i18n") ;
		StringBuffer chinese = new StringBuffer("var zLocale = {") ;
		StringBuffer english = new StringBuffer("var zLocale = {") ;
		for (I18NItem item : items) {
			// 中文
			chinese.append("\r\n'" + item.key + "'") ;
			chinese.append(":") ;
			chinese.append("'" + item.chinese + "'") ;
			chinese.append(",") ;
			// 英文
			english.append("\r\n'" + item.key + "'") ;
			english.append(":") ;
	
			
			english.append("'" +item.english  + "'") ;
				
			english.append(",") ;
		}
		
		// 生成文件
		FileUtils.write(new File(i18nDirectory ,"message_zh.js"), chinese.substring(0, chinese.length() - 1) + "\r\n} ;");
		FileUtils.write(new File(i18nDirectory ,"message_en.js"), english.substring(0, english.length() - 1) + "\r\n} ;");
	}
	
	/**
	 * I18N条目对象
	 * @author	zhanglikun
	 * @date	2015年12月9日 上午11:54:01
	 */
	static final class I18NItem {
		String key ;
		String chinese ;
		String chineseUnicode ;
		String english ;
		String englishUnicode ;
		String remark ;
	}
	
	/**
	 * Unicode编码工具类
	 * @link	http://my.oschina.net/HuifengWang/blog/232964
	 * @author	zhanglikun
	 * @date	2015年12月9日 上午11:43:37
	 */
	static final class UnicodeUtils {
		
		private static final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
			'B', 'C', 'D', 'E', 'F' };
		
		private static char toHex(int nibble) {
			return hexDigit[(nibble & 0xF)];
		}
		
		/**
		 * 使用Unicode编码
		 * @author	zhanglikun
		 * @param s
		 * @return
		 */
		public static String encodedUnicode(String s) {
			if(s == null) return null ;
			int len = s.length();
			int bufLen = len * 2;
			if (bufLen < 0) {
				bufLen = Integer.MAX_VALUE;
			}
			StringBuffer outBuffer = new StringBuffer(bufLen);
			
			for (int x = 0; x < len; x++) {
				char aChar = s.charAt(x);
				// Handle common case first, selecting largest block that
				// avoids the specials below
				if ((aChar > 61) && (aChar < 127)) {
					if (aChar == '\\') {
						outBuffer.append('\\');
						outBuffer.append('\\');
						continue;
					}
					outBuffer.append(aChar);
					continue;
				}
				
				switch (aChar) {
				case ' ':
					if (x == 0) outBuffer.append('\\');
					outBuffer.append(' ');
					break;
				case '\t':
					outBuffer.append('\\');
					outBuffer.append('t');
					break;
				case '\n':
					outBuffer.append('\\');
					outBuffer.append('n');
					break;
				case '\r':
					outBuffer.append('\\');
					outBuffer.append('r');
					break;
				case '\f':
					outBuffer.append('\\');
					outBuffer.append('f');
					break;
				case '=': // Fall through
				case ':': // Fall through
				case '#': // Fall through
				case '!':
					outBuffer.append('\\');
					outBuffer.append(aChar);
					break;
				default:
					if ((aChar < 0x0020) || (aChar > 0x007e)) {
						// 每个unicode有16位，每四位对应的16进制从高位保存到低位
						outBuffer.append('\\');
						outBuffer.append('u');
						outBuffer.append(toHex((aChar >> 12) & 0xF));
						outBuffer.append(toHex((aChar >> 8) & 0xF));
						outBuffer.append(toHex((aChar >> 4) & 0xF));
						outBuffer.append(toHex(aChar & 0xF));
					} else {
						outBuffer.append(aChar);
					}
				}
			}
			return outBuffer.toString();
		}
		
	}
	
	
	
	
}
