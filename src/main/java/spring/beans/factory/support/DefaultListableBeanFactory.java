package spring.beans.factory.support;

import spring.beans.factory.conf.BeanDefinition;
import spring.exception.BeansException;
import spring.util.Assert;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 一个基于 bean 定义元数据的成熟 bean 工厂，可通过后处理器进行扩展。典型用法是在访问 bean 之前首先注册所有 bean 定义（可能从 bean 定义文件中读取）
 * @author fanlongfei
 * @date 2022年05月10日 6:39 下午
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry{

    /**
     * 存储bean定义对象
     */
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    /**
     * 存储bean定义对象名称，按照注册顺序来排序
     */
    private volatile List<String> beanDefinitionNames = new ArrayList<>(256);

    /**
     * 按照注册顺序注册单例对象
     */
    private volatile Set<String> manualSingletonNames = new LinkedHashSet<>(16);

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        Assert.hasText(beanName, "Bean name must not be empty");
        Assert.notNull(beanDefinition, "BeanDefinition must not be null");

        BeanDefinition existBeanDefinition = this.beanDefinitionMap.get(beanName);
        //有则更新,无则创建
        if (null != existBeanDefinition){
            this.beanDefinitionMap.put(beanName, beanDefinition);
        }else {
            if (hasBeanCreationStarted()){
                synchronized (this.beanDefinitionMap){
                    this.beanDefinitionMap.put(beanName, beanDefinition);
                    List<String> updatedDefinitions = new ArrayList<>(this.beanDefinitionNames.size() + 1);
                    updatedDefinitions.addAll(this.beanDefinitionNames);
                    updatedDefinitions.add(beanName);
                    this.beanDefinitionNames = updatedDefinitions;
                    if (this.manualSingletonNames.contains(beanName)){
                        Set<String> updatedSingletons = new LinkedHashSet<>(this.manualSingletonNames);
                        updatedSingletons.remove(beanName);
                        this.manualSingletonNames = updatedSingletons;
                    }
                }
            }else{
                this.beanDefinitionMap.put(beanName, beanDefinition);
                this.beanDefinitionNames.add(beanName);
                this.manualSingletonNames.remove(beanName);
            }
        }
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return null;
    }
}
