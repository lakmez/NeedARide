package com.ucm.needaride.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection="offers")
public class Offer extends Ride {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	@Id
	private String offerid;
	private int seats;
	private LatLong source;
	private LatLong destination;
	private String registration;	
	private Date datetime;
	
	private List<String> riderIds;
	
	
	
	private String userName;
	
	 
	public void addRider(String rider) {
		riderIds.add(rider);
	}	
	public Date getDatetime() {
		return datetime;
	}		
	
	
	
	public LatLong getDestination() {
		return destination;
	}
	public String getOfferid() {
		return offerid;
	}
	public String getRegistration() {
		return registration;
	}
	public List<String> getRiderIds() {
		return riderIds;
	}
	public int getSeat() {
		return seats;
	}
	public int getSeats() {
		return seats;
	}
	public LatLong getSource() {
		return source;
	}

	/*public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}*/
	public String getUserName() {
		return userName;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	public void setDestination(LatLong destination) {
		this.destination = destination;
	}
	public void setOfferid(String offerid) {
		this.offerid = offerid;
	}
	public void setRegistration(String registration) {
		this.registration = registration;
	}
	public void setRiderIds(List<String> riderIds) {
		this.riderIds = riderIds;
	}
	public void setSeat(int seat) {
		this.seats = seat;
	}
	public void setSeats(int seats) {
		this.seats = seats;
	}
	public void setSource(LatLong source) {
		this.source = source;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
