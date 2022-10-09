package com.example.perpusonlinegroup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.perpusonlinegroup.model.Request;
import com.example.perpusonlinegroup.model.User;
import com.example.perpusonlinegroup.service.RequestService;
import com.example.perpusonlinegroup.service.UserService;

public class SMSActivity extends AppCompatActivity {

    TextView destination;
    EditText text;
    Button submit;

    UserService userService;
    RequestService requestService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        destination = findViewById(R.id.sms_destination);
        text = findViewById(R.id.sms_text);
        submit = findViewById(R.id.sms_submit);

        userService = new UserService(this);
        requestService = new RequestService(this);

        Request r = requestService.GetByID(getIntent().getIntExtra("RequestID", 1));
        User req = userService.GetByID(r.getRequesterID());

        destination.setText(String.format("Destination : %s", req.getPhoneNumber()));
        submit.setOnClickListener(view -> {
            String msg = text.getText().toString();

            if (msg.length() == 0) Toast.makeText(this, "Must not be empty", Toast.LENGTH_SHORT).show();
            else{
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(
                        req.getPhoneNumber(),
                        null,
                        msg,
                        null,
                        null
                );
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                text.setText("");
            }
        });
    }
}