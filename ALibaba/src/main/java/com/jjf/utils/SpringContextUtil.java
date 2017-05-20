package com.jjf.utils;

/**
 * Created by jjf_lenovo on 2017/5/20.
 */

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * ��ȡspring�������Է��������ж��������bean
 * @author Simon
 *
 */
public class SpringContextUtil implements ApplicationContextAware{

    private static ApplicationContext applicationContext;

    /**
     * ʵ��ApplicationContextAware�ӿڵĻص����������������Ļ���
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)  {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) throws BeansException  {
        return applicationContext.getBean(name);

    }

    public static String getContextPath(){
        return applicationContext.getApplicationName();
    }

}
