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

            userName = etuserName.getText().toString();
            email = etemail.getText().toString();
            fullName = etfullName.getText().toString();
            phoneNumber = etphoneNumber.getText().toString();


            registerModule = new RegisterModule(this,userName, email, fullName, phoneNumber);

//            queue = registerModule.checkErrors();
//
//            while (!queue.isEmpty())
//            {
//                Toast.makeText(this, queue.remove(), Toast.LENGTH_SHORT).show();
//            }

            registerModule.OnSuccess();
            Toast.makeText(this, "נרשמת בהצלחה", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(RegisterActivity.this, ProfileActivity.class);
            intent.putExtra("key",true);
            startActivity(intent);

        }
        if (btnBackR == view) {
            Intent intent = new Intent(RegisterActivity.this,ProfileActivity.class);
            startActivity(intent);
        }
    }



}