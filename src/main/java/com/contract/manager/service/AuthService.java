package com.contract.manager.service;

import com.contract.manager.mapper.UserMapper;
import com.contract.manager.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service( "authService" )
public class AuthService implements UserDetailsService {

//    @Autowired
//    private //在这里注入mapper，再想ia面根据用户名做信息查找

    /**
     * 重写PasswordEncoder  接口中的方法，实例化加密策略
     * @return 返回 BCrypt 加密策略
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private UserMapper userMapper;


    /**
     * 加载用户数据 , 返回UserDetail 实例
     * @param username  用户登录username
     * @return  返回User实体类 做用户校验
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String password = passwordEncoder().encode("123456");
        // System.out.println( password );

        User user = userMapper.selectOne( username );
        // System.out.println( "username: " + username );
        //根据查找到的用户信息判断用户是否被冻结
        return new User( user.getUsername(),user.getPassword(),
                true,true,true,true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("user") );
    }
}