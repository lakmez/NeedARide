package com.ucm.needaride.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import com.ucm.needaride.model.AppUser;
import com.ucm.needaride.model.Offer;
import com.ucm.needaride.model.Request;

public interface RideService {

	public AppUser loginUser(String uName, String pwd);
	public boolean addOffer(Offer ofr) ;
	public int addRequest(Request req);
	public ArrayList<Offer> searchRides(String originz, String destinationz, String datez,
			String timez);
	public Map getSDList() ;
	public Offer selectRide(String id) ;
	public ArrayList<Request> populateRequests() ;
	public ArrayList<Offer> populateOffers();
	public ArrayList<Offer> getMyOffers(String userName);
	public ArrayList<Request> getMyRequests(String userName);
	public int deleteOffer(String idSela);
	public int deleteRequest(String idSela);
	public void getmatchingRequest(Offer ofr) ;
	AppUser registerUser(AppUser pooler, String pwd);
}
