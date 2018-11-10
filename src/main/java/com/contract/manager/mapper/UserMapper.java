package com.contract.manager.mapper;

import com.contract.manager.model.User;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
  User selectOne( String username );
}