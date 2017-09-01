package com.system.util.common;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil  implements ApplicationContextAware  {

	// Spring应用上下文环境  
    private static ApplicationContext applicationContext;  
    
      
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;  
    }
  
      
    public static ApplicationContext getApplicationContext() {  
        return applicationContext;  
    }
      
    public static <T> T  getBean(String name) throws BeansException {  
        return (T) applicationContext.getBean(name);  
    }
    public static <T> T getBean(Class requiredType) throws BeansException {  
        return (T) applicationContext.getBean(requiredType);  
    }
    
    public static String getDataBaseType() {
    	SqlSessionFactoryBean factoryBean = getBean(SqlSessionFactoryBean.class);
    	try {
			SqlSessionFactory factory = factoryBean.getObject();
			SqlSession session = factory.openSession();
			Connection conn = session.getConnection();
			DatabaseMetaData dbmd = conn.getMetaData();
			return dbmd.getDatabaseProductName();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }

}
