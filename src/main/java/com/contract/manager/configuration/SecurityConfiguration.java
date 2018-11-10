package com.contract.manager.configuration;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.contract.manager.filter.TokenAuthorizationFilter;
import com.contract.manager.service.AuthService;
import com.contract.manager.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
// @EnableWebMvcSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private String usernameParameter = "username";
  private String passwordParameter = "password";
  private String login = "/auth/user";
  private String logout = "/auth/clear";

  @Autowired
  private AuthService authService;

  @Autowired
  private TokenAuthorizationFilter tokenAuthorizationFilter;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService( authService ).passwordEncoder( new BCryptPasswordEncoder() );
    // auth.inMemoryAuthentication()
        //.withUser("user").password("password").roles("USER");
  }

  @Override
  protected void configure( HttpSecurity http ) throws Exception {

    http.exceptionHandling()
      .accessDeniedHandler( new RestfulAccessDeniedHandler() )
      .authenticationEntryPoint( new RestfulAuthenticationEntryPoint() );

    http.authorizeRequests()
      .antMatchers("/", "/home")
        .permitAll()
      .anyRequest()
        .authenticated()
      .and()
        .formLogin()
        .loginProcessingUrl("/login")
        //.successHandler( new RestfulAuthenticationSuccessHandler() )
        .successHandler( new SuccessHandler( new JwtTokenUtil() ) )
        .failureHandler( new RestfulAuthenticationFailureHandler() )
        .permitAll()// .usernameParameter( usernameParameter ).passwordParameter( passwordParameter )
      .and()
        .logout()
        .logoutUrl(logout)
        .permitAll()
      .and()
        .sessionManagement().sessionCreationPolicy(STATELESS)
      .and()
        .addFilterBefore(tokenAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
  }
}

class RestfulAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setHeader( "Content-Type", "application/json;charset=utf-8" );
        response.getWriter().print("{\"code\":1,\"message\":\"" + accessDeniedException.getMessage() + "\"}");
	}
}

class RestfulAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
                       AuthenticationException exception) throws IOException, ServletException {
      response.setHeader("Content-Type", "application/json;charset=utf-8");
      response.getWriter().print("{\"code\":1,\"message\":\""+exception.getMessage()+"\"}");
      response.getWriter().flush();
  }
}

class RestfulAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException, ServletException {
      response.setHeader("Content-Type", "application/json;charset=utf-8");
      response.getWriter().print("{\"code\":0,\"message\":\"success\"}");
      response.getWriter().flush();
  }
}

class RestfulAuthenticationFailureHandler implements AuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                      AuthenticationException exception) throws IOException, ServletException {
      response.setHeader("Content-Type", "application/json;charset=utf-8");
      response.getWriter().print("{\"code\":1,\"message\":\""+exception.getMessage()+"\"}");
      response.getWriter().flush();
  }
}

class SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  private final JwtTokenUtil tokenUtils;

  private final ObjectMapper objectMapper;

  private final JsonNodeFactory jsonNodeFactory;


  public SuccessHandler(JwtTokenUtil tokenUtils) {
      this.tokenUtils = tokenUtils;
      this.objectMapper = new ObjectMapper();
      this.jsonNodeFactory = JsonNodeFactory.instance;
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

      response.setStatus(HttpStatus.OK.value());
      response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

      try (Writer writer = response.getWriter()){
          JsonNode jsonNode = jsonNodeFactory.objectNode()
                  .put("token",tokenUtils.generateToken(authentication));
          objectMapper.writeValue(writer,jsonNode);
      }catch (Exception e){
          e.printStackTrace();
      }
  }
}