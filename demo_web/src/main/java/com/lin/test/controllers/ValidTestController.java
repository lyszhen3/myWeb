package com.lin.test.controllers;

import com.lin.Test;
import com.lin.test.bo.ShopBO;
import com.lin.test.bo.UserBo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.groups.Default;

/**
 * Created by lys on 7/3/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@Controller
public class ValidTestController {
	/**
	 * 验证标签 @Validated 表示Update{@link Test.bo.UserBo.Update} 组
	 * 和 Default {@link javax.validation.groups.Default}   组需要验证
	 *
	 * @param bo     参数
	 * @param errors 错误
	 * @return json
	 * @see Test.bo.UserBo.Update
	 */
	@RequestMapping("validGroupUpdate")
	@ResponseBody
	public String testValidUpdate(@Validated({UserBo.Update.class, Default.class}) UserBo bo, Errors errors) {
		String err = null;

		if (errors.hasErrors()) {
			err = errors.getAllErrors().get(0).getDefaultMessage();
		}
		return err;
	}

	@RequestMapping("validGroupInsert")
	@ResponseBody
	public String testValidInsert(@Validated(UserBo.InGroup.class) UserBo bo, Errors errors) {
		String err = null;
		if (errors.hasErrors()) {
			err = errors.getAllErrors().get(0).getDefaultMessage();
		}
		return err;
	}

	@RequestMapping("validExtends")
	@ResponseBody
	public String testValidExtends(@Validated ShopBO shopBO, BindingResult errors) {

		String err = null;
		if (errors.hasErrors()) {
			err = errors.getAllErrors().get(0).getDefaultMessage();
		}
		return err;
	}

}
