package com.ucm.cis.android.db;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by LekshmiPriya on 4/21/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 7;
    private static final String DATABASE_NAME = "ridesDB.db";
    public static final String TABLE_OFFER_TABLE = "offersTable";
    public static final String TABLE_REQUEST_TABLE = "requestTable";
    public static final String TABLE_USER_DETAILS = "userTable";
    public static final String TABLE_USER_LOGIN = "userLogin";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SOURCE = "source";
    public static final String COLUMN_SLATITUDE = "slatitude";
    public static final String COLUMN_SLONGITUDE = "slongitude";
    public static final String COLUMN_DESTINATION = "destination";

    public static final String COLUMN_DLATITUDE = "dlatitude";
    public static final String COLUMN_DLONGITUDE = "dlongitude";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_SEATS = "seats";
    public static final String COLUMN_REGISTRATION = "registration";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_ACTIVE = "isactive";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_FIRSTNAME = "firstname";
    public static final String COLUMN_LASTNAME = "lastname";
    public static final String COLUMN_ROLLNO = "rollno";
    public static final String COLUMN_PHNO = "phno";
    public static final String COLUMN_PASSWORD = "password";
    private ContentResolver myCR;
    private User user;


    public DatabaseHelper(Context context,
                          SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        //myCR = context.getContentResolver();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    private void createTable(SQLiteDatabase db) {
        String CREATE_OFFER_TABLE = "CREATE TABLE " + TABLE_OFFER_TABLE + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_SOURCE + " TEXT, "
                + COLUMN_SLATITUDE + " TEXT, "
                + COLUMN_SLONGITUDE + " TEXT, "
                + COLUMN_DESTINATION + " TEXT, "
                + COLUMN_DLATITUDE + " TEXT, "
                + COLUMN_DLONGITUDE + " TEXT, "
                + COLUMN_DATE + " DATE, "
                + COLUMN_TIME + " TIME, "
                + COLUMN_SEATS + " INTEGER, "
                + COLUMN_REGISTRATION + " TEXT, "
                + COLUMN_USERNAME + " TEXT"
                + ")";
        String CREATE_REQUEST_TABLE = "CREATE TABLE " + TABLE_REQUEST_TABLE + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_SOURCE + " TEXT, "
                + COLUMN_SLATITUDE + " TEXT, "
                + COLUMN_SLONGITUDE + " TEXT, "
                + COLUMN_DESTINATION + " TEXT, "
                + COLUMN_DLATITUDE + " TEXT, "
                + COLUMN_DLONGITUDE + " TEXT, "
                + COLUMN_DATE + " DATE, "
                + COLUMN_TIME + " TIME, "
                + COLUMN_ACTIVE + " TEXT, "
                + COLUMN_USERNAME + " TEXT"
                + ")";

        String CREATE_USER_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER_LOGIN + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_PASSWORD + " TEXT "
                + ")";

        String CREATE_USER_DETAILS = "CREATE TABLE " + TABLE_USER_DETAILS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_FIRSTNAME + " TEXT, "
                + COLUMN_LASTNAME + " TEXT, "
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_ROLLNO + " TEXT, "
                + COLUMN_PHNO + " TEXT "
                + ")";
        db.execSQL(CREATE_OFFER_TABLE);
        db.execSQL(CREATE_REQUEST_TABLE);
        db.execSQL(CREATE_USER_LOGIN_TABLE);
        db.execSQL(CREATE_USER_DETAILS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OFFER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REQUEST_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_DETAILS);
        onCreate(db);
    }

    public long addAnOffer(Offer offer) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_SOURCE, offer.getSource());
        values.put(COLUMN_SLATITUDE, offer.getSlatLong().getLatitude());
        values.put(COLUMN_SLONGITUDE, offer.getSlatLong().getLongitude());
        values.put(COLUMN_DESTINATION, offer.getDestination());
        values.put(COLUMN_DLATITUDE, offer.getDlatLong().getLatitude());
        values.put(COLUMN_DLONGITUDE, offer.getDlatLong().getLongitude());
        values.put(COLUMN_DATE, offer.getDate());
        values.put(COLUMN_TIME, offer.getTime());
        values.put(COLUMN_SEATS, offer.getSeats());
        values.put(COLUMN_REGISTRATION, offer.getRegistration());
        values.put(COLUMN_USERNAME, offer.getUserName());

        // insert row
        long offer_id = db.insert(TABLE_OFFER_TABLE, null, values);
        if (offer_id != -1) {
            System.out.println(" row id" + offer_id);
        }
        return offer_id;
    }

    public long addRequest(Request request) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SOURCE, request.getSource());
        values.put(COLUMN_SLATITUDE, request.getSlatLong().getLatitude());
        values.put(COLUMN_SLONGITUDE, request.getSlatLong().getLatitude());
        values.put(COLUMN_DESTINATION, request.getDestination());
        values.put(COLUMN_DLATITUDE, request.getDlatLong().getLatitude());
        values.put(COLUMN_DLONGITUDE, request.getDlatLong().getLatitude());
        values.put(COLUMN_DATE, request.getDate());
        values.put(COLUMN_TIME, request.getTime());
        values.put(COLUMN_USERNAME, request.getUserName());
        values.put(COLUMN_ACTIVE, request.isActive());

        // insert row
        long req_id = db.insert(TABLE_REQUEST_TABLE, null, values);
        if (req_id != -1) {
            System.out.println(" row id" + req_id);
        }
        return req_id;
    }

    public ArrayList<Offer> findOffer(String source, String dest, String date, String time) {

        String query = "SELECT * FROM " + TABLE_OFFER_TABLE + " WHERE source = " + "'" + source + "'" + " AND destination = " + "'" + dest + "';";
        if (null != date && date.trim().length() != 0)
            query = "SELECT * FROM " + TABLE_OFFER_TABLE + " WHERE source = " + "'" + source + "'" + " AND destination = " + "'" + dest + "'"
                    + " AND date = '" + date + "';";
        if (null != time && time.trim().length() != 0)
            query = "SELECT * FROM " + TABLE_OFFER_TABLE + " WHERE source = " + "'" + source + "'" + " AND destination = " + "'" + dest + "'"
                    + " AND time = '" + time + "';";
        if (null != date && null != time && date.trim().length() != 0 && time.trim().length() != 0)
            query = "SELECT * FROM " + TABLE_OFFER_TABLE + " WHERE source = " + "'" + source + "'" + " AND destination = " + "'" + dest + "'"
                    + " AND time = '" + time + "'" + " AND date = '" + date + "';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        ArrayList<Offer> offerList = new ArrayList<>();
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Offer td = new Offer();
                td.setId(c.getInt(c.getColumnIndex(COLUMN_ID)));
                td.setSource(c.getString(c.getColumnIndex(COLUMN_SOURCE)));
                td.setSlatLong(new LatLong(c.getString(c.getColumnIndex(COLUMN_SLATITUDE)),
                        c.getString(c.getColumnIndex(COLUMN_SLONGITUDE))));
                td.setDestination(c.getString(c.getColumnIndex(COLUMN_DESTINATION)));
                td.setDlatLong(new LatLong(c.getString(c.getColumnIndex(COLUMN_DLATITUDE)),
                        c.getString(c.getColumnIndex(COLUMN_DLONGITUDE))));
                td.setDate(c.getString(c.getColumnIndex(COLUMN_DATE)));
                td.setTime(c.getString(c.getColumnIndex(COLUMN_TIME)));
                td.setSeats(c.getInt(c.getColumnIndex(COLUMN_SEATS)));
                td.setRegistration(c.getString(c.getColumnIndex(COLUMN_REGISTRATION)));
                td.setUserName(c.getString(c.getColumnIndex(COLUMN_USERNAME)));
                // adding to offer list
                offerList.add(td);
            } while (c.moveToNext());
        }

        return offerList;

    }

    public double registerRide(int id, int newSeats) {
        //modifies seat count
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SEATS, newSeats);

        // updating row
        return db.update(TABLE_OFFER_TABLE, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    /**
     * ******not clean****
     */
    public boolean deleteTitle(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete(TABLE_OFFER_TABLE, COLUMN_ID + "=" + id, null) > 0;
    }

    public void delete(int id) {
        // String table = "beaconTable";
        SQLiteDatabase db = this.getReadableDatabase();
        String whereClause = COLUMN_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(id)};
        db.delete(TABLE_OFFER_TABLE, whereClause, whereArgs);
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    public User getRegisteredUser(String username, String password) {
        String query = "Select * from " + TABLE_USER_LOGIN + " where " + COLUMN_EMAIL + "='" + username + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        String pass2 = "";
        if (c.moveToFirst()) {
            do {
                pass2 = c.getString(c.getColumnIndex(COLUMN_PASSWORD));
            } while (c.moveToNext());
        }
        if (pass2.equals(password)) {
            user = getUserDetails(username);
        }
        return user;
    }

    public User registerUser(User user, String password) {
        /********************/
      //  User lek = new User("Lekshmi", "Geetha", "lakme.1989@gmail.com", "70056", "9133132727");
        addUserDetails(user);
        addUserLogin(user.getEmail(), password);
        return user;
        /*********************/
    }

    private User getUserDetails(String username) {
        String query = "Select * from " + TABLE_USER_DETAILS + " where " + COLUMN_EMAIL + "='" + username + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        User user = new User();
        if (c.moveToFirst()) {
            do {
                user.setEmail(c.getString(c.getColumnIndex(COLUMN_EMAIL)));
                user.setFstName(c.getString(c.getColumnIndex(COLUMN_FIRSTNAME)));
                user.setLstName(c.getString(c.getColumnIndex(COLUMN_LASTNAME)));
                user.setPhNo(c.getString(c.getColumnIndex(COLUMN_PHNO)));
                user.setRolNo(c.getString(c.getColumnIndex(COLUMN_ROLLNO)));
            } while (c.moveToNext());
        }
        return user;
    }

    private void addUserDetails(User user) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_FIRSTNAME, user.getFstName());
        values.put(COLUMN_LASTNAME, user.getLstName());
        values.put(COLUMN_ROLLNO, user.getRolNo());
        values.put(COLUMN_PHNO, user.getPhNo());
        // insert row
        long req_id = db.insert(TABLE_USER_DETAILS, null, values);
        if (req_id != -1) {
            System.out.println(" row id" + req_id);
        }
    }

    private void addUserLogin(String email, String password) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        // insert row
        long req_id = db.insert(TABLE_USER_LOGIN, null, values);
        if (req_id != -1) {
            System.out.println(" row id" + req_id);
        }
    }

    public ArrayList<Offer> getAllOffers(String condition) {
        //ArrayList<Offer> offerArrayList = new ArrayList<>();
        String query = "Select * from " + TABLE_OFFER_TABLE + condition;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        ArrayList<Offer> offerList = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                Offer td = new Offer();
                td.setId(c.getInt(c.getColumnIndex(COLUMN_ID)));
                td.setSource(c.getString(c.getColumnIndex(COLUMN_SOURCE)));
                td.setSlatLong(new LatLong(c.getString(c.getColumnIndex(COLUMN_SLATITUDE)),
                        c.getString(c.getColumnIndex(COLUMN_SLONGITUDE))));
                td.setDestination(c.getString(c.getColumnIndex(COLUMN_DESTINATION)));
                td.setDlatLong(new LatLong(c.getString(c.getColumnIndex(COLUMN_DLATITUDE)),
                        c.getString(c.getColumnIndex(COLUMN_DLONGITUDE))));
                td.setDate(c.getString(c.getColumnIndex(COLUMN_DATE)));
                td.setTime(c.getString(c.getColumnIndex(COLUMN_TIME)));
                td.setSeats(c.getInt(c.getColumnIndex(COLUMN_SEATS)));
                td.setRegistration(c.getString(c.getColumnIndex(COLUMN_REGISTRATION)));
                td.setUserName(c.getString(c.getColumnIndex(COLUMN_USERNAME)));

                offerList.add(td);
            } while (c.moveToNext());
        }
        return offerList;
    }


    public ArrayList<Request> getAllMatchingRequests(String condition) {

        String query = "Select * from " + TABLE_REQUEST_TABLE + condition;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        ArrayList<Request> offerList = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                Request td = new Request();
                td.setId(c.getInt(c.getColumnIndex(COLUMN_ID)));
                td.setSource(c.getString(c.getColumnIndex(COLUMN_SOURCE)));
                td.setSlatLong(new LatLong(c.getString(c.getColumnIndex(COLUMN_SLATITUDE)),
                        c.getString(c.getColumnIndex(COLUMN_SLONGITUDE))));
                td.setDestination(c.getString(c.getColumnIndex(COLUMN_DESTINATION)));
                td.setDlatLong(new LatLong(c.getString(c.getColumnIndex(COLUMN_DLATITUDE)),
                        c.getString(c.getColumnIndex(COLUMN_DLONGITUDE))));
                td.setDate(c.getString(c.getColumnIndex(COLUMN_DATE)));
                td.setTime(c.getString(c.getColumnIndex(COLUMN_TIME)));
                if (c.getString(c.getColumnIndex(COLUMN_ACTIVE)).equals("1")) {
                    td.setActive(true);
                } else {
                    td.setActive(false);
                }
                // td.setActive(c.getString(c.getColumnIndex(COLUMN_ACTIVE)));
                // td.setSeats(c.getInt(c.getColumnIndex(COLUMN_SEATS)));
                // td.setRegistration(c.getString(c.getColumnIndex(COLUMN_REGISTRATION)));
                td.setUserName(c.getString(c.getColumnIndex(COLUMN_USERNAME)));

                offerList.add(td);
            } while (c.moveToNext());
        }
        return offerList;
    }
}


