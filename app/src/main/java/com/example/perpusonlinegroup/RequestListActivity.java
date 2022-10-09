package com.example.perpusonlinegroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.example.perpusonlinegroup.adapter.RequestBookAdapter;
import com.example.perpusonlinegroup.model.Request;
import com.example.perpusonlinegroup.service.RequestService;

import java.util.ArrayList;
import java.util.Vector;

public class RequestListActivity extends AppCompatActivity {

    RecyclerView request_list;
    RequestService requestService;
    RequestBookAdapter requestBookAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestService = new RequestService(this);
        request_list = findViewById(R.id.main_request_list);
        Vector<Request> t = requestService.GetAll();

        requestBookAdapter = new RequestBookAdapter(this, t);

        request_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        request_list.setAdapter(requestBookAdapter);
    }
}