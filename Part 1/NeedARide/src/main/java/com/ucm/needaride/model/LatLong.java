package com.ucm.needaride.model;

import org.springframework.data.mongodb.core.geo.Point;

public class LatLong {
	private Double[] cordinates = new Double[2];

	
	public Double[] getCordinates() {
		return cordinates;
	}

	public void setCordinates(Double[] cordinates) {
		this.cordinates = cordinates;
	}
	private String type="Point";
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	private String latitude;
	private String longitude;
	private String address;
	
	public LatLong(String position, String address) {
		// TODO Auto-generated constructor stub
		String[] pos = position.split(",");
		this.cordinates[0] = Double.parseDouble(pos[1]);
		
		this.cordinates[1] = Double.parseDouble(pos[0]);//(pos[0]);
		this.latitude = pos[0];
		this.longitude = pos[1];
		this.address = address;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LatLong() {
		// TODO Auto-generated constructor stub
	}
	
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	

}
