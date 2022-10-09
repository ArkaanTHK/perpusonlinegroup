package com.example.perpusonlinegroup.model;

public class Request {

    public static String TABLE_NAME = "requests";
    public static String COLUMN_ID = "id";
    public static String COLUMN_BOOK_ID = "book_id";
    public static String COLUMN_REQUESTER_ID = "requester_id";
    public static String COLUMN_RECEIVER_ID = "receiver_id";
    public static String COLUMN_LATITUDE = "latitude";
    public static String COLUMN_LONGITUDE = "longitude";

    private Integer ID;
    private Integer BookID;
    private Integer RequesterID;
    private Integer ReceiverID;
    private Double Latitude;
    private Double Longitude;

    public Request(Integer ID, Integer bookID, Integer requesterID, Integer receiverID, Double latitude, Double longitude) {
        this.ID = ID;
        BookID = bookID;
        RequesterID = requesterID;
        ReceiverID = receiverID;
        Latitude = latitude;
        Longitude = longitude;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getBookID() {
        return BookID;
    }

    public void setBookID(Integer bookID) {
        BookID = bookID;
    }

    public Integer getRequesterID() {
        return RequesterID;
    }

    public void setRequesterID(Integer requesterID) {
        RequesterID = requesterID;
    }

    public Integer getReceiverID() {
        return ReceiverID;
    }

    public void setReceiverID(Integer receiverID) {
        ReceiverID = receiverID;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }
}
