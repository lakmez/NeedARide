package com.ucm.needaride.repo;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.Distance;
import org.springframework.data.mongodb.core.geo.GeoResults;
import org.springframework.data.mongodb.core.geo.Metrics;
import org.springframework.data.mongodb.core.geo.Point;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.BasicDBObjectBuilder;
import com.ucm.needaride.model.Offer;

public class OfferRepositoryImpl implements OfferRepositoryCustom {

	@Autowired
	private MongoTemplate mongoTemplate;

	//@Override
	public GeoResults<Offer> getMatchingOffersNear() {
		/*
		Query query = new Query(Criteria.where("tickets").gt(tickets).
	            and("type").is(type));
	query.fields().include("city").include("name").include("tickets").
	include("type").include("state").include("address");*/
		int u = 0;
		Point point1 = new Point( -94.3821724, 
	            38.9108408);
		// -94.663539, 39.038890);
		Date todaysDate = new Date();
		Query query = new Query(Criteria.where("seats").gt(u).and("datetime").gt(todaysDate));/*.and("destination.cordinates").
				is(point1))*/
	/*query.fields().include("city").include("name").include("tickets").
	include("type").include("state").include("address");*/
	
	Point point = new Point(-94.6557914, 
            39.0277832);//new Point( -94.663539, 39.038890);
	NearQuery nearQuery = NearQuery.near(point).maxDistance(new Distance(3, Metrics.KILOMETERS));
	nearQuery.spherical(true);
	nearQuery.query(query);
	nearQuery.num(100);

	GeoResults<Offer> data = mongoTemplate.geoNear(nearQuery, Offer.class);//geoNear(nearQuery, Place.class); 

		return data;
	}

	@Override
	public GeoResults<Offer> findByCordinates(String s1, String s2, String d1, String d2, String dattim) {
		
		//Point point = new Point( -94.6557914, 39.0277832);	
		Point srcpoint = new Point(Double.parseDouble(s2), Double.parseDouble(s1));
		Date dateCondition = new Date();
		
		int zero=0;
		//Query query = new Query(Criteria.where("destination.latitude").is(d1).and("destination.longitude").is(d2).				
			//	and("datetime").gte(dateCondition).and("seats").gt(zero));
		
		NearQuery nearQuery = NearQuery.near(srcpoint).maxDistance(new Distance(3, Metrics.KILOMETERS));
		nearQuery.spherical(true);
		//nearQuery.query(query);
		nearQuery.num(100);
		//List<Offer> abc = mongoTemplate.find(query, Offer.class);
		
		GeoResults<Offer> data = mongoTemplate.geoNear(nearQuery, Offer.class);//geoNear(nearQuery, Place.class); 

		return data;
		//return abc;
	}


}
