package com.example.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.model.User;

@Mapper
public interface Registered {

	@Insert("insert into `user` (`name`, `password`, `update_date` ,`create_date`) values (#{name}, #{password}, null, now())")
	void insert(String name, String password);
	
	@Select("select name, password from user where name = #{name}")
	User selectByName(String name);
}
