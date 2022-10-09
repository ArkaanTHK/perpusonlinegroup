package com.example.perpusonlinegroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.perpusonlinegroup.model.Book;
import com.example.perpusonlinegroup.model.Request;
import com.example.perpusonlinegroup.service.BookService;
import com.example.perpusonlinegroup.service.RequestService;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.perpusonlinegroup.databinding.ActivityBookDetailBinding;

public class BookDetailActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityBookDetailBinding binding;
    private TextView title, author, synopsis;
    private ImageView image;
    private Book current_book;
    private BookService bookService;
    private RequestService requestService;
    private Button submit, map;
    private LatLng current_location;
    private Dialog map_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBookDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        bookService = new BookService(this);
        title = findViewById(R.id.book_detail_title);
        author = findViewById(R.id.book_detail_author);
        synopsis = findViewById(R.id.book_detail_synopsis);
        image = findViewById(R.id.book_detail_image);
        current_book = bookService.GetByID(getIntent().getIntExtra("BookID", 1));
        requestService = new RequestService(this);

        title.setText(current_book.getName());
        author.setText(current_book.getAuthor());
        synopsis.setText(current_book.getSynopsis());
        Glide.with(this).load(current_book.getCoverURL()).into(image);

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

        mMap.setOnMapClickListener(coordinate -> {
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(coordinate).title("Selected Location"));
            current_location = coordinate;
        });

        submit = findViewById(R.id.book_detail_submit);
        submit.setOnClickListener(v -> {
            if (current_location == null) Toast.makeText(this, "Please select a location first", Toast.LENGTH_SHORT).show();
            else{
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Integer request_id = sp.getInt("SESSION", 1);
                requestService.Insert(new Request(0, current_book.getID(), request_id, null, current_location.latitude, current_location.longitude));

                Toast.makeText(this, "Book Requested", Toast.LENGTH_SHORT).show();
            }
        });

        map = findViewById(R.id.book_detail_map);
        map.setOnClickListener(v -> map_dialog.show());
    }
}