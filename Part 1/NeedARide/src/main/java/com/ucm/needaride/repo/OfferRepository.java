package com.ucm.needaride.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ucm.needaride.model.Offer;

public interface OfferRepository  extends CrudRepository<Offer, String> {

	
	List<Offer> findOffersBy(String source, String destination,String date, String time);
	List<Offer> findByUserName(String userName);
	// @Query(" { $and: [ { 'datetime': { $gte: (?0).toISOString() } }, {'seats': {$gt:0} } ] }")
	// @Query(" { $and: [ { 'source.latitude': { $all: [?1]}},{'destination.cordinates': {$all: [?2]}} ] }")
	List<Offer> findByDatetimeAndSourceAndDestination(String slat,String slng, String destination);
}
