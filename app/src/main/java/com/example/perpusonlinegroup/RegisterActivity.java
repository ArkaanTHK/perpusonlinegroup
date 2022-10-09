package com.example.perpusonlinegroup;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.perpusonlinegroup.model.User;
import com.example.perpusonlinegroup.service.UserService;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText EmailReg, PasswordReg, PhoneNumReg;
    private TextView DOBReg, RedirectLogin;
    private Button RegBtn;
    private CheckBox CheckBoxReg;
    Calendar DOBDate, currYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        CreateNewUser();
    }

    void CreateNewUser()
    {
        EmailReg = findViewById(R.id.register_email);
        PasswordReg = findViewById(R.id.register_password);
        PhoneNumReg = findViewById(R.id.register_phonenum);
        DOBReg = findViewById(R.id.register_dob);
        DOBDate = Calendar.getInstance();
        currYear = Calendar.getInstance();
        CheckBoxReg = findViewById(R.id.terms_and_condition);
        RegBtn = findViewById(R.id.register_btn);
        RedirectLogin = findViewById(R.id.login_redirect);

        DOBReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                summonDatePicker();
            }
        });

        RegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String NewEmail = EmailReg.getText().toString();
                String NewPassword = PasswordReg.getText().toString();
                String NewPhoneNum = PhoneNumReg.getText().toString();
                Boolean isMoreThan13 = checkAge();
                Boolean checkEmailValid = checkEmail(NewEmail), checkPhoneNumValid = checkPhoneNum(NewPhoneNum);
                checkPassword(NewPassword);

                if(NewEmail.isEmpty())
                {
                    Toast.makeText(RegisterActivity.this, "Email couldn't be empty", Toast.LENGTH_SHORT).show();
                }
                else if(!checkEmailValid)
                {
                    Toast.makeText(RegisterActivity.this, "Email is not valid", Toast.LENGTH_SHORT).show();
                }
                else if(NewPassword.isEmpty())
                {
                    Toast.makeText(RegisterActivity.this, "Password couldn't be empty", Toast.LENGTH_SHORT).show();
                }
                else if(NewPassword.length() <= 8)
                {
                    Toast.makeText(RegisterActivity.this, "Password is too short", Toast.LENGTH_SHORT).show();
                }
                else if(!isPassIncludeChar || !isPassIncludeNum)
                {
                    Toast.makeText(RegisterActivity.this, "Password should include characters and numbers", Toast.LENGTH_SHORT).show();
                }
                else if(NewPhoneNum.isEmpty())
                {
                    Toast.makeText(RegisterActivity.this, "Phone number shouldn't be empty", Toast.LENGTH_SHORT).show();
                }
                else if(!checkPhoneNumValid)
                {
                    Toast.makeText(RegisterActivity.this, "Phone number is not valid", Toast.LENGTH_SHORT).show();
                }
                else if(!isMoreThan13)
                {
                    Toast.makeText(RegisterActivity.this, "Not eligible to register", Toast.LENGTH_SHORT).show();
                }
                else if(!CheckBoxReg.isChecked())
                {
                    Toast.makeText(RegisterActivity.this, "Please agree to our Terms and Condition to proceed", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();
                    UserService newUser = new UserService(RegisterActivity.this);
                    newUser.Insert(new User(0,NewEmail,NewPassword,NewPhoneNum,DOBDate));
                    Intent LoginActivityRedirect = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(LoginActivityRedirect);
                }
            }
        });
        RedirectLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void summonDatePicker()
    {
        DatePickerDialog DOBDatePicker = new DatePickerDialog(RegisterActivity.this, this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DATE));
        DOBDatePicker.show();
    }



    @Override
    public void onDateSet(DatePicker datePicker, int DOBYear, int DOBMonth, int DOBDay)
    {
        DOBDate.set(DOBYear,DOBMonth,DOBDay);
    }


    public Boolean checkAge()
    {
        if(currYear.get(Calendar.YEAR) - DOBDate.get(Calendar.YEAR) < 13)
        {
            return false;
        }
        else return true;
    }

    public Boolean checkEmail(String NewEmail)
    {
        Pattern emailPattern = Pattern.compile("[a-z0-9]+@[a-z0-9]+\\.com",Pattern.CASE_INSENSITIVE);
        Matcher MatchEmail = emailPattern.matcher(NewEmail);
        return MatchEmail.find();
    }

    Boolean isPassIncludeNum = false, isPassIncludeChar = false;

    public void checkPassword(String NewPassword)
    {
        for(char p: NewPassword.toCharArray())
        {
            isPassIncludeNum = Character.isDigit(p);
            if(isPassIncludeNum == true)
            {
                break;
            }

        }

        for(char p: NewPassword.toCharArray())
        {
            isPassIncludeChar = Character.isLetter(p);
            if(isPassIncludeChar == true)
            {
                break;
            }

        }


    }

    public Boolean checkPhoneNum( String NewPhoneNum)
    {
        if(NewPhoneNum.startsWith("+62") && (NewPhoneNum.length()>= 10 || NewPhoneNum.length()<= 15))
        {
            return true;
        }
        return false;
    }




}