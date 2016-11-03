package com.jiangli.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * @author Jiangli
 *
 * CreateTime 2013-8-7 下午2:22:37
 */
//@Component
public class SpringUtil implements ApplicationContextAware{
	private  static ApplicationContext applicationContext;
	
	@Override
	public final void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtil.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}
}
