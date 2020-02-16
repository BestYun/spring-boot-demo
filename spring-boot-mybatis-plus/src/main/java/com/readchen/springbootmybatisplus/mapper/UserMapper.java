package com.readchen.springbootmybatisplus.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.readchen.springbootmybatisplus.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user ${ew.customSqlSegment}")
    List<User> getUserAll(@Param(Constants.WRAPPER) Wrapper wrapper);

    @Select("select * from user ")
    List<User> getUserAll2();


    @Select("select * from user where name = '${name}' ")
    List<User> getUserByName(String name);

}
