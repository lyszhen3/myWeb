package batchexcute;

import java.util.Set;

/**
 * @author LinYuanSheng
 * @date 5/8/2019
 */
public abstract class BatchProcess<R> {

    protected AbstractMethodHandler<R> handler;

    public BatchProcess(AbstractMethodHandler<R> handler) {
        this.handler = handler;
    }

    public abstract R doProcess(Set<Long> ids, int limit);

    public static void main(String[] args) {

    }
}
