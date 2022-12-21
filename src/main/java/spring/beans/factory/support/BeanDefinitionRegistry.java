package spring.beans.factory.support;

import spring.beans.factory.conf.BeanDefinition;

/**
 * bean定义对象注册
 * @author fanlongfei
 * @date 2022年05月12日 6:02 下午
 */
public interface BeanDefinitionRegistry {

    /**
     * 向注册中心注册bean定义对象
     * @param beanName
     * @param beanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
