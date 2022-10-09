package com.example.perpusonlinegroup;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.perpusonlinegroup.model.Book;
import com.example.perpusonlinegroup.model.Request;
import com.example.perpusonlinegroup.model.User;
import com.example.perpusonlinegroup.service.BookService;
import com.example.perpusonlinegroup.service.DatabaseService;
import com.example.perpusonlinegroup.service.RequestService;
import com.example.perpusonlinegroup.service.UserService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Vector;

public class LoginActivity extends AppCompatActivity {

    private EditText mViewUser, mViewPassword;
    private Button btnLogin;
    private TextView btnRegister;
    private void startApp(){
        BookService bs = new BookService(this);
        UserService us = new UserService(this);
        RequestService rs = new RequestService(this);
        AskForPermission();


        if (bs.GetAll().size() == 0 && !DatabaseService.IS_MIGRATED){
            RequestQueue rq = Volley.newRequestQueue(this);
            JsonArrayRequest arrayRequest = new JsonArrayRequest("https://isys6203-perpus-online.herokuapp.com/",
                response ->  {
                    Vector<Book> books = new Vector<>();
                    for (int i = 0; i < response.length(); i++){
                        try {
                            JSONObject data = response.getJSONObject(i);
                            books.add(new Book(
                                0,
                                data.getString("name"),
                                data.getString("author"),
                                data.getString("cover"),
                                data.getString("synopsis")
                            ));

                        } catch (JSONException e) {
                           e.printStackTrace();
                        }
                    }
                    bs.InsertMany(books);

                    us.Insert(new User(0, "admin@mail.com", "admin123", "+621234567890", Calendar.getInstance()));
                    us.Insert(new User(0, "ad@mail.com", "ad123", "+621234567890", Calendar.getInstance()));

                    rs.Insert(new Request(0, 2, 1, 2, -62.2, 62.2));

                }, Throwable::printStackTrace
            );

            rq.add(arrayRequest);

            DatabaseService.IS_MIGRATED = true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        startApp();
        Login();

    }

    void Login(){

        mViewUser = findViewById(R.id.et_emailSignin);
        mViewPassword = findViewById(R.id.et_passwordSignin);
        btnLogin = findViewById(R.id.button_signinSignin);
        btnRegister = findViewById(R.id.tx_register);
        UserService userService = new UserService(this);
        btnLogin.setOnClickListener(view -> {
            String user = mViewUser.getText().toString();
            String password = mViewPassword.getText().toString();
            int id = userService.checkUser(user, password);
            if (id > 0){
                SharedPreferences sp =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                sp.edit().putInt("SESSION", id).commit();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(LoginActivity.this, "Invalid Email / Password", Toast.LENGTH_SHORT).show();
            }
        });

        btnRegister.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
            finish();
        });

    }

    private void AskForPermission(){
//        Vector<String> needed = new Vector<>();
//
//        needed.add(Manifest.permission.SEND_SMS);
//        needed.add(Manifest.permission.RECEIVE_SMS);
//        needed.add(Manifest.permission.READ_SMS);
//        needed.add(Manifest.permission.ACCESS_FINE_LOCATION);
//
//        for(String p : needed){
//            int permission = ContextCompat.checkSelfPermission(this, p);
//            if (permission != PackageManager.PERMISSION_GRANTED){
//                Log.i("aaa", "bbb");
//                ActivityCompat.requestPermissions(this, new String[]{ p }, 1);
//            }
//        }
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.SEND_SMS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_SMS
        }, 1);
    }
}


//    //startApp();
//    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
//    startActivity(intent);}
