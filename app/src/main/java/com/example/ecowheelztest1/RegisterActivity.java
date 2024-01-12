package com.example.ecowheelztest1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView btnBackR;
    private EditText etuserName, etemail, etfullName, etphoneNumber;
    private String userName, email, fullName, phoneNumber;
    private Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);

        btnBackR = findViewById(R.id.btnBackR);
        btnBackR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });


        etuserName = findViewById(R.id.userName);
        etemail = findViewById(R.id.email);
        etfullName = findViewById(R.id.fullName);
        etphoneNumber = findViewById(R.id.phoneNumber);
        btnCreate = findViewById(R.id.create);
        btnCreate.setOnClickListener(this);



    }

    @Override
    public void onClick(View view)
    {
        if (btnCreate == view)
        {
            if (checkErrors() == true)
            {
                Toast.makeText(this, "נרשמת בהצלחה", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(RegisterActivity.this, ProfileActivity.class);
                intent.putExtra("username", userName);
                intent.putExtra("email", email);
                intent.putExtra("fullname", fullName);
                intent.putExtra("phonenumber", phoneNumber);
                startActivity(intent);

            }
        }
    }

    public boolean checkErrors()
    {
        userName = etuserName.getText().toString();
        email = etemail.getText().toString();
        fullName = etfullName.getText().toString();
        phoneNumber = etphoneNumber.getText().toString();

        if (userName.length()==0)
        {
            etuserName.setError("Enter UserName");
            return false;
        }

        if (email.length() == 0)
        {
            etemail.setError("Enter Email");
            return false;
        }

        if (fullName.length() == 0)
        {
            etfullName.setError("Enter Full Name");
            return false;
        }

        if (phoneNumber.length() == 0)
        {
            etphoneNumber.setError("Enter Phone");
            return false;
        }

        return true;
    }

}