package com.contract.manager.service;

import com.contract.manager.mapper.UserMapper;
import com.contract.manager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service( "userService" )
public class UserService {

    @Autowired
    UserMapper userMapper;

    public User fetchByUserName( User user ) {
        return userMapper.fetchByUserName( user );
    }

    public boolean update( User user ) {
        return userMapper.update( user );
    }
}
