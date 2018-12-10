package com.contract.manager.filter;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.stereotype.Component;

// @Component
public class JsonAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
      UsernamePasswordAuthenticationToken authRequest;
      try (InputStream is = request.getInputStream()) {
          DocumentContext context = JsonPath.parse(is);
          String username = context.read("userName", String.class);
          String password = context.read("password", String.class);
          authRequest = new UsernamePasswordAuthenticationToken(username, password);
      } catch (IOException e) {
          e.printStackTrace();
          authRequest = new UsernamePasswordAuthenticationToken("", "");
      }
      setDetails(request, authRequest);
      return this.getAuthenticationManager().authenticate(authRequest);
  }

}