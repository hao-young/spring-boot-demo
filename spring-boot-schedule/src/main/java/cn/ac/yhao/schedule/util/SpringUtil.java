package cn.ac.yhao.schedule.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class SpringUtil implements BeanFactoryPostProcessor {

    private static ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    /**
     *
     * @param name 类名 首字母小写
     * @return
     */
    public static Object getBean(String name) {
        return beanFactory.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return beanFactory.getBean(clazz);
    }
}
