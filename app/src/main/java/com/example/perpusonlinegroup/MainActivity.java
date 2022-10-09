package com.example.perpusonlinegroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.perpusonlinegroup.adapter.MainBookAdapter;
import com.example.perpusonlinegroup.model.Book;
import com.example.perpusonlinegroup.service.BookService;

import java.util.Vector;

public class MainActivity extends AppCompatActivity{

    RecyclerView book_list;
    MainBookAdapter book_list_adapter;
    BookService bookService;

    private void Init(){
        bookService = new BookService(this);
        book_list = findViewById(R.id.main_book_list);
        Vector<Book> t = bookService.GetAll();

        book_list_adapter = new MainBookAdapter(this, t);

        book_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        book_list.setAdapter(book_list_adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.main_menu_request){
            Intent goToRequestList = new Intent(this, RequestListActivity.class);
            startActivity(goToRequestList);
        }
        else if (item.getItemId() == R.id.main_menu_logout){
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
            sp.edit().remove("SESSION").commit();

            Intent goToLogin = new Intent(this, LoginActivity.class);
            startActivity(goToLogin);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();
    }
}