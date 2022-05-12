package com.tiny.beans.factory.support;

import com.tiny.beans.BeansException;
import com.tiny.beans.factory.BeanFactory;
import com.tiny.beans.factory.config.BeanDefinition;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override
    public Object getBean(String beanName) {
        Object bean = getSingleton(beanName);
        if (null != bean) {
            return bean;
        }

        BeanDefinition bd = getBeanDefinition(beanName);
        return createBean(beanName, bd);
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;
}
