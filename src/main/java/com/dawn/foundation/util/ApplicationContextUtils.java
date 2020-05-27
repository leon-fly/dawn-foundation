package com.dawn.foundation.util;

import com.dawn.foundation.aware.BeanAware;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.beans.Introspector;
import java.util.Map;
import java.util.Optional;

@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext context)
            throws BeansException {
        applicationContext = context;
    }

    public static <T> T getBean(String key, Class<T> cls) {
        return applicationContext.getBean(key, cls);
    }

    public static <T> T getBean(Class<? extends T> subCls, Class<T> cls) {
        return applicationContext.getBean(Introspector.decapitalize(subCls.getSimpleName()), cls);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String key, Class<T> cls, Object... args) {
        Object result = applicationContext.getBean(key, args);
        if (result == null) {
            result = applicationContext.getBean(cls, args);
        }
        if (result == null) {
            return null;
        }
        return (T) result;
    }

    /**
     * The bean id name is same as the class name.
     *
     * @param <T>
     * @param cls
     * @return <T>
     */
    public static <T> T getBean(Class<T> cls) {
        return applicationContext.getBean(cls);
    }

    public static <C, T extends BeanAware<C>> T getAwareBean(Class<T> clazz, C c) {
        Map<String, T> beans = applicationContext.getBeansOfType(clazz);
        if (null == beans || beans.isEmpty()) {
            return null;
        }

        Optional<Map.Entry<String, T>> optional = beans.entrySet().stream().filter(stringTEntry -> (stringTEntry.getValue()).isMatched(c)).findFirst();

        if (!optional.isPresent()) {
            return null;
        }

        return optional.get().getValue();
    }
}

