package spring.beans.factory;

import spring.exception.BeansException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fanlongfei
 * @desc 用于访问 Spring bean 容器的根接口。
 * @date 2022年05月06日 6:01 下午
 */
public interface BeanFactory {

    Object getBean(String name) throws BeansException;

}


