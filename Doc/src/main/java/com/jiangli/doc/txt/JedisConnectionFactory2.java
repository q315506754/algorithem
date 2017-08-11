package com.jiangli.doc.txt;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.net.URI;

/**
 * 使用URI配置JedisConnectionFactory，参考：#JedisPoolFactory
 * @author	zhanglikun
 * @date	2016年2月19日 下午1:49:01
 */
public class JedisConnectionFactory2 extends JedisConnectionFactory {

	/**
	 * 使用URI配置JedisConnectionFactory，便于维护自行控制密码等
	 * 此配置可能会覆盖JedisConnectionFactory中的hostName、port、password、database四个字段
	 * @param uri
	 */
	public void setUri(String uri) {
		URI redisURI = URI.create(uri) ;
		if(redisURI == null || !JedisURIHelper.isValid(redisURI)) {
		    throw new IllegalArgumentException(String.format(
		    		"Cannot open Redis connection due invalid URI. %s", uri.toString()));			    
		}
		// 使用URI初始化Redis连接
		this.setHostName(redisURI.getHost());
		this.setPort(redisURI.getPort());
		String password = JedisURIHelper.getPassword(redisURI) ;
		if(password != null) this.setPassword(password);
		this.setDatabase(JedisURIHelper.getDBIndex(redisURI));
	}
	
}