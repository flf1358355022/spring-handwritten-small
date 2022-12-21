package spring.beans.factory.conf;

/**
 * @author fanlongfei
 * @desc 存储bean定义信息
 * @date 2022年05月06日 5:59 下午
 */
public class BeanDefinition {

    private Object bean;

    public BeanDefinition(final Object bean) {
        this.bean = bean;
    }

    public Object getBean(){
        return this.bean;
    }
}
