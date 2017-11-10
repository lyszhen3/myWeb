package com.lin.test.bo;

import com.lin.base.BaseBo;
import com.lin.valid.Phone;

import javax.validation.constraints.NotNull;


/**
 * Created by pc on 2017-03-24.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class UserBo extends BaseBo {
    @NotNull
    private String content;
    private String title;
    @Phone(message = "手机必须13位")
    private String phone;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
