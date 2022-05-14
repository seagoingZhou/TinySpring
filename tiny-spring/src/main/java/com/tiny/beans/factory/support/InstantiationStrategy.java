package com.tiny.beans.factory.support;

import com.tiny.beans.BeansException;
import com.tiny.beans.factory.BeanFactory;
import com.tiny.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public interface InstantiationStrategy {

    Object instantiate(BeanDefinition bd, String beanName, BeanFactory owner, final Constructor<?> ctor, Object... args)
            throws BeansException;
}
