package com.freshworks.project.freshworksapi.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.freshworks.project.freshworksapi.Repository.GovNgoRepository;
import com.freshworks.project.freshworksapi.Repository.HotelFoodRepository;
import com.freshworks.project.freshworksapi.classes.GovNgoData;
import com.freshworks.project.freshworksapi.classes.HotelFoodData;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
@Controller
@RequestMapping("/NGOS")
public class NGOSController {
	
	@Autowired
	public HotelFoodRepository hotelFoodRepository;
	@Autowired
	public GovNgoRepository govNgoRepository;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String viewAllHotelInfoIntoList(Model m)
	{
		
		List<HotelFoodData> list=new ArrayList<>();
		hotelFoodRepository.findAll().forEach(list::add);//get all hotel food info into the list
		m.addAttribute("list",list);//pass the list into the view_data.html page
		//System.out.println(list);
		return "NGOS/view_data";//go to the view_data.html page
	}
	
	@RequestMapping(value="/Hotel_Book/{id}",method=RequestMethod.GET)
	public String goToTheBookPage(@PathVariable int id,Model m) {
		
		Optional<HotelFoodData> n=hotelFoodRepository.findById(id);//get the specific hotel food info by using id
		if(n!=null)
		{
			HotelFoodData notes=n.get();//id not null then get the object
			int bhaji=Integer.valueOf(notes.getBhaji());//convert no of bhaji into int
			int paratha=Integer.valueOf(notes.getParatha());//convert no of paratha into int
			int rice=Integer.valueOf(notes.getRice());//convert no of rice into int
			m.addAttribute("notes",notes);//pass the notes object to the book_info.html page
			m.addAttribute("bhaji",bhaji);//pass the bhaji object to the book_info.html page
			m.addAttribute("paratha",paratha);//pass the paratha object to the book_info.html page
			m.addAttribute("rice",rice);//pass the rice object to the book_info.html page
		}
		return "NGOS/book_info";//go to the book_info page
	}
	
	
	@RequestMapping(value="/book_food_info",method=RequestMethod.POST)
	public String bookHotelFoodInfo(@ModelAttribute HotelFoodData user,Principal p) {
		
		String email=p.getName();
		GovNgoData j=govNgoRepository.findByEmail(email);//get the current hotel user object
		Optional<HotelFoodData> oldInfo=hotelFoodRepository.findById(user.getId());//get the hotel food object using current hotel user id
		int old_id=0;
		if(oldInfo!=null)
		{
			HotelFoodData oi=oldInfo.get();//if not null get the user
			old_id=oi.getGovNgoData().getId();//specific hotel user id
			int old_paratha=Integer.valueOf(oi.getParatha());//get the old enter paratha 
			int old_bhaji=Integer.valueOf(oi.getBhaji());//get the old enter bhaji 
			int old_rice=Integer.valueOf(oi.getRice());//get the old enter rice 
			int enter_paratha=Integer.valueOf(user.getParatha());//get the user enter paratha 
			int enter_bhaji=Integer.valueOf(user.getBhaji());//get the user enter paratha 
			int enter_rice=Integer.valueOf(user.getRice());//get the user enter paratha 
			if(enter_paratha<=old_paratha && enter_bhaji<=old_bhaji && enter_rice<=old_rice)
			{
				String set_paratha=Integer.toString(old_paratha-enter_paratha);//set the after substracting  paratha 
				String set_bhaji=Integer.toString(old_bhaji-enter_bhaji);//set the after substracting  bhaji 
				String set_rice=Integer.toString(old_rice-enter_rice);//set the after substracting  rice 
				user.setGovNgoData(oi.getGovNgoData());//set the same hotel food user
				user.setParatha(set_paratha);//set the paratha to after substracting value
				user.setBhaji(set_bhaji);//set the bhaji to after substracting value
				user.setRice(set_rice);//set the rice to after substracting value
				hotelFoodRepository.save(user);//set user into mysql table
				System.out.println(user);
			}
			//create sms for ngo
			String smsforngo="You Booked The Hotel Food\n"+"Name Of Hotel: "+user.getName()+"\nAdress Of Hotel: "+user.getAddress()+"\nEntered Bhaji: "+Integer.toString(enter_bhaji)+"\nEntered Paratha: "+Integer.toString(enter_paratha)+"\nEntered Rice: "+Integer.toString(enter_rice);
			//create sms for hotel
			String smsforhotel="NGO Booked Your Hotel\n"+"Name Of NGO: "+j.getName()+"\nCity Of NGO: "+j.getCity()+"\nNGO Booked Bhaji: "+Integer.toString(enter_bhaji)+"\nNGO Booked Paratha: "+Integer.toString(enter_paratha)+"\nNGO Booked Rice: "+Integer.toString(enter_rice);
			//initiallize the sid and stoken
			Twilio.init("AC1ebbdd1ca3c489f7abfcda0bc10172d1","602cf1aeeab616a4d2fb574e0bf9fde9");
			//send sms to ngo user
			System.out.println("Sonal");
            Message.creator(new PhoneNumber("+91"+j.getPhone()),
                            new PhoneNumber("++19498066183"), smsforngo).create();
            System.out.println("Sonal");
            //send sms to hotel user
            Message.creator(new PhoneNumber("+91"+oi.getGovNgoData().getPhone()),
                    new PhoneNumber("++19498066183"), smsforhotel).create();

           
		}
		
	
		
		return "redirect:/NGOS/view_into_maps/"+Integer.toString(old_id);//go to the map page and pass the hotel user object
	}
	
	
	@RequestMapping(value="/view_into_maps/{id}",method=RequestMethod.GET)
	public ModelAndView viewIntoMap(Map<String, Object> model,Principal principal,@PathVariable int id) throws FileNotFoundException, IOException, CsvValidationException {
		
		String email=principal.getName();
		GovNgoData current_user=govNgoRepository.findByEmail(email);//current ngo object
		Optional<GovNgoData> user=govNgoRepository.findById(id);//specific hotel user object which is booked by ngo
		if(user!=null)
		{
			
		GovNgoData old_user=user.get();//if not null get the object
		String city=old_user.getCity();//get the hotel user city
		String city1=current_user.getCity();//get the ngo user city
		double p = 0,p1 = 0;//initiallize
		double p2 = 0,p12 = 0;//initiallize
		
		//path where the city csv file is present
		try (CSVReader reader = new CSVReader(new FileReader("C:\\Users\\AVCOE\\Desktop\\freshworks-api\\src\\main\\java\\city.csv"))) {
		      String[] lineInArray;
		     
		      while ((lineInArray = reader.readNext()) != null) {
		         // System.out.println(lineInArray[0]+" "+lineInArray[1]+" "+lineInArray[2]);
		    	  if(lineInArray[0].compareTo(city)==0)
		    	  {
		    		  p=Double.valueOf(lineInArray[1]);
		    		  p1=Double.valueOf(lineInArray[2]);
		    	  }
		    	  if(lineInArray[0].compareTo(city1)==0)
		    	  {
		    		  p2=Double.valueOf(lineInArray[1]);
		    		  p12=Double.valueOf(lineInArray[2]);
		    	  }
		      }
		     
		}
		System.out.println(p+" "+p1);
	    
		model.put("langi",p);//pass the longitude of 1st city
	    model.put("latti",p1);//pass the lattitude of 1st city
	    model.put("langi1",p2);//pass the longitude of 2st city
	    model.put("latti1",p12);//pass the lattitude of 2st city
		}
	    return new ModelAndView("map1");
	}
	
