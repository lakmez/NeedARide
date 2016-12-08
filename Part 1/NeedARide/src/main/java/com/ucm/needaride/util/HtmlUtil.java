package com.ucm.needaride.util;

import java.text.SimpleDateFormat;

import com.ucm.needaride.model.AppUser;
import com.ucm.needaride.model.Offer;
import com.ucm.needaride.model.Request;

//1-offer registration, 2-request registration, 3-matching offer found, 4-accepted ur ride{userinfo of rider and offer of driver}
public class HtmlUtil {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	public static String getMsgBody(String title, AppUser userRider, Offer offer, 
			Request req, int i, AppUser driveryou){
		String body="</p></body></html>";
		String header = "<!DOCTYPE html>\n"+
				"<html>\n"+
				"<head>\n"+
				"   <title>"+title+"</title>\n"+
				"</head>\n"+
				"<body style=\"background-color:#ffffff\">\n";	
				
		if(i== 1){
			body = //ur offer details
					"<p> Hello "+driveryou.getUserName()+",<br/><br><br>"
				+"Your Ride Offer Details" +" </p><br/><br><br>"+
					"<table bgcolor=\"#2E9AFE\" border=\"1\" style=\"width: 75%\" >"+
					"<tr>"+
					"<td><label>Origin</label></td>"+
					"<td>:</td>"+offer.getSource().getAddress()+
					"</tr>"+
					"<tr>"+
					"<td><label>Destination</label></td>"+
					"<td>:</td>"+offer.getDestination().getAddress()+
					"</tr>"+
					"<tr>"+
					"<td><label>Date</label></td>"+
					"<td>:</td>"+sdf.format(offer.getDatetime())+
					"</tr>"+
					
					"<tr>"+
					"<td><label>Seats</label></td>"+
					"<td>:</td>"+offer.getSeats()+
					"</tr><br/><br/><br/>"+
					"</table>"+
					"</body></html>";

		}
		if(i==2){
			body = //ur req has been registered+
					"<p> Hello "+userRider.getUserName()+",<br/><br><br>"+
					"Your Ride Request has been registered with the following details" +" </p><br/><br><br>"+
					"<table bgcolor=\"#2E9AFE\" border=\"1\" style=\"width: 75%\" >"+
					"<tr>"+
					"<td><label>Origin</label></td>"+
					"<td>:</td>"+req.getSource().getAddress()+
					"</tr>"+
					"<tr>"+
					"<td><label>Destination</label></td>"+
					"<td>:</td>"+req.getDestination().getAddress()+
					"</tr>"+
					"<tr>"+
					"<td><label>Date</label></td>"+
					"<td>:</td>"+sdf.format(req.getDatetime())+
					"</tr>"+
					
					"</table>"+
					"</body></html>";

		}
		if(i==3){
			body = //matching offer has been found for your ride request
					"<p> Hello "+userRider.getUserName()+",<br/><br><br>"+
					"A Ride offer matching your Ride reqest has been found" +"  </p><br/><br><br>"+
					"<table bgcolor=\"#2E9AFE\" border=\"1\" style=\"width: 75%\" >"+
					"<tr>"+
					"<td><label>Origin</label></td>"+
					"<td>:</td>"+offer.getSource().getAddress()+
					"</tr>"+
					"<tr>"+
					"<td><label>Destination</label></td>"+
					"<td>:</td>"+offer.getDestination().getAddress()+
					"</tr>"+
					"<tr>"+
					"<td><label>Date</label></td>"+
					"<td>:</td>"+sdf.format(offer.getDatetime())+
					"</tr>"+
					"<tr>"+
					"<td><label>Seats Remaining</label></td>"+
					"<td>:</td>"+offer.getSeats()+
					"</tr><br/><br/><br/>"+
					"</table>"+
					"<br/><br/>"+
					"<p>Go to NeedARide and Accept This Offer</p>"+
					"</body></html>";

		}
		if(i==4){
			//the following user has accepted your ride offer.//username of rider and details of my pool-remainng seats
			body = "<p> Hello "+driveryou.getUserName()+",<br/><br><br>"+
					"The following user has accepted your Ride Offer" +"  </p><br/><br><br>"+
					"Rider Name-"+userRider.getUserName()+"<br/><br/>"+
					"Rider Email-"+userRider.getEmail()+"<br/><br/>"+
					"<table bgcolor=\"#2E9AFE\" border=\"1\" style=\"width: 75%\" >"+
					"<tr>"+
					"<td><label>Origin</label></td>"+
					"<td>:</td>"+offer.getSource().getAddress()+
					"</tr>"+
					"<tr>"+
					"<td><label>Destination</label></td>"+
					"<td>:</td>"+offer.getDestination().getAddress()+
					"</tr>"+
					"<tr>"+
					"<td><label>Date</label></td>"+
					"<td>:</td>"+sdf.format(offer.getDatetime())+
					"</tr>"+					
					"<tr>"+
					"<td><label>Seats Remaining</label></td>"+
					"<td>:</td>"+offer.getSeats()+
					"</tr><br/><br/><br/>"+
					"</table>"+
					"</body></html>";

		}
		
		if(i==5){
			//The ride you accepted is
			body = "<p> Hello "+userRider.getUserName()+",<br/><br><br>"+
					"You have accepted the following Ride Offer" +"  </p><br/><br><br>"+
					"Driver Name-"+driveryou.getUserName()+"<br/><br/>"+
					"Driver Contact-"+driveryou.getEmail()+"<br/><br/>"+
					"<table bgcolor=\"#2E9AFE\" border=\"1\" style=\"width: 75%\" >"+
					"<tr>"+
					"<td><label>Origin</label></td>"+
					"<td>:</td>"+offer.getSource().getAddress()+
					"</tr>"+
					"<tr>"+
					"<td><label>Destination</label></td>"+
					"<td>:</td>"+offer.getDestination().getAddress()+
					"</tr>"+
					"<tr>"+
					"<td><label>Date</label></td>"+
					"<td>:</td>"+sdf.format(offer.getDatetime())+
					"</tr>"+					
					"<tr>"+
					"<td><label>Seats Remaining</label></td>"+
					"<td>:</td>"+offer.getSeats()+
					"</tr><br/><br/><br/>"+
					"</table>"+
					"</body></html>";

		}
		return header+body;
	}
}


