package com.ucm.needaride.repo;

import org.springframework.data.mongodb.core.geo.GeoResults;

import com.ucm.needaride.model.Offer;
import com.ucm.needaride.model.Request;

public interface RequestRepositoryCustom {

	GeoResults<Request> findByCordinates(String s1, String s2, String d1, String d2, String dattim);
}
