package com.freshworks.project.freshworksapi.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.freshworks.project.freshworksapi.classes.GovNgoData;


public interface GovNgoRepository extends CrudRepository<GovNgoData,Integer> {

	
	public GovNgoData findByEmail(String username);

	public List<GovNgoData> findByStatus(String status);



}
