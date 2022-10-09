package com.example.perpusonlinegroup.service;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import com.example.perpusonlinegroup.R;
import com.example.perpusonlinegroup.model.Book;
import com.example.perpusonlinegroup.model.Request;
import com.example.perpusonlinegroup.model.User;

import java.util.List;
import java.util.Vector;

public class RequestService extends DatabaseService<Request> {

    public static Integer CURRENT_ID = 1;

    public RequestService(@Nullable Context context) {
        super(context);
    }

    @Override
    public void InsertMany(Vector<Request> data) {
        SQLiteDatabase db = this.getWritableDatabase();
        for(Request r : data){
            ContentValues cv = new ContentValues();
            cv.put(Request.COLUMN_BOOK_ID, r.getBookID());
            cv.put(Request.COLUMN_REQUESTER_ID, r.getRequesterID());
            cv.put(Request.COLUMN_RECEIVER_ID, r.getReceiverID());
            cv.put(Request.COLUMN_LATITUDE, r.getLatitude());
            cv.put(Request.COLUMN_LONGITUDE, r.getLongitude());
            db.insert(Request.TABLE_NAME, null, cv);
        }
    }

    @Override
    public void Insert(Request data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Request.COLUMN_BOOK_ID, data.getBookID());
        cv.put(Request.COLUMN_REQUESTER_ID, data.getRequesterID());
        cv.put(Request.COLUMN_RECEIVER_ID, data.getReceiverID());
        cv.put(Request.COLUMN_LATITUDE, data.getLatitude());
        cv.put(Request.COLUMN_LONGITUDE, data.getLongitude());
        db.insert(Request.TABLE_NAME, null, cv);
    }

    @SuppressLint("Range")
    @Override
    public Vector<Request> GetAll() {
        Vector<Request> res = new Vector<Request>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query(Request.TABLE_NAME,
                new String[]{ Request.COLUMN_ID, Request.COLUMN_BOOK_ID, Request.COLUMN_REQUESTER_ID, Request.COLUMN_RECEIVER_ID, Request.COLUMN_LATITUDE, Request.COLUMN_LONGITUDE},
                null,
                null,
                null,
                null,
                null,
                null);

        while (c.moveToNext()){
            res.add(new Request(
                    c.getInt(c.getColumnIndex(Request.COLUMN_ID)),
                    c.getInt(c.getColumnIndex(Request.COLUMN_BOOK_ID)),
                    c.getInt(c.getColumnIndex(Request.COLUMN_REQUESTER_ID)),
                    c.getInt(c.getColumnIndex(Request.COLUMN_RECEIVER_ID)),
                    c.getDouble(c.getColumnIndex(Request.COLUMN_LATITUDE)),
                    c.getDouble(c.getColumnIndex(Request.COLUMN_LONGITUDE))
            ));
        }
        c.close();
        db.close();
        return res;
    }

    @SuppressLint("Range")
    @Override
    public Request GetByID(Integer ID) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(Request.TABLE_NAME,
                new String[]{ Request.COLUMN_ID, Request.COLUMN_BOOK_ID, Request.COLUMN_REQUESTER_ID, Request.COLUMN_RECEIVER_ID, Request.COLUMN_LATITUDE, Request.COLUMN_LONGITUDE},
                Request.COLUMN_ID + " = ?",
                new String[]{String.valueOf(ID)},
                null,
                null,
                null,
                null);
        if(c.moveToNext()){
            return new Request(
                    c.getInt(c.getColumnIndex(Request.COLUMN_ID)),
                    c.getInt(c.getColumnIndex(Request.COLUMN_BOOK_ID)),
                    c.getInt(c.getColumnIndex(Request.COLUMN_REQUESTER_ID)),
                    c.getInt(c.getColumnIndex(Request.COLUMN_RECEIVER_ID)),
                    c.getDouble(c.getColumnIndex(Request.COLUMN_LATITUDE)),
                    c.getDouble(c.getColumnIndex(Request.COLUMN_LONGITUDE))
            );
        }
        c.close();
        db.close();
        return null;
    }

    public void AcceptRequest(Integer requestID, Integer userReceiverID){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Request.COLUMN_RECEIVER_ID, userReceiverID);

        db.update(
                Request.TABLE_NAME,
                cv,
                String.format("%s = ?", Request.COLUMN_ID),
                new String[]{ requestID.toString() }
        );
        db.close();
    }
}
