package com.contract.manager.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.contract.manager.util.JwtTokenUtil;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class TokenAuthorizationFilter extends GenericFilterBean {

  private final String HEADER_NAME = "Authorization";

  private final JwtTokenUtil tokenUtils;

  public TokenAuthorizationFilter(JwtTokenUtil tokenUtils) {
      this.tokenUtils = tokenUtils;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      HttpServletRequest servletRequest = (HttpServletRequest) request;

      String token = resolveToken((HttpServletRequest) request);

      if(tokenUtils.validateToken(token)){
          Authentication authentication = tokenUtils.getAuthentication(token);
          SecurityContextHolder.getContext().setAuthentication(authentication);
      }
      chain.doFilter(request, response);

      cleanAuthentication();
  }

  /**
   * ?????????????????????token
   * @param request
   * @return token
   */
  private String resolveToken(HttpServletRequest request){
      String token = request.getHeader(HEADER_NAME);
      if(token==null||!token.startsWith("Bearer "))
          return null;
      else
          return token.substring(7);
  }

  private void cleanAuthentication(){
      SecurityContextHolder.getContext().setAuthentication(null);
  }
}