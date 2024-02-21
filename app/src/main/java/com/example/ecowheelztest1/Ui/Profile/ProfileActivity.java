package com.example.ecowheelztest1.Ui.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecowheelztest1.R;
import com.example.ecowheelztest1.Repository.User;
import com.example.ecowheelztest1.Ui.LogIn.LogInActivity;
import com.example.ecowheelztest1.Ui.Maps.MapsActivity;
import com.example.ecowheelztest1.Ui.Register.RegisterActivity;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout pA, regiButtonLayout,profilepicLayout;
    private RelativeLayout header_titleLayout;
    private ImageView btnbackP;
    private TextView tvName, alreadyRegistered;
    private Button register;
    private String userName, email, fullName,phoneNumber ;
    private ProfileModule profileModule;
    private static User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        pA = findViewById(R.id.profileActivity);
        header_titleLayout = findViewById(R.id.headertitle);
        profilepicLayout = findViewById(R.id.profilepic);
        regiButtonLayout = findViewById(R.id.regiButtonLayout);

        btnbackP = findViewById(R.id.btnBackP);
        btnbackP.setOnClickListener(this);
        register = findViewById(R.id.register);
        register.setOnClickListener(this);
        tvName = findViewById(R.id.tvName);
        alreadyRegistered = findViewById(R.id.alreadyRegistered);
        alreadyRegistered.setOnClickListener(this);
        

        profileModule = new ProfileModule(this);
        boolean isLoggedIn = profileModule.getIsLoggedIn();

        if (isLoggedIn)
        {
            user = profileModule.getUserLogIn();
            userName = user.getUserName();
            email = user.getEmail();
            fullName = user.getFullName();
            phoneNumber = user.getPhoneNumber();
            profileModule.NewLayout(pA,regiButtonLayout,tvName,userName,fullName,email,phoneNumber);
            //btnChange.setOnClickListener(this);
        }
        else
        {
            pA.removeAllViews();
            pA.addView(header_titleLayout);
            pA.addView(profilepicLayout);
            pA.addView(regiButtonLayout);
        }
    }
    


    @Override
    public void onClick(View v) 
    {
        if (btnbackP == v)
        {
            Intent intent = new Intent(ProfileActivity.this, MapsActivity.class);
            btnbackP.setClickable(false);
            startActivity(intent);
        }
        if (register == v)
        {
            Intent intent = new Intent(ProfileActivity.this, RegisterActivity.class);
            register.setClickable(false);
            startActivity(intent);
        }
        if (alreadyRegistered == v)
        {
            Intent intent = new Intent(ProfileActivity.this, LogInActivity.class);
            alreadyRegistered.setClickable(false);
            startActivity(intent);
        }
//        if (btnChange == v)
//        {
//            profileModule.updateDataDialog();
//        }
    }
}