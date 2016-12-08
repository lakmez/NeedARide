package com.ucm.cis.android.db;

/**
 * Created by LekshmiPriya on 4/30/2015.
 */
public class User {
    // private String userName;
    private String fstName;
    private String lstName;
    private String email;
    private String rolNo;
    private String phNo;

    public User(){

    }

    public User(String fname, String lname, String email, String roll, String phNo) {
    this.fstName = fname;
        this.lstName= lname;
        this.email=email;
        this.rolNo=roll;
        this.phNo=phNo;
    }

    public String getPhNo() {
        return phNo;
    }

    public void setPhNo(String phNo) {
        this.phNo = phNo;
    }

    public String getFstName() {
        return fstName;
    }

    public void setFstName(String fstName) {
        this.fstName = fstName;
    }

    public String getLstName() {
        return lstName;
    }

    public void setLstName(String lstName) {
        this.lstName = lstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRolNo() {
        return rolNo;
    }

    public void setRolNo(String rolNo) {
        this.rolNo = rolNo;
    }
}
