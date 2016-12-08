package com.ucm.needaride.repo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.Distance;
import org.springframework.data.mongodb.core.geo.GeoResults;
import org.springframework.data.mongodb.core.geo.Metrics;
import org.springframework.data.mongodb.core.geo.Point;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;

import com.ucm.needaride.model.Offer;
import com.ucm.needaride.model.Request;

public class RequestRepositoryImpl implements RequestRepositoryCustom{

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public GeoResults<Request> findByCordinates(String s1, String s2, String d1, String d2, String dattim) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		//Point point = new Point( -94.6557914, 39.0277832);	
		Point srcpoint = new Point(Double.parseDouble(s2), Double.parseDouble(s1));
		Date dateCondition = new Date();
		
		try{
			dateCondition = sdf.parse(dattim);
		}catch(Exception e){}
		
		int zero=0;
		Query query = new Query(Criteria.where("destination.latitude").is(d1).and("destination.longitude").is(d2).				
				and("datetime").gte(dateCondition));
		
		NearQuery nearQuery = NearQuery.near(srcpoint).maxDistance(new Distance(3, Metrics.KILOMETERS));
		nearQuery.spherical(true);
		nearQuery.query(query);
		nearQuery.num(100);
		//List<Offer> abc = mongoTemplate.find(query, Offer.class);
		
		GeoResults<Request> data = mongoTemplate.geoNear(nearQuery, Request.class);//geoNear(nearQuery, Place.class); 

		return data;
		//return abc;
	}

}
