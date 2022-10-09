package com.example.perpusonlinegroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.perpusonlinegroup.databinding.ActivityRequestDetailBinding;
import com.example.perpusonlinegroup.model.Book;
import com.example.perpusonlinegroup.model.Request;
import com.example.perpusonlinegroup.model.User;
import com.example.perpusonlinegroup.service.BookService;
import com.example.perpusonlinegroup.service.RequestService;
import com.example.perpusonlinegroup.service.UserService;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class RequestDetailActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityRequestDetailBinding binding;
    private Button map, submit;
    private TextView name, author, synopsis, requester, receiver, sms;
    private ImageView image;
    private Dialog map_dialog;
    private Integer session;

    private UserService userService;
    private BookService bookService;
    private RequestService requestService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_detail);

        binding = ActivityRequestDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sms = findViewById(R.id.request_detail_sms);
        map = findViewById(R.id.request_detail_map);
        submit = findViewById(R.id.request_detail_submit);

        name = findViewById(R.id.request_detail_book_name);
        author = findViewById(R.id.request_detail_book_author);
        synopsis = findViewById(R.id.request_detail_book_synopsis);
        requester = findViewById(R.id.request_detail_user_requester_email);
        receiver = findViewById(R.id.request_detail_user_receiver_email);

        image = findViewById(R.id.request_detail_book_image);

        userService = new UserService(this);
        bookService = new BookService(this);
        requestService = new RequestService(this);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        session = sp.getInt("SESSION", 1);

        map_dialog = new Dialog(this);
        map_dialog.setTitle("Select Location");
        map_dialog.setContentView(R.layout.map_fragment);
        map_dialog.findViewById(R.id.dialog_map_done).setOnClickListener(v -> map_dialog.dismiss());

        // https://stackoverflow.com/questions/36282719/mapview-inside-dialog
        MapView mv = map_dialog.findViewById(R.id.dialog_map);
        mv.onCreate(map_dialog.onSaveInstanceState());
        mv.onResume();
        mv.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        SetData(mMap);

        sms.setOnClickListener(view -> {
            Intent goToSMS = new Intent(this, SMSActivity.class);
            goToSMS.putExtra("RequestID", getIntent().getIntExtra("RequestID", 1));
            startActivity(goToSMS);
        });

        map.setOnClickListener(view -> {
            map_dialog.show();
        });

        submit.setOnClickListener(view -> {
            Request r = requestService.GetByID(getIntent().getIntExtra("RequestID", 1));
            requestService.AcceptRequest(r.getID(), session);

            Toast.makeText(this, "Book Received", Toast.LENGTH_SHORT).show();
            finish();
        });

    }

    private void SetData(GoogleMap googleMap){
        Request r = requestService.GetByID(getIntent().getIntExtra("RequestID", 1));
        Book b = bookService.GetByID(r.getBookID());
        User req = userService.GetByID(r.getRequesterID());
        User rec = null;
        if (r.getReceiverID() != null || r.getReceiverID() != 0) rec = userService.GetByID(r.getReceiverID());

        name.setText(b.getName());
        author.setText(b.getAuthor());
        synopsis.setText(b.getSynopsis());
        requester.setText(String.format("Requested by : %s", req.getEmail()));
        receiver.setText(String.format("Received from : %s", (rec == null) ? "-" : rec.getEmail()));

        Glide.with(this).load(b.getCoverURL()).into(image);

        googleMap.clear();
        LatLng loc = new LatLng(r.getLatitude(), r.getLongitude());
        googleMap.addMarker(new MarkerOptions().title("Location").position(loc));

        if (session == req.getID()){
            submit.setVisibility(View.INVISIBLE);
            sms.setVisibility(View.INVISIBLE);
        }
        if (rec != null){
            submit.setVisibility(View.INVISIBLE);
            sms.setVisibility(View.INVISIBLE);
            map.setVisibility(View.INVISIBLE);
        }
    }
}