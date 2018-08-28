package com.lin.test.bo;

import com.lin.base.BaseBo;
import com.lin.valid.Phone;
import org.springframework.validation.annotation.Validated;

import javax.validation.GroupSequence;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;


/**
 * Created by pc on 2017-03-24.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class UserBo extends BaseBo {
    @NotNull(message = "为什么你不行")
    private String content;
    private String title;
    /**
     * groups 属于组, 验证时只需满足组中其中一个元素即可
     */
    @Phone(message = "手机必须13位",groups = {Insert.class,Update.class})
    private String phone;

    @Max(value = 1,groups = {Update.class})
    private Integer sex;

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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public interface Insert{

    }
    public interface Update{

    }

    /**
     * 排序组验证 e.g. 如果Insert 组中验有错误 则 Default 则不再验证
     */
    @GroupSequence({Insert.class,Default.class})
    public interface InGroup{

    }
}
