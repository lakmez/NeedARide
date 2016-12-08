package com.ucm.needaride.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.geo.GeoResults;

import com.ucm.needaride.model.Offer;

public interface OfferRepositoryCustom {

	GeoResults<Offer> getMatchingOffersNear();
	GeoResults<Offer> findByCordinates(String s1, String s2, String d1, String d2, String dattim);
}
