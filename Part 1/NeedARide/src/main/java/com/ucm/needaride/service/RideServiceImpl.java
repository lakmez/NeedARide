package com.ucm.needaride.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoResult;
import org.springframework.data.mongodb.core.geo.GeoResults;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.ucm.needaride.model.AppUser;
import com.ucm.needaride.model.Offer;
import com.ucm.needaride.model.Request;
import com.ucm.needaride.model.Ride;
import com.ucm.needaride.repo.OfferRepository;
import com.ucm.needaride.repo.OfferRepositoryCustom;
import com.ucm.needaride.repo.RequestRepository;
import com.ucm.needaride.repo.RequestRepositoryCustom;
import com.ucm.needaride.repo.UserRepository;
import com.ucm.needaride.util.HtmlUtil;
import com.ucm.needaride.util.MailHelper;
import com.ucm.needaride.util.SendMailUtl;

public class RideServiceImpl {

	@Autowired
	UserRepository repo;

	@Autowired
	OfferRepository offerRepo;

	@Autowired
	OfferRepositoryCustom offerRepoCustom;

	@Autowired
	RequestRepository requestRepo;

	@Autowired
	RequestRepositoryCustom requestRepositoryCustom;

	@Autowired
	private JavaMailSender mailSender;
	// SendMailUtl mailer = new SendMailUtl();
	// MailHelper mailHelper = new MailHelper();

	// SimpleMailMessage email;
	/*
	 * MimeMessagePreparator preparator = new MimeMessagePreparator() {
	 * 
	 * @Override public void prepare(MimeMessage mimeMessage) throws Exception {
	 * 
	 * mimeMessage.setRecipient(Message.RecipientType.TO, new
	 * InternetAddress(order.getCustomer().getEmailAddress()));
	 * mimeMessage.setFrom(new InternetAddress("mail@mycompany.com"));
	 * mimeMessage.setContent("body", "text/html");
	 * 
	 * };
	 */
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public AppUser loginUser(String uName, String pwd) {

		// AppUser abc = new AppUser();
		// abc.setUserName(uName);
		AppUser p1 = repo.findByUserName(uName);
		if (p1.getPassword().equals(pwd))
			return p1;
		return null;
	}

	public void addOffer(Offer ofr) {
		try {
			offerRepo.save(ofr);
			AppUser driver = repo.findByUserName(ofr.getUserName());
			MailHelper mailHelper = new MailHelper();
			mailHelper.sendMail(ofr, driver);
			findMatchingRequests(ofr);
			/*
			 * String body = HtmlUtil.getMsgBody("NeedARide-Offer Added", new
			 * AppUser(), ofr, new Request(), 1, driver);
			 * 
			 * email = new SimpleMailMessage(); email.setTo(driver.getEmail());
			 * email.setSubject("NeedARide-Offer Added"); email.setText(body);
			 * 
			 * // sends the e-mail mailSender.send(email);
			 */
			// return true;

		} catch (Exception e) {
			// return false;
		}
	}

	private void findMatchingRequests(Offer ofr) {
		MailHelper mailHelper = new MailHelper();
		List<GeoResult<Request>> matchingRequests = searchRequests(
				ofr.getSource().getLatitude() + "," + ofr.getSource().getLongitude(),
				ofr.getDestination().getLatitude() + "," + ofr.getDestination().getLongitude(),
				sdf.format(ofr.getDatetime()));
		int seatNum = ofr.getSeats();
		if (seatNum > 0) {
			for (GeoResult<Request> req : matchingRequests) {
				seatNum--;				
				Request request = req.getContent();
				AppUser rider = repo.findByUserName(request.getUserName());
				//request.setMatchedOfferId(ofr.getOfferid());
				requestRepo.save(request);
				mailHelper.sendRideMatchMail(ofr, rider);
			}
		}

	}

