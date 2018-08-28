package com.lin.test.beans;

import com.lin.valid.Phone;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by pc on 2017-03-24.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class Shop {
    private String id;
//    @NotNull(message = "我的天")
    private String shopName;
    private String userName;
//    @NotNull(message = "手机号不能空")
//    @Phone(message = "手机必须13位")
    private String phone;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