	@ModelAttribute
	public void addCommonData(Principal p,Model m)//principal for points to current user
	{
		String email=p.getName();
		GovNgoData user=govNgoRepository.findByEmail(email);
		m.addAttribute("user",user);
	}
	
	@RequestMapping(value="/viewNGOProfile",method=RequestMethod.GET)
	public String viewNGOProfile() {
		
		return "NGOS/view_profile";//go to the view_profile page
	}
	
	@RequestMapping(value="/updateNGOUser",method=RequestMethod.POST)
	public String updateNGOProfile(@ModelAttribute GovNgoData user,Model m,HttpSession session)
	{
		Optional<GovNgoData> Olduser=govNgoRepository.findById(user.getId());//get the object of current object
		System.out.println(Olduser);
		if(Olduser!=null)
		{
			user.setPassword(Olduser.get().getPassword());//set the same password to updated object
			//user.setRole(Olduser.get().getRole());
			//user.setEmail(Olduser.get().getEmail());
			user.setEmail(Olduser.get().getEmail());//set the same email to updated object
			user.setRole(Olduser.get().getRole());//set the same role to updated object
			user.setStatus(Olduser.get().getStatus());//set the same status to updated object
			GovNgoData updateUser=govNgoRepository.save(user);//save the user into mysql table
			if(updateUser!=null)
			{
				session.setAttribute("msg","Profile Updated Successfully!!");
				m.addAttribute("user",updateUser);//send updated profile
				
			}
			else
			{
				session.setAttribute("msg","Profile Not Updated Successfully!!");
			}
			
		}
		
		
		return "redirect:/NGOS/viewNGOProfile";//go to the view_profile.html page
	}
	
}
