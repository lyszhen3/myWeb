package com.lin.test.mappers;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

/**
 * Created by pc on 2017-01-13.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@Scope("singleton")
@Lazy(true)
public interface TestMapper {


    int testCount();

}
