package com.ucm.needaride.repo;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ucm.needaride.model.AppUser;



@Repository
public interface UserRepository extends CrudRepository<AppUser, String>{

	public AppUser findByUserName(String uname);
	

	//AppUser findByUserName(String username);
}