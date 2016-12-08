package com.ucm.needaride.util;

import com.ucm.needaride.model.AppUser;
import com.ucm.needaride.model.Offer;
import com.ucm.needaride.model.Request;
import com.ucm.needaride.model.Ride;

public class MailHelper {

	HtmlUtil util;// = new HtmlUtil();
	SendMailUtl sender = new SendMailUtl();
	public void sendMail(Ride ofr, AppUser driver) {
		
		if(ofr instanceof Offer){
			Offer offr = (Offer)ofr;
			String body = HtmlUtil.getMsgBody("NeedARide-Offer Added", new AppUser(), offr,
					new Request(), 1, 
					driver);
			
			sender.sender(driver.getEmail(), "NeedARide-Offer Added", body);
		}
		
		if(ofr instanceof Request){
			Request req = (Request)ofr;
			String body = HtmlUtil.getMsgBody("NeedARide-Request Added", driver, null,
					req, 2, 
					new AppUser());
			
			sender.sender(driver.getEmail(), "NeedARide-Request Added", body);
		}
	}
	public void sendMailSelect(Offer ofr, AppUser rider, AppUser driver) {
		//send mail to ofr.getemail, id 
		
		String body = HtmlUtil.getMsgBody("NeedARide-User Accepted Your Ride", rider, ofr,
				new Request(), 4, 
				driver);
		sender.sender(driver.getEmail(), "NeedARide-User Accepted Your Ride", body);
		
		String body1 = HtmlUtil.getMsgBody("NeedARide-You have Accepted A Ride", rider, ofr,
				new Request(), 5, 
				driver);
		sender.sender(rider.getEmail(), "NeedARide-You have Accepted A Ride", body1);
		
	}

	public void sendRideMatchMail(Offer ofr, AppUser rider) {
		//send mail to ofr.getemail, id 
		
		String body = HtmlUtil.getMsgBody("NeedARide-Ride Available", rider, ofr,
				new Request(), 3, 
				new AppUser());
		sender.sender(rider.getEmail(), "NeedARide-Ride Available", body);
	}
}
