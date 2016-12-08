package com.ucm.needaride.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.ucm.needaride.model.Request;

@Repository
public interface RequestRepository extends CrudRepository<Request, String> {
	List<Request> findByUserName(String userName);
}
