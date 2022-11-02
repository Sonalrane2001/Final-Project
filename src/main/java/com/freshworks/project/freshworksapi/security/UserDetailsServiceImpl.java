package com.freshworks.project.freshworksapi.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import com.freshworks.project.freshworksapi.Repository.GovNgoRepository;
import com.freshworks.project.freshworksapi.classes.GovNgoData;

public class UserDetailsServiceImpl implements UserDetailsService{
	@Autowired
	public GovNgoRepository govNgoRepository;
	public UserDetailsServiceImpl()
	{
		
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		GovNgoData user=govNgoRepository.findByEmail(username);
		if(user==null)
		{
			throw new UsernameNotFoundException("User not exist");
		}
		else
		{
			CustomUserDetailsGov customUserDetails=new CustomUserDetailsGov(user);
			return customUserDetails;
		}
		
	}
}
