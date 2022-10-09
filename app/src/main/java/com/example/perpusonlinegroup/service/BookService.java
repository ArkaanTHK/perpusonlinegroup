package com.example.perpusonlinegroup.service;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import com.example.perpusonlinegroup.model.Book;
import com.example.perpusonlinegroup.model.User;

import java.util.Vector;

public class BookService extends DatabaseService<Book>{

    private static Integer CURRENT_ID = 1;

    public BookService(@Nullable Context context) {
        super(context);
    }

    @Override
    public void InsertMany(Vector<Book> data) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (Book b : data){
            ContentValues cv = new ContentValues();
            cv.put(Book.COLUMN_NAME, b.getName());
            cv.put(Book.COLUMN_AUTHOR, b.getAuthor());
            cv.put(Book.COLUMN_COVER, b.getCover());
            cv.put(Book.COLUMN_SYNOPSIS, b.getSynopsis());
            db.insert(Book.TABLE_NAME, null, cv);
        }
        db.close();
    }

    @Override
    public void Insert(Book data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Book.COLUMN_NAME, data.getName());
        cv.put(Book.COLUMN_AUTHOR, data.getAuthor());
        cv.put(Book.COLUMN_COVER, data.getCover());
        cv.put(Book.COLUMN_SYNOPSIS, data.getSynopsis());
        db.insert(Book.TABLE_NAME, null, cv);
    }

    @SuppressLint("Range")
    @Override
    public Vector<Book> GetAll() {
        Vector<Book> res = new Vector<Book>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query(Book.TABLE_NAME,
                new String[]{ Book.COLUMN_ID, Book.COLUMN_NAME, Book.COLUMN_AUTHOR, Book.COLUMN_COVER, Book.COLUMN_SYNOPSIS },
                null,
                null,
                null,
                null,
                null,
                null);

        while(c.moveToNext()){
            res.add(new Book(
                    c.getInt(c.getColumnIndex(Book.COLUMN_ID)),
                    c.getString(c.getColumnIndex(Book.COLUMN_NAME)),
                    c.getString(c.getColumnIndex(Book.COLUMN_AUTHOR)),
                    c.getString(c.getColumnIndex(Book.COLUMN_COVER)),
                    c.getString(c.getColumnIndex(Book.COLUMN_SYNOPSIS))
            ));
        }
        c.close();
        return res;
    }

    @SuppressLint("Range")
    @Override
    public Book GetByID(Integer ID) {
        Book res = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(Book.TABLE_NAME,
                new String[]{ Book.COLUMN_ID, Book.COLUMN_NAME, Book.COLUMN_AUTHOR, Book.COLUMN_COVER, Book.COLUMN_SYNOPSIS },
                "id = ?",
                new String[] { ID.toString() },
                null,
                null,
                null,
                null);

        if (c.moveToFirst()){
            res = new Book(
                    c.getInt(c.getColumnIndex(Book.COLUMN_ID)),
                    c.getString(c.getColumnIndex(Book.COLUMN_NAME)),
                    c.getString(c.getColumnIndex(Book.COLUMN_AUTHOR)),
                    c.getString(c.getColumnIndex(Book.COLUMN_COVER)),
                    c.getString(c.getColumnIndex(Book.COLUMN_SYNOPSIS))
            );
        }

        c.close();
        return res;
    }

}
