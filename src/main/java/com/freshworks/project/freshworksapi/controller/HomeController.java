package com.freshworks.project.freshworksapi.controller;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.freshworks.project.freshworksapi.Repository.GovNgoRepository;

import com.freshworks.project.freshworksapi.classes.GovNgoData;

@Controller
public class HomeController {
	@Autowired
	private BCryptPasswordEncoder passwordEncode;
	@Autowired
	public GovNgoRepository govNgoRepository;
	@RequestMapping("/home")
	public String home() //calling to home page
	{
		return "home";
	}
	
	@RequestMapping("/Gov")
	public String govern()  //calling to government page
	{
		return "homeGov";
	}
	@RequestMapping("/hotel")  //calling to hotel page
	public String hotel()
	{
		return "homeHotel";
	}
	@RequestMapping("/Ngo")  //calling to ngo page
	public String ngo()
	{
		return "homeNgo";
	}
	@RequestMapping("/signupNgo")  //calling to signup for ngo page
	public String ngoSignUp()
	{
		return "signupNgo";
	}
	@RequestMapping("/signupHotel")  //calling to signup for hotel page
	public String hoteSignUp()
	{
		return "signupHotel";
	}
	@RequestMapping("/signupGov")  //calling to signup for gov page
	public String govSignUp()
	{
		return "signupGov";
	}
	@RequestMapping("/signin")
	public String loginForAll()  //calling to login page
	{
		
		return "login";
	}
	
	
	@RequestMapping(value="/saveGov",method=RequestMethod.POST)
	public String saveGovUser(@ModelAttribute GovNgoData user,HttpSession session)
	{
		String email=user.getEmail();
		GovNgoData kl=govNgoRepository.findByEmail(email);  //get user from email
		if(kl==null)  //if user is not present then kl is equal to null and proceed for signup
		{
		user.setPassword(passwordEncode.encode(user.getPassword()));//encode password because of security
		user.setRole("ROLE_GOV");  //set role as government
		GovNgoData u=govNgoRepository.save(user); //save data into mysql table
		//System.out.println(user);
		if(u!=null)  
		{
		session.setAttribute("msg","Register SuccessFully!!");
		}
		else
		{
			session.setAttribute("msg","Wrong On Server!!");
		}
		}
		else //if email is already present the it show alert
		{
			session.setAttribute("msg","UserName Is Present,Try With Other Email!!");
		}
			
		return "redirect:/signupGov";
	}
	@RequestMapping(value="/saveHotel",method=RequestMethod.POST)
	public String saveHotelUser(@ModelAttribute GovNgoData user,HttpSession session)
	{
		String email=user.getEmail();
		GovNgoData kl=govNgoRepository.findByEmail(email);//get user from email
		if(kl==null)//if user is not present then kl is equal to null and proceed for signup
		{
		user.setPassword(passwordEncode.encode(user.getPassword()));//encode password because of security
		user.setRole("ROLE_HOTEL"); //set role as hotel
		
		user.setStatus("Decline");//set status as decline initially
		GovNgoData u=govNgoRepository.save(user); //save data into mysql table
		System.out.println(user);
		if(u!=null)
		{
		session.setAttribute("msg","Register SuccessFully!!");
		}
		else
		{
			session.setAttribute("msg","Wrong On Server!!");
		}
		}
		else //if email is already present the it show alert
		{
			session.setAttribute("msg","UserName Is Present,Try With Other Email!!");
		}
			
		return "redirect:/signupHotel";
	}
	@RequestMapping(value="/saveNgo",method=RequestMethod.POST)
	public String saveNGOUser(@ModelAttribute GovNgoData user,HttpSession session)
	{
		String email=user.getEmail();
		GovNgoData kl=govNgoRepository.findByEmail(email);//get user from email
		if(kl==null)//if user is not present then kl is equal to null and proceed for signup
		{
		user.setPassword(passwordEncode.encode(user.getPassword()));//encode password because of security
		user.setRole("ROLE_NGO"); //set role as ngo
		GovNgoData u=govNgoRepository.save(user); //save data into mysql table
		System.out.println(user);
		if(u!=null)
		{
		session.setAttribute("msg","Register SuccessFully!!");
		}
		else
		{
			session.setAttribute("msg","Wrong On Server!!");
		}
		}
		else //if email is already present the it show alert
		{
			session.setAttribute("msg","UserName Is Present,Try With Other Email!!");
		}
			
			
		return "redirect:/signupNgo";
	}
	

}
