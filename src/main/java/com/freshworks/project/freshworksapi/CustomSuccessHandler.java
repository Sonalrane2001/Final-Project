package com.freshworks.project.freshworksapi;

import java.io.IOException;


import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.freshworks.project.freshworksapi.security.CustomUserDetailsGov;
@Configuration
public class CustomSuccessHandler implements AuthenticationSuccessHandler{

	public CustomUserDetailsGov auth;
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		 
		Set<String> list=AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		if(list.contains("ROLE_GOV"))
		{
			response.sendRedirect("/Govern/");
		}
		else if(list.contains("ROLE_HOTEL"))
		{
			response.sendRedirect("/Hotel/");
		}
		else
		{
			response.sendRedirect("/NGOS/");
		}
		
	}
	

}
