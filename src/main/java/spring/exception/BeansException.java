package spring.exception;

import org.springframework.lang.Nullable;

/**
 *
 * @author fanlongfei
 * @date 2022年05月07日 6:03 下午
 */
public class BeansException extends RuntimeException  {

    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(@Nullable String msg, @Nullable Throwable cause) {
        super(msg, cause);
    }
}
