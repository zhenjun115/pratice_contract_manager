package com.contract.manager.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {

  private String userName;
  private String password;

  public User( String userName, String password ) {
    this.userName = userName;
    this.password = password;
  }

  public User(String username, String password, boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isCredentialsNonExpired, boolean isEnabled,
			List<GrantedAuthority> commaSeparatedStringToAuthorityList) {
        this.userName = username;
        this.password = password;
	}

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  
  /**
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String getUsername() {
    return this.userName;
  }

  /**
   * @param userName the userName to set
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
	public boolean isEnabled() {
		return true;
	}

}