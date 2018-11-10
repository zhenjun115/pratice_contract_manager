package com.contract.manager.controller;

import java.io.IOException;

import javax.websocket.server.PathParam;

import com.contract.manager.model.Msg;
import com.contract.manager.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 判断json/html 请求 返回不同的结果
 * @ 注解@ResponseStatus ：响应状态码 UNAUTHORIZED(401, "Unauthorized")
 * Created by Fant.J.
 */

/**
 * 响应状态码 UNAUTHORIZED(401, "Unauthorized")
 */
@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
@RestController
public class AuthController {

  @Autowired
  AuthService AuthService;
  /**
   * 当需要身份认证时 跳转到这里
   */
  @RequestMapping("/auth/user")
  public Msg auth( @RequestParam String username, @RequestParam String password ) throws IOException {
      //如果不是，返回一个json 字符串
      return new Msg( 400, "fail: " + username );
  }
}