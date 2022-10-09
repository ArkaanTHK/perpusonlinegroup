package com.example.perpusonlinegroup.service;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.perpusonlinegroup.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

public class UserService extends DatabaseService<User>{

    public UserService(@Nullable Context context) {
        super(context);
    }

    public String calendarToString(Calendar c){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        return sdf.format(c.getTime());
    }

    public Calendar convertStringToCalendar(String time){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        try {
            Date date = dateFormat.parse(time);
            assert date != null;
            calendar.setTime(date);
            return calendar;
        } catch (ParseException e) {
            return calendar;
        }
    }

    @Override
    public void InsertMany(Vector<User> data) {
        SQLiteDatabase db = this.getWritableDatabase();
        for(User u : data){
            ContentValues cv = new ContentValues();
            cv.put(User.COLUMN_EMAIL, u.getEmail());
            cv.put(User.COLUMN_PASSWORD, u.getPassword());
            cv.put(User.COLUMN_PHONE, u.getPhoneNumber());
            cv.put(User.COLUMN_DOB, calendarToString(u.getDOB()));
            db.insert(User.TABLE_NAME, null, cv);
        }
    }

    @Override
    public void Insert(User data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(User.COLUMN_EMAIL, data.getEmail());
        cv.put(User.COLUMN_PASSWORD, data.getPassword());
        cv.put(User.COLUMN_PHONE, data.getPhoneNumber());
        cv.put(User.COLUMN_DOB, calendarToString(data.getDOB()));
        db.insert(User.TABLE_NAME, null, cv);
    }


    @SuppressLint("Range")
    public int checkUser(String email, String password) {
        String[] columns = {
                    User.COLUMN_ID, User.COLUMN_PASSWORD
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = User.COLUMN_EMAIL + " = ?" + " AND " + User.COLUMN_PASSWORD + " = ?";

        String[] selectionArgs = { email, password };

        Cursor cursor = db.query(User.TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null,
                null);                      //The sort order

        int count = cursor.getCount();
        if (count > 0) {
            cursor.moveToFirst();

            int r = cursor.getInt(cursor.getColumnIndex(User.COLUMN_ID));

            db.close();
            cursor.close();
            return r;

        } else {
            db.close();
            cursor.close();
            return 0;
        }

    }

    @Override
    public Vector<User> GetAll() {
        return null;
    }

    @SuppressLint("Range")
    @Override
    public User GetByID(Integer ID) {
        User u = null;
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query(
                User.TABLE_NAME,
                new String[]{ User.COLUMN_ID, User.COLUMN_EMAIL, User.COLUMN_PASSWORD, User.COLUMN_DOB, User.COLUMN_PHONE },
                "id = ?",
                new String[]{ ID.toString() },
                null,
                null,
                null,
                null
        );

        if (c.moveToFirst()){
            u = new User(
                    c.getInt(c.getColumnIndex(User.COLUMN_ID)),
                    c.getString(c.getColumnIndex(User.COLUMN_EMAIL)),
                    c.getString(c.getColumnIndex(User.COLUMN_PASSWORD)),
                    c.getString(c.getColumnIndex(User.COLUMN_PHONE)),
                    convertStringToCalendar(c.getString(c.getColumnIndex(User.COLUMN_DOB)))

            );
        }
        c.close();
        return u;
    }
}
