package spring.beans.factory.support;


import spring.beans.factory.BeanFactory;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 提供 bean 创建（使用构造函数解析）、属性填充、连接（包括自动连接）和初始化。处理运行时 bean 引用、解析托管集合、调用初始化方法等。
 * @author fanlongfei
 * @date 2022年05月10日 4:52 下午
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    /**
     * 存储至少创建过一次的bean对象
     */
    private final Set<String> alreadyCreated = Collections.newSetFromMap(new ConcurrentHashMap<>(256));


    /**
     * 检查这个工厂的 bean 创建阶段是否已经开始，即在此期间是否有任何 bean 被标记为已创建
     * @return
     */
    protected Boolean hasBeanCreationStarted(){
        return !this.alreadyCreated.isEmpty();
    }
}
