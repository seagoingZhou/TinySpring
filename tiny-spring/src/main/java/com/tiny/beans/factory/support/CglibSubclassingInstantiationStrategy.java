package com.tiny.beans.factory.support;

import com.tiny.beans.BeanUtils;
import com.tiny.beans.BeansException;
import com.tiny.beans.factory.BeanFactory;
import com.tiny.beans.factory.config.BeanDefinition;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CglibSubclassingInstantiationStrategy extends SimpleInstantiationStrategy {

    @Override
    public Object instantiate(BeanDefinition bd, String beanName, BeanFactory owner, Constructor<?> ctor, Object... args) throws BeansException {
        // Must generate CGLIB subclass...
        try {
            return new CglibSubclassCreator(bd, owner).instantiate(ctor, args);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new BeansException("Failed to invoke constructor for CGLIB enhanced subclass [" + bd.getBeanClass().getName() + "]",e);
        }

    }

    private static class CglibSubclassCreator {

        private static final Class<?>[] CALLBACK_TYPES = new Class<?>[]
                {NoOp.class};

        private final BeanDefinition beanDefinition;

        private final BeanFactory owner;

        CglibSubclassCreator(BeanDefinition beanDefinition, BeanFactory owner) {
            this.beanDefinition = beanDefinition;
            this.owner = owner;
        }

        /**
         * Create a new instance of a dynamically generated subclass implementing the
         * required lookups.
         * @param ctor constructor to use. If this is {@code null}, use the
         * no-arg constructor (no parameterization, or Setter Injection)
         * @param args arguments to use for the constructor.
         * Ignored if the {@code ctor} parameter is {@code null}.
         * @return new instance of the dynamically generated subclass
         */
        public Object instantiate( Constructor<?> ctor, Object... args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
            Class<?> subclass = createEnhancedSubclass(this.beanDefinition);
            Object instance;
            if (ctor == null) {
                instance = BeanUtils.instantiateClass(subclass);
            } else {

                Constructor<?> enhancedSubclassConstructor = subclass.getConstructor(ctor.getParameterTypes());
                instance = enhancedSubclassConstructor.newInstance(args);

            }

            return instance;
        }


        /**
         * Create an enhanced subclass of the bean class for the provided bean
         * definition, using CGLIB.
         */
        private Class<?> createEnhancedSubclass(BeanDefinition beanDefinition) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(beanDefinition.getBeanClass());
            enhancer.setCallbackTypes(CALLBACK_TYPES);
            return enhancer.createClass();
        }
    }
}
