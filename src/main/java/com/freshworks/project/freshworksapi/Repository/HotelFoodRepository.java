package com.freshworks.project.freshworksapi.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.freshworks.project.freshworksapi.classes.HotelFoodData;

public interface HotelFoodRepository extends CrudRepository<HotelFoodData,Integer>{

	@Query(value = "SELECT * FROM hotel_food_data WHERE gov_ngo_data_id = ?1", nativeQuery = true)
	public List<HotelFoodData> findDataByUser(int id);

	
}
