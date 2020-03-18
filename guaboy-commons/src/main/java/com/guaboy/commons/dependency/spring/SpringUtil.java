package com.guaboy.commons.dependency.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * Spring容器工具类,需要依赖spring4.3以上的相关jar,项目启动时需要初始化该类。
 * 
 * @author zhousir
 * @since 1.0.0-RELEASE
 * 
 */
@Component
public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (SpringUtil.applicationContext == null) {
			SpringUtil.applicationContext = applicationContext;

	        System.out.println("----------------------------------------------------------------------------------------");
	        System.out.println("Guaboy-commons中的SpringUtil初始化已完成：ApplicationContext="+SpringUtil.applicationContext);
	        System.out.println("----------------------------------------------------------------------------------------");
		}
	}

	
	/**
	 * 获取applicationContext
	 * 
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	
	/**
	 * 通过name获取 Bean
	 * 
	 */
	public static Object getBean(String name) {
		try {
			return getApplicationContext().getBean(name);
		}catch(Exception e) {
		}
		return null;
	}

	
	/**
	 * 通过class获取Bean
	 * 
	 */
	public static <T> T getBean(Class<T> clazz) {
		try {
			return getApplicationContext().getBean(clazz);
		}catch(Exception e) {
		}
		return null;
	}

	
	/**
	 * 通过name以及Clazz返回指定的Bean
	 * 
	 */
	public static <T> T getBean(String name, Class<T> clazz) {
		try {
			return getApplicationContext().getBean(name, clazz);
		}catch(Exception e) {
		}
		return null;
	}

}
