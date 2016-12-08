package com.ucm.needaride;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoResult;
import org.springframework.data.mongodb.core.geo.GeoResults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ucm.needaride.model.AppUser;
import com.ucm.needaride.model.LatLong;
import com.ucm.needaride.model.Offer;
import com.ucm.needaride.model.Request;
import com.ucm.needaride.model.Ride;
import com.ucm.needaride.service.RideServiceImpl;

/**
 * Handles requests for the application home page.
 */

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	// RideShareResource rideShareResource;
	AppUser appUser;
	String msg;

	@Autowired
	RideServiceImpl rideService;

	@RequestMapping("/")
	public String lhi() {
		return "home";
	}

	// http://localhost:8080/NeedARide/login?userName=abc&password=here&destination=der&datetime=dat&type=offer

	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.GET, produces = "application/json")
	public AppUser login(@RequestParam(value = "userName") String userName,
			@RequestParam(value = "password") String password) {

		AppUser user = rideService.loginUser(userName, password);

		// return Response.status(200).entity(user).build();
		return user;
	}

	/*
	 * http://localhost:8080/NeedARide/register?userName=piyusraj&firstName=
	 * piyush&lastName=raj &email=p@pmail.com&phno=999999&password=pass
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.GET, headers = "Accept=application/json", produces = "application/json")
	public AppUser register(@RequestParam(value = "userName", required = false) String userName,
			@RequestParam(value = "firstName", required = false) String firstName,
			@RequestParam(value = "lastName", required = false) String lastName,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "phno", required = false) String phno,
			@RequestParam(value = "password", required = false) String password) {
		AppUser abc = new AppUser();
		abc.setFirstName(firstName);
		abc.setLastName(lastName);
		abc.setUserName(userName);
		abc.setEmail(email);
		abc.setPhNo(phno);
		// abc.setPassword(password);
		boolean ret = rideService.registerUser(abc, password);
		if (ret)
			return abc;
		else
			return null;
	}

	// http://localhost:8080/NeedARide/offer?userName=piyusraj&source=34.998,-46.889&destination=35.3345,-46.987
	// &datetime=datetime&seats=3

	@ResponseBody
	@RequestMapping(value = "/offer", method = RequestMethod.GET, produces = "application/json")
	public Offer addOffer(@RequestParam(value = "userName", required = false) String userName,
			@RequestParam(value = "source", required = false) String source,
			@RequestParam(value = "sAddress", required = false) String sAddress,
			@RequestParam(value = "destination", required = false) String destination,
			@RequestParam(value = "dAddress", required = false) String dAddress,
			@RequestParam(value = "datetime", required = false) String datetime,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "seats", required = false) String seats) {

		Offer ofr = new Offer();
		ofr.setUserName(userName);
		ofr.setSource(new LatLong(source, sAddress));
		ofr.setDestination(new LatLong(destination, dAddress));

		try {
			if(datetime.contains("/")){datetime = datetime.replaceAll("/", " ");}
			Date datetime1 = sdf.parse(datetime);
			
			sdf.parse(datetime.replaceAll("/", " "));
			ofr.setDatetime(datetime1);
			ofr.setSeats(Integer.parseInt(seats));
			 rideService.addOffer(ofr);
			//if (ret)
				return ofr;
			//else
				//return null;
		} catch (Exception e) {
			logger.error("Error Occured while parsing date"+e.getMessage());
		}
		return null;
	}

	/*
	 * 
	 * http://localhost:8080/NeedARide/request?userName=piyusraj&source=34.998,-
	 * 46.889&destination=35.3345,-46.987 &datetime=datetime&seats=3
	 */
	@ResponseBody
	@RequestMapping(value = "/request", method = RequestMethod.GET, produces = "application/json")
	public Request addRequest(@RequestParam(value = "userName", required = false) String userName,
			@RequestParam(value = "source", required = false) String source,
			@RequestParam(value = "sAddress", required = false) String sAddress,
			@RequestParam(value = "destination", required = false) String destination,
			@RequestParam(value = "dAddress", required = false) String dAddress,
			@RequestParam(value = "datetime", required = false) String datetime) {

		Request req = new Request();
		req.setUserName(userName);
		req.setSource(new LatLong(source, sAddress));
		// req.setSource(sAddress);
		req.setDestination(new LatLong(destination, dAddress));
		// req.setDestination(dAddress);

		// String tim = format.format(new java.sql.Timestamp(today.getTime()));
		if(datetime.contains("/")){datetime = datetime.replaceAll("/", " ");}
		try {
			Date datetime1 = sdf.parse(datetime);
			req.setDatetime(datetime1);
			req.setActive(true);// (Integer.parseInt(seats));
			boolean ret = rideService.addRequest(req);// (ofr);
			if (ret)
				return req;
			else
				return null;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// http://localhost:8080/NeedARide/search?track=abc&source=here&destination=der&datetime=dat&type=offer
	@ResponseBody
	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
	public List<GeoResult<Offer>> findRides(@RequestParam(value = "track", required = false) String track,
			@RequestParam(value = "source", required = false) String source,
			@RequestParam(value = "destination", required = false) String destination,
			@RequestParam(value = "datetime", required = false) String datetime,
			@RequestParam(value = "type", required = false) String type) {

		//String dat = parseDate(datetime);
		if(datetime.contains("/")){datetime = datetime.replaceAll("/", " ");}
		List<GeoResult<Offer>> abc = rideService.searchRides(source, destination, datetime);
		return abc;
	}

	// http://localhost:8080/NeedARide/accept?offerid=5718e7f60a6389d88b7088c8&riderid=kkk&requestid=57006fd601637d7a8c09e9bf
	@ResponseBody
	@RequestMapping(value = "/accept", method = RequestMethod.GET, produces = "application/json")
	public Offer accept(@RequestParam(value = "offerid", required = false) String offerid,
			@RequestParam(value = "riderid", required = false) String riderid,
			@RequestParam(value = "requestid", required = false) String rid) {
		Offer ofr = rideService.selectRide(offerid, riderid, rid);
		return ofr;
	}

	// http://localhost:8080/NeedARide/myProfile?userName=piyusraj
	@ResponseBody
	@RequestMapping(value = "/myProfile", method = RequestMethod.GET, produces = "application/json")
	public List<Ride> myProfile(@RequestParam(value = "userName", required = false) String userName) {
		ArrayList olist = rideService.getMyProfile(userName);
		return olist;
	}

	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET, produces = "application/json")
	public void delete(@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "type", required = false) String type) {
		rideService.deleteEntry(id, type);

	}

	@ResponseBody
	@RequestMapping(value = "/select", method = RequestMethod.GET, produces = "application/json")
	public Offer select(@RequestParam(value = "id", required = false) String offerid,
			@RequestParam(value = "userName", required = false) String userName,
			@RequestParam(value = "reqId", required = false) String reqId) {
		Offer ofr = rideService.selectRide(offerid, userName, reqId) ;//(id, type);
		return ofr;
	}

}
