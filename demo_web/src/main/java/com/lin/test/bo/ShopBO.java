package com.lin.test.bo;

import com.lin.Test.beans.Shop;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


/**
 * Created by lys on 2018/8/16.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */

public class ShopBO extends Shop {

	/**
	 * 这个验证么。。需要userBo 不能null 不然userBo 还是不会验证。。
	 */
	@NotNull(message = "不能空")
	@Valid
	private UserBo userBo;

	public UserBo getUserBo() {
		return userBo;
	}

	public void setUserBo(UserBo userBo) {
		this.userBo = userBo;
	}
}
