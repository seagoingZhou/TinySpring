package com.tiny.beans.factory.config;

public interface SingletonBeanRegistry {

    Object getSingleton(String beanName);
}
