package com.example.ecowheelztest1.Ui.Register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecowheelztest1.R;
import com.example.ecowheelztest1.Ui.LogIn.LogInActivity;
import com.example.ecowheelztest1.Ui.Profile.ProfileActivity;

import java.util.Queue;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView btnBackR;
    private EditText etuserName, etemail, etfullName, etphoneNumber;
    private String userName,email, fullName, phoneNumber;
    private RegisterModule registerModule;
    private Button btnCreate;
    private Queue<String> queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnBackR = findViewById(R.id.btnBackR);
        btnBackR.setOnClickListener(this);

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
        if (btnCreate == view) {

            userName = etuserName.getText().toString().trim();
            email = etemail.getText().toString().trim();
            fullName = etfullName.getText().toString().trim();
            phoneNumber = etphoneNumber.getText().toString().trim();

            registerModule = new RegisterModule(this,userName, email, fullName, phoneNumber);
//            queue = registerModule.checkErrors();
//
//            while (!queue.isEmpty())
//            {
//                Toast.makeText(this, queue.remove(), Toast.LENGTH_SHORT).show();
//            }
            if(registerModule.OnSuccess()){
                Intent intent = new Intent(RegisterActivity.this, LogInActivity.class);
                btnCreate.setClickable(false);
                startActivity(intent);
            }else {
                Toast.makeText(this, "מספר טלפון זה כבר קיים, אנא הכנס מספר אחר", Toast.LENGTH_SHORT).show();
            }

            
        }
        if (btnBackR == view) {
            Intent intent = new Intent(RegisterActivity.this,ProfileActivity.class);
            btnBackR.setClickable(false);
            startActivity(intent);
        }
    }



}