package spring.beans.factory.conf;

import com.sun.istack.internal.Nullable;

public interface SingletonBeanRegistry {

    /**
     * 注册bean
     * @param beanName
     * @param singletonObject
     */
    void registerSingleton(String beanName, Object singletonObject);

    /**
     * 获取bean
     * @param beanName
     * @return
     */
    Object getSingleton(String beanName);
}
