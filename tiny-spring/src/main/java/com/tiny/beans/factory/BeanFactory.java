package com.tiny.beans.factory;

public interface BeanFactory {

    public Object getBean(String beanName);

    Object getBean(String beanName, Object... args);
}
