package com.ucm.needaride.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection="requests")
public class Request extends Ride{
	    
	@Id
	private String requestId;
	private boolean isActive;
	private Date datetime;
//	private String matchedOfferId;

	private LatLong source;	
	private LatLong destination;
	
	//private String time;
	private String userName;
	
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	
    public LatLong getSource() {
		return source;
	}
	public void setSource(LatLong source) {
		this.source = source;
	}
	
	/*public String getMatchedOfferId() {
		return matchedOfferId;
	}
	public void setMatchedOfferId(String matchedOfferId) {
		this.matchedOfferId = matchedOfferId;
	}*/
	public LatLong getDestination() {
		return destination;
	}
	public void setDestination(LatLong destination) {
		this.destination = destination;
	}
	
	public Date getDatetime() {
		return datetime;
	}
	 public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
}
