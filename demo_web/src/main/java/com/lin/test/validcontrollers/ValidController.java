package com.lin.test.validcontrollers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lin.test.bo.UserBo;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Optional;

/**
 * Created by lys on 5/31/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@Validated   //使方法类型参数验证生效
@RestController
@RequestMapping("/valid")
public class ValidController {

	@GetMapping()
	public ResponseEntity<String> validmethod(@RequestParam(value = "id", required = false)
											 @Length(max = 2) String id) {
		System.out.println(id);


		return ResponseEntity.ok("可以了:" + id);
	}

	@GetMapping("/bean")
	public ResponseEntity<JSON> valideBean(@Validated UserBo bo, Errors errors) {
		if (errors.hasErrors()) {
			JSONObject jsonObject = new JSONObject();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObject.fluentPut("msg", errors.getAllErrors().get(0).getDefaultMessage()));
		}
		Object o = JSONObject.toJSON(bo);

		return ResponseEntity.ok((JSON) o);
	}

	/**
	 * 验证异常拦截
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<JSON> handlerConstrainViolationException(ConstraintViolationException ex) {
		JSON json = new JSONObject();
		Optional<ConstraintViolation<?>> first = ex.getConstraintViolations().stream().findFirst();
		first.ifPresent(constraintViolation -> ((JSONObject) json).put("msg", constraintViolation.getMessage()));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(json);
	}

}
