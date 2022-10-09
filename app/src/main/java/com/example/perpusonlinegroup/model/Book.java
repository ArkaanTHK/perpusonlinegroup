package com.example.perpusonlinegroup.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Book {

    public static String TABLE_NAME = "books";
    public static String COLUMN_ID = "id";
    public static String COLUMN_NAME = "name";
    public static String COLUMN_AUTHOR = "author";
    public static String COLUMN_COVER = "cover";
    public static String COLUMN_SYNOPSIS = "synopsis";

    private Integer ID;
    private String Name;
    private String Author;
    private String Cover;
    private String Synopsis;

    public Book(Integer ID, String name, String author, String cover, String synopsis) {
        this.ID = ID;
        Name = name;
        Author = author;
        Cover = cover;
        Synopsis = synopsis;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getCover() {
        return Cover;
    }

    public void setCover(String cover) {
        Cover = cover;
    }

    public String getSynopsis() {
        return Synopsis;
    }

    public void setSynopsis(String synopsis) {
        Synopsis = synopsis;
    }

    public String getCoverURL(){
        return "https://isys6203-perpus-online.herokuapp.com/" + this.Cover;
    }

    public Bitmap getCoverImage(){
        try {
            URL imgUrl = new URL(this.getCoverURL());
            return BitmapFactory.decodeStream(imgUrl.openConnection().getInputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
