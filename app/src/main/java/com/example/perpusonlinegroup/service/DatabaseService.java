package com.example.perpusonlinegroup.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.perpusonlinegroup.model.Book;
import com.example.perpusonlinegroup.model.Request;
import com.example.perpusonlinegroup.model.User;

import java.util.Vector;

public abstract class DatabaseService<T> extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "perpus_online_group";
    public static Integer DATABASE_VERSION = 1;
    public static Boolean IS_MIGRATED = false;

    public DatabaseService(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    private void insertTable(@NonNull SQLiteDatabase db){
        db.rawQuery("DROP TABLE IF EXISTS requests", null);
        db.rawQuery("DROP TABLE IF EXISTS books", null);
        db.rawQuery("DROP TABLE IF EXISTS users", null);
        db.execSQL(String.format("" +
                "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "%s TEXT," +
                    "%s TEXT," +
                    "%s TEXT," +
                    "%s TEXT" +
                ")", User.TABLE_NAME,
                User.COLUMN_ID, User.COLUMN_EMAIL, User.COLUMN_PASSWORD, User.COLUMN_PHONE, User.COLUMN_DOB));

        db.execSQL(String.format("" +
                        "CREATE TABLE %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT," +
                        "%s TEXT," +
                        "%s TEXT," +
                        "%s TEXT" +
                        ")", Book.TABLE_NAME,
                Book.COLUMN_ID, Book.COLUMN_NAME, Book.COLUMN_AUTHOR, Book.COLUMN_COVER, Book.COLUMN_SYNOPSIS));

        db.execSQL(String.format("" +
                        "CREATE TABLE %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s INTEGER," +
                        "%s INTEGER," +
                        "%s INTEGER," +
                        "%s REAL," +
                        "%s REAL," +
                        "FOREIGN KEY (%s) REFERENCES %s(%s)," +
                        "FOREIGN KEY (%s) REFERENCES %s(%s)," +
                        "FOREIGN KEY (%s) REFERENCES %s(%s)" +
                        ")", Request.TABLE_NAME,
                Request.COLUMN_ID, Request.COLUMN_BOOK_ID, Request.COLUMN_REQUESTER_ID, Request.COLUMN_RECEIVER_ID, Request.COLUMN_LATITUDE, Request.COLUMN_LONGITUDE,
                Request.COLUMN_BOOK_ID, Book.TABLE_NAME, Book.COLUMN_ID,
                Request.COLUMN_REQUESTER_ID, User.TABLE_NAME, User.COLUMN_ID,
                Request.COLUMN_RECEIVER_ID, User.TABLE_NAME, User.COLUMN_ID));
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        insertTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int old_version, int new_version) {
        sqLiteDatabase.rawQuery("DROP TABLE IF EXISTS requests", null);
        sqLiteDatabase.rawQuery("DROP TABLE IF EXISTS books", null);
        sqLiteDatabase.rawQuery("DROP TABLE IF EXISTS users", null);
        insertTable(this.getWritableDatabase());
    }

    public abstract void InsertMany(Vector<T> data);
    public abstract void Insert(T data);

    public abstract Vector<T> GetAll();
    public abstract T GetByID(Integer ID);
}
