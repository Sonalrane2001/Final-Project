package com.freshworks.project.freshworksapi.security;

import java.util.Arrays;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.freshworks.project.freshworksapi.classes.GovNgoData;

public class CustomUserDetailsGov implements UserDetails{
	public GovNgoData govNgoData;

	public CustomUserDetailsGov(GovNgoData govNgoData) {
		super();
		this.govNgoData = govNgoData;
	}
	public CustomUserDetailsGov() {
		
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority(govNgoData.getRole());
		return Arrays.asList(simpleGrantedAuthority);
		
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return govNgoData.getPassword();
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return govNgoData.getEmail();
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}
