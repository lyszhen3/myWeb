package com.lin.data.base;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Created by pc on 2017-07-03.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class MoreSiteBean implements Serializable {
    @Transient
    private static final long serialVersionUID = 5888128607885894181L;
    @Transient
    private String webSite;

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }
}
