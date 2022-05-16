package com.tiny.test;

import com.tiny.beans.MutablePropertyValues;
import com.tiny.beans.PropertyValue;
import com.tiny.beans.PropertyValues;
import com.tiny.beans.factory.config.BeanDefinition;
import com.tiny.beans.factory.config.BeanReference;
import com.tiny.beans.factory.support.DefaultListableBeanFactory;
import com.tiny.test.beans.UserDAO;
import com.tiny.test.beans.UserService;
import org.junit.Test;

public class ApiTest {

    @Test
    public void testBeanFactory() {
        // 0.初始化BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 1. register userDAO
        beanFactory.registerBeanDefinition("userDAO",new BeanDefinition(UserDAO.class));

        // 2. set propertyvalues
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.add("uid", "001");
        propertyValues.add("userDAO", new BeanReference("userDAO"));

        // 3. inject bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class, propertyValues);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        // 4. get bean
        UserDAO userDAO = (UserDAO) beanFactory.getBean("userDAO");
        System.out.println(userDAO.queryUserName("001"));
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();

    }
}
