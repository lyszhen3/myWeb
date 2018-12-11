package com.lin.data.mappers;

import com.lin.data.beans.Test;
import com.lin.data.core.mapper.Mapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lys on 2018/11/8.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@Repository
@Lazy
public interface TestMapper extends Mapper<Test> {


	@Select("select*from `test` where `super_id` is null ")
	@Results({@Result(property = "tests", column = "id", many = @Many(select = "selectBySuperId")),
			@Result(property = "id", column = "id"),
			@Result(property = "superId", column = "super_id")
	})
	List<Test> selectList(RowBounds rowBounds);

	@Select("SELECT*FROM `test` WHERE `super_id`=#{superId}")
	@Results({@Result(property = "tests", column = "id", many = @Many(select = "selectBySuperId")),
			@Result(property = "id", column = "id"),
			@Result(property = "superId", column = "super_id")

	})
	Test selectBySuperId(@Param("superId") String superId);


}