	public boolean addRequest(Request req) {

		try {
			requestRepo.save(req);
			AppUser rider = repo.findByUserName(req.getUserName());
			MailHelper mailHelper = new MailHelper();
			mailHelper.sendMail(req, rider);

			/*
			 * String body = HtmlUtil.getMsgBody("NeedARide-Request Added",
			 * rider, null, req, 2, new AppUser());
			 * 
			 * email = new SimpleMailMessage(); email.setTo(rider.getEmail());
			 * email.setSubject("NeedARide-Request Added"); email.setText(body);
			 * 
			 * //sender.sender(rider.getEmail(), "NeedARide-Request Added",
			 * body); mailSender.send(email);
			 */
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public List<GeoResult<Offer>> searchRides(String source, String destination, String datez) {

		String slat, slng, dlat, dlng = null;
		slng = source.split(",")[1];
		slat = source.split(",")[0];
		dlng = destination.split(",")[1];
		dlat = destination.split(",")[0];

		Date date = parseDate(datez);

		GeoResults<Offer> abc = // offerRepoCustom.getMatchingOffersNear();
		offerRepoCustom.findByCordinates(slat, slng, dlat, dlng, datez);
		return abc.getContent();
	}

	public List<GeoResult<Request>> searchRequests(String source, String destination, String datez) {

		String slat, slng, dlat, dlng = null;
		slng = source.split(",")[1];
		slat = source.split(",")[0];
		dlng = destination.split(",")[1];
		dlat = destination.split(",")[0];

		Date date = parseDate(datez);

		GeoResults<Request> abc = // offerRepoCustom.getMatchingOffersNear();
		requestRepositoryCustom.findByCordinates(slat, slng, dlat, dlng, datez);
		return abc.getContent();
	}

	private Date parseDate(String datez) {
		Date date;
		try {
			if (!datez.contains("undefined"))
				date = sdf.parse(datez);
			else
				date = new Date();
		} catch (ParseException e) {
			date = new Date();
			e.printStackTrace();
		}
		return date;
	}

	/*
	 * public Map getSDList() {
	 * 
	 * return null; }
	 */

	public Offer selectRide(String offerid, String riderUserName, String requestid) {

		int seats = 0;
		Offer ofr = offerRepo.findOne(offerid);
		seats = ofr.getSeats();
		List<String> riderIds = ofr.getRiderIds();
		if (ofr.getSeats() > 0) {
			seats = seats - 1;
			if (null != requestid) {
				Request req = requestRepo.findOne(requestid);
				req.setActive(false);
				requestRepo.save(req);
			}
			//riderIds.add(e)
			ofr.setSeats(seats);
			

			AppUser rider = repo.findByUserName(riderUserName);
			AppUser driver = repo.findByUserName(ofr.getUserName());
			//riderIds.add(rider.getUserName()+" "+rider.getEmail());
			offerRepo.save(ofr);
			MailHelper mailHelper = new MailHelper();
			mailHelper.sendMailSelect(ofr, rider, driver);
			
			return ofr;
		} else {
			return null;
		}

	}

	/*
	 * public ArrayList<Request> populateRequests() {
	 * 
	 * return null; }
	 * 
	 * public ArrayList<Offer> populateOffers() {
	 * 
	 * return null; }
	 */

	public List<Offer> getMyOffers(String userName) {

		// GeoResults<Offer> geoOffers =
		// offerRepoCustom.getMatchingOffersNear();
		return offerRepo.findByUserName(userName);
	}

	public List<Request> getMyRequests(String userName) {

		return requestRepo.findByUserName(userName);

	}

	public void getmatchingRequest(Offer ofr) {

	}

	public boolean registerUser(AppUser pooler, String pwd) {
		try {
			pooler.setPassword(pwd);
			repo.save(pooler);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public ArrayList getMyProfile(String userName) {
		// TODO Auto-generated method stub
		ArrayList rides = new ArrayList();
		// rides.addAll()
		List<Offer> offers = new ArrayList<Offer>();
		List<Request> requests = new ArrayList<Request>();
		offers = getMyOffers(userName);
		rides.add(offers);
		requests = getMyRequests(userName); // requestRepo.findByUserName(userName);
		rides.add(requests);

		return rides;
	}

	public boolean deleteEntry(String id, String type) {
		// TODO Auto-generated method stub
		if (type.equals("offer")) {
			offerRepo.delete(id);
		} else {
			requestRepo.delete(id);
		}
		return true;
	}

}
