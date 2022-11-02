package com.freshworks.project.freshworksapi.classes;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
@Entity
public class HotelFoodData {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String address;
	private String name;
	private String bhaji;
	private String paratha;
	private String rice;
	
	@ManyToOne(fetch=FetchType.LAZY)
	public GovNgoData govNgoData;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBhaji() {
		return bhaji;
	}

	public void setBhaji(String bhaji) {
		this.bhaji = bhaji;
	}

	public String getParatha() {
		return paratha;
	}

	public void setParatha(String paratha) {
		this.paratha = paratha;
	}

	public String getRice() {
		return rice;
	}

	public void setRice(String rice) {
		this.rice = rice;
	}

	public GovNgoData getGovNgoData() {
		return govNgoData;
	}

	public void setGovNgoData(GovNgoData govNgoData) {
		this.govNgoData = govNgoData;
	}

	public HotelFoodData(int id, String address, String name, String bhaji, String paratha, String rice,
			GovNgoData govNgoData) {
		super();
		this.id = id;
		this.address = address;
		this.name = name;
		this.bhaji = bhaji;
		this.paratha = paratha;
		this.rice = rice;
		this.govNgoData = govNgoData;
	}

	@Override
	public String toString() {
		return "HotelFoodData [id=" + id + ", address=" + address + ", name=" + name + ", bhaji=" + bhaji + ", paratha="
				+ paratha + ", rice=" + rice + ", govNgoData=" + govNgoData + "]";
	}
	
	public HotelFoodData()
	{
		
	}
}
