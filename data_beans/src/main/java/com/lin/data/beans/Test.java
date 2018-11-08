package com.lin.data.beans;

import com.baomidou.mybatisplus.annotations.TableId;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.List;

/**
 * Created by lys on 2018/11/8.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class Test {
	@TableId
	private Long id;
	@Column(name = "super_id")
	private Long superId;
	private List<Test> tests;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSuperId() {
		return superId;
	}

	public void setSuperId(Long superId) {
		this.superId = superId;
	}
}
