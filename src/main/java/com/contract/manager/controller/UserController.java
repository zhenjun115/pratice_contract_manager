package com.contract.manager.controller;

import com.auth0.jwt.JWT;
import com.contract.manager.model.Msg;
import com.contract.manager.model.User;
import com.contract.manager.service.UserService;
import com.contract.manager.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping( "/user" )
public class UserController {


    @Autowired
    UserService userService;

    /**
     * 获取个人信息
     * @param headers
     * @return
     */
    @RequestMapping( "fetch" )
    public Msg fecth( @RequestHeader HttpHeaders headers ) {
        // 获取单点用户token
        String token = headers.get( "authorization" ).get( 0 );
        // 获取token关联的用户登录名称
        String userName = JWT.decode( token.substring(7) ).getSubject();
        User user = new User();
        user.setUserName( userName );

        user = userService.fetchByUserName( user );

        Msg msg = new Msg();
        msg.setCode( 1 );
        msg.setContent( "获取用户成功" );
        msg.setPayload( user );

        return msg;
    }

    /**
     * 更新个人信息
     * @param user
     * @return Msg
     */
    @RequestMapping( "update" )
    public Msg update(@RequestBody User user, @RequestHeader HttpHeaders headers ) {
        // 获取单点用户token
        String token = headers.get( "authorization" ).get( 0 );
        // 获取token关联的用户登录名称
        String userName = JWT.decode( token.substring(7) ).getSubject();
        user.setUserName( userName );

        // 更新密码
        if( user.getPassword() != null && user.getPassword().equals( "" ) == false ) {
            // user.setPassword( "" );
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword( passwordEncoder.encode( user.getPassword() ) );
        }

        boolean saved = userService.update( user );

        Msg msg = new Msg();

        if( saved ) {
            msg.setCode( 1 );
            msg.setContent( "更新成功" );
        } else {
            msg.setCode( 0 );
            msg.setContent( "更新失败" );
        }

        return msg;
    }
}