package batchexcute;

import java.util.Set;

/**
 * @author LinYuanSheng
 * @date 5/8/2019
 */
public abstract class AbstractMethodHandler<R> {
    public abstract R doAction(Set<Long> ids);
}
