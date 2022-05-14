package com.tiny.beans.factory.support;

import com.tiny.beans.BeanUtils;
import com.tiny.beans.BeansException;
import com.tiny.beans.factory.BeanFactory;
import com.tiny.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SimpleInstantiationStrategy implements InstantiationStrategy {

    @Override
    public Object instantiate(BeanDefinition bd, String beanName, BeanFactory owner, final Constructor<?> ctor, Object... args) throws BeansException {
        final Class<?> clazz = bd.getBeanClass();
        Constructor<?> constructorToUse = ctor;
        try {
            if (null == ctor) {
                constructorToUse = clazz.getDeclaredConstructor();
            }
            return BeanUtils.instantiateClass(constructorToUse, args);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new BeansException("Failed to instantiate [" + clazz.getName() + "]", e);
        }
    }
}
