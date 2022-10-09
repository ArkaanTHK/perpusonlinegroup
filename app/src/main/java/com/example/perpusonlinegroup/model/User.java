package com.example.perpusonlinegroup.model;

import java.util.Calendar;

public class User {

    public static String TABLE_NAME = "users";
    public static String COLUMN_ID = "id";
    public static String COLUMN_EMAIL = "email";
    public static String COLUMN_PASSWORD = "password";
    public static String COLUMN_PHONE = "phone_number";
    public static String COLUMN_DOB = "date_of_birth";
    public static Integer SESSIONID = 0;

    private Integer ID;
    private String Email;
    private String Password;
    private String PhoneNumber;
    private Calendar DOB;

    public User(Integer ID, String email, String password, String phoneNumber, Calendar DOB) {
        this.ID = ID;
        Email = email;
        Password = password;
        PhoneNumber = phoneNumber;
        this.DOB = DOB;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public Calendar getDOB() {
        return DOB;
    }

    public void setDOB(Calendar DOB) {
        this.DOB = DOB;
    }
}
