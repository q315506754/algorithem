package com.jiangli.common.utils;


import org.apache.commons.lang.StringUtils;

public class StringHelper {

	/**
	 * 驼峰命名转换为下划线命名(适用于属性转换为字段)
	 * @param camel 驼峰格式字符串
	 * @return
	 */
	public static final String camelToUnderscore(String camel) {
		// 统一转换为大写
		return camelToUnderscore(camel,true);
	}

	public static final String camelToUnderscore(String camel,boolean uppercase) {
		if(camel == null) return null ;
		StringBuilder sb = new StringBuilder() ;
		for(int i = 0 ,len = camel.length() ; i < len ; i ++) {
			char ch = camel.charAt(i) ;
			// 如果当前字符是大写字符且不是第一个字符，前加_
			if('A' <= ch && ch <= 'Z' && i != 0) {
				sb.append('_') ;
			}
			sb.append(ch) ;
		}
        String str = sb.toString();
		if(uppercase){
            return StringUtils.upperCase(str) ;
        }
		// 统一转换为大写
        return str ;
	}

	/**
	 * 下划线格式字符串转换为驼峰格式(适用于字段转换为属性)
	 * @param underscore 下划线格式字符串
	 * @return
	 */
	public static final String underscoreToCamel(String underscore) {
		if(underscore == null) return null ;
		underscore = underscore.toLowerCase() ;	// 统一转换为小写
		if(underscore.contains("_")) {
			StringBuilder sb = new StringBuilder() ;
			for(int i = 0 ,len = underscore.length() ; i < len ; i ++) {
				char ch = underscore.charAt(i) ;
				if(ch == '_') {
					// 如果_不是最后一个字符
					if(i != len - 1) {
						sb.append((underscore.charAt(++ i) + "").toUpperCase()) ;
					}
				} else sb.append(ch) ;
			}
			return sb.toString() ;
		} else return underscore ;
	}
	
}
