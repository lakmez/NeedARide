/*package com.ucm.needaride.dao;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.ucm.needaride.model.AppUser;
import com.ucm.needaride.model.Offer;
import com.ucm.needaride.model.Request;

public class RideDaoImpl implements RideDao {
	private String dbname;
	private String userid;
	private String password;
	private String server;
	private int port;
	private Jongo jongo;
	private String offersCollectionName;
	private String requestsCollectionName;
	private String loginCollectionName;
	private static final Logger logger = LoggerFactory.getLogger(RideDaoImpl.class);
	MongoCollection offersCollection;
	MongoCollection requestsCollection;
	MongoCollection loginCollection;
	
	public RideDaoImpl() {
		// TODO Auto-generated constructor stub
		//initialise();
	}

	public void initialise() {

		try {
			File file = new File("src/test/resources/db.properties");
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);

			userid = properties.getProperty("userName");
			password = properties.getProperty("password");
			dbname = properties.getProperty("mongodbname");
			server = properties.getProperty("server");
			port = Integer.parseInt(properties.getProperty("port"));

			offersCollectionName = properties.getProperty("offerCollectionname");
			requestsCollectionName = properties.getProperty("requestCollectionname");
			loginCollectionName =properties.getProperty("loginCollectionName");
			
			
			
			userid = "admin";
					password = "admin";
					dbname = "ridesdb";
					server = "localhost";
					port = 27017;

					offersCollectionName = "offers";
					requestsCollectionName = "requests";
					loginCollectionName = "users";
					
			List<MongoCredential> credentials = Arrays
					.asList(MongoCredential.createMongoCRCredential(userid, dbname, password.toCharArray()));
			ServerAddress serveraddress = new ServerAddress(server, port);
			MongoClient mongoclient = new MongoClient(serveraddress, credentials);

			jongo = new Jongo(mongoclient.getDB(dbname));
			// MongoCollection offers = jongo.getCollection("offers");

			offersCollection = jongo.getCollection(offersCollectionName);
			requestsCollection = jongo.getCollection(requestsCollectionName);
			loginCollection = jongo.getCollection(loginCollectionName);
			//loginCollection = jongo.getCollection("rideUsers");

		} catch (Exception e) {
			logger.info("Error reading database properties file \n" + e.getMessage());
		}
	}

	public boolean addOffer(Offer offer) {
		try {
			//offersCollection.insert(offer);
			return true;
		} catch (Exception e) {
			logger.error("Add offer Error" + e.getStackTrace());
			return false;
		}

	}

	public boolean addRequest(Request request, String pass) {
		try {
			//requestsCollection.insert(request);
			return true;
		} catch (Exception e) {
			logger.error("Add Request Error" + e.getStackTrace());
			return false;
		}
	}

	public boolean findOffer(AppUser pooler, String pass) {
		// TODO Auto-generated method stub
		
		//Iterable<Offer> users = offersCollection.find("{age: 18}").as(Offer.class);
		return false;
	}

	public boolean findRequest(AppUser pooler, String pass) {
		// TODO Auto-generated method stub
		
		
		
		 *  MongoClient client = new MongoClient(new ServerAddress("192.168.2.4", 27017));

    MongoDatabase db = client.getDatabase("test");

    MongoCollection<Document> collection = db.getCollection("sample");

    MongoCursor<Document> iterator = collection.find().iterator();

    BasicDBList list = new BasicDBList();
    while (iterator.hasNext()) {
        Document doc = iterator.next();
        list.add(doc);
    }
    System.out.println(JSON.serialize(list));
		 * 
		
		 * DBObject dbo = JSON.parse("{age: 18}");
DBCursor results = users.find(dbo);
for (DBObject result : results) {
    User user = new User();
    user.setUsername((String) result.get("username"));
    user.setAge((Integer) result.get("age"));
    user.setAddress(new Address(..));
}
		 * 
		
		
		return false;
	}

	public AppUser loginUser(String uName, String pwd) {
		 AppUser appUser = new AppUser();
		 appUser.setUserName(uName);
		// appUser.setUserName(uName);
		 
		// AppUser appUser = loginCollection.findOne("{userName:"+uName+"}").as(AppUser.class);
		
		 return appUser;
	}

	public boolean registerUser(AppUser appUser, String pass) {
		try {
		//	loginCollection.insert(appUser);
			return true;
		} catch (Exception e) {
			logger.error("Add User Error" + e.getStackTrace());
			return false;
		}
	}
}
*/