package spring.beans.factory.support;

import org.springframework.lang.Nullable;
import spring.beans.factory.ObjectFactory;
import spring.beans.factory.conf.SingletonBeanRegistry;
import spring.exception.BeansException;
import spring.util.Assert;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fanlongfei
 * @date 2022年05月09日 5:10 下午
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * 一级缓存:最终实例对象缓存(bean name --> bean instance)
     */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    /**
     * 三级缓存:单例工厂缓存(bean name --> ObjectFactory)
     */
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);

    /**
     * 二级缓存:存储早期对象(bean name --> bean instance)
     */
    private final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>(16);

    /**
     * 已注册bean的集合体
     */
    private final Set<String> registerSingletons = new LinkedHashSet<>(256);

    /**
     * 存储正在创建的bean对象
     */
    private final Set<String> singletonsCurrentlyInCreation = Collections.newSetFromMap(new ConcurrentHashMap<>(16));

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        Assert.notNull(beanName, "Bean name must not be null");
        Assert.notNull(singletonObject, "Singleton object must not be null");

        synchronized (this.singletonObjects){
            Object oldObject = this.singletonObjects.get(beanName);
            if (null != oldObject){
                throw new IllegalStateException("\"Could not register object [\" + singletonObject +\n" +
                        "] under bean name '\" + beanName + \"': there is already object [\" + oldObject + \"] bound\"");
            }
            addSingleton(beanName, singletonObject);
        }

    }

    /**
     * 当最终对象创建出来之后，清空二三级缓存
     * @param beanName
     * @param singletonObject
     */
    private void addSingleton(String beanName, Object singletonObject) {
        synchronized(this.singletonObjects){
            this.singletonObjects.put(beanName, singletonObject);
            this.singletonFactories.remove(beanName);
            this.earlySingletonObjects.remove(beanName);
            this.registerSingletons.add(beanName);
        }

    }

    @Override
    @Nullable
    public Object getSingleton(String beanName) {
        return getSingleton(beanName, true);
    }

    protected void addSingletonFactorie(String beanName, ObjectFactory<?> singletonFactory) {
        Assert.notNull(singletonFactory, "Singleton factory must not be null");

        if (!this.singletonFactories.containsKey(beanName)) {
            this.singletonFactories.put(beanName, singletonFactory);
            this.earlySingletonObjects.remove(beanName);
            this.registerSingletons.add(beanName);
        }
    }

    /**
     *
     * @param beanName 指定返回的bean名称
     * @param allowEarlyReference 是否允许早期引用(循环依赖)
     * @return
     */
    @Nullable
    protected Object getSingleton(String beanName, boolean allowEarlyReference) {
        Object singletonObject = this.singletonObjects.get(beanName);
        if (null == singletonObject && isSingletonCurrentlyInCreation(beanName)){
            synchronized (this.singletonObjects){
                singletonObject = this.earlySingletonObjects.get(beanName);
                if (null == singletonObject && allowEarlyReference){
                    ObjectFactory<?> singletonFactory = this.singletonFactories.get(beanName);
                    if (null != singletonFactory){
                        singletonObject = singletonFactory.getObject();
                        this.earlySingletonObjects.put(beanName, singletonObject);
                        this.singletonFactories.remove(beanName);
                    }
                }
            }
        }
        return singletonObject;
    }

    /**
     * 判断当前对象是否在创建中
     * @param beanName
     * @return
     */
    public boolean isSingletonCurrentlyInCreation(String beanName) {
        return this.singletonsCurrentlyInCreation.contains(beanName);
    }

}
