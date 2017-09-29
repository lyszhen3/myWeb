package com.lin.test.beans;

import java.io.Serializable;

/**
 * Created by lys on 2017-09-15.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class TestUser implements Serializable {
    private static final long serialVersionUID = -7721059177263362069L;
    private String name;
    private Long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
