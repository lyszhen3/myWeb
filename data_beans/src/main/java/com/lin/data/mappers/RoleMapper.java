package com.lin.data.mappers;

import com.lin.data.beans.Role;
import com.lin.data.core.mapper.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lys on 12/25/2017.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@Repository
public interface RoleMapper extends Mapper<Role> {
	@Select("SELECT*FROM `role` limit 180,10")
	List<Role> selectList();
}
