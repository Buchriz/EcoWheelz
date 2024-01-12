package com.example.ecowheelztest1;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout pA;
    private ImageView btnbackP;
    private TextView tvName;
    private Button regi, btnChange;
    private String userName, email, fullName, phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile);

        btnbackP = findViewById(R.id.btnBackP);
        btnbackP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });

        pA = findViewById(R.id.profileActivity);
        tvName = findViewById(R.id.tvName);

        Intent intent = getIntent();

        userName = intent.getStringExtra("username");
        email = intent.getStringExtra("email");
        fullName = intent.getStringExtra("fullname");
        phoneNumber = intent.getStringExtra("phonenumber");


        if (userName == null)
        {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(-1,170);
            l.setMargins(0,100,0,0);
            linearLayout.setLayoutParams(l);

            regi = new Button(this);
            
            LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(-2,130);
            ll.gravity = Gravity.CENTER;
            regi.setLayoutParams(ll);

            regi.setGravity(Gravity.CENTER);
            regi.setText("הרשם");
            regi.setTypeface(null, Typeface.BOLD);
            regi.setTextSize(18);
            regi.setTextColor(getResources().getColor(R.color.primary));
            regi.setBackground(getDrawable(R.drawable.register_btn_background));
            
            regi.setOnClickListener(this);
            linearLayout.addView(regi);
            pA.addView(linearLayout);
        }
        else
        {
            /////////////////////////////////////////////////////////////////////////
            //                   השמה של הפרטים האישיים
            ////////////////////////////////////////////////////////////////////////
            tvName.setText(userName);

            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, 0, 1)); // Weighted height
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setGravity(Gravity.CENTER);

            TextView tvfullname = new TextView(this);
            tvfullname.setTextSize(20);
            tvfullname.setTextColor(Color.WHITE);
            tvfullname.setTypeface(null, Typeface.BOLD);
            tvfullname.setGravity(Gravity.CENTER);
            tvfullname.setBackground(getDrawable(R.drawable.round_background_settings));
            tvfullname.setText("Full Name: " + fullName);
//            tvfullname.setBackgroundColor(Color.LTGRAY);


            TextView tvemail = new TextView(this);
            tvemail.setTextSize(20);
            tvemail.setTextColor(Color.WHITE);
            tvemail.setTypeface(null, Typeface.BOLD);
            tvemail.setGravity(Gravity.CENTER);
            tvemail.setBackground(getDrawable(R.drawable.round_background_settings));
            tvemail.setText("Email: " + email);
//            tvemail.setBackgroundColor(Color.LTGRAY);


            TextView tvphonenumber = new TextView(this);
            tvphonenumber.setTextSize(20);
            tvphonenumber.setTextColor(Color.WHITE);
            tvphonenumber.setTypeface(null, Typeface.BOLD);
            tvphonenumber.setGravity(Gravity.CENTER);
            tvphonenumber.setBackground(getDrawable(R.drawable.round_background_settings));
            tvphonenumber.setText("Phone Number: " + phoneNumber);
//            tvphonenumber.setBackgroundColor(Color.LTGRAY);


            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(-1,200);
            textParams.setMargins(5,30,5,0);
            tvfullname.setLayoutParams(textParams);
            tvemail.setLayoutParams(textParams);
            tvphonenumber.setLayoutParams(textParams);


            linearLayout.addView(tvfullname);
            linearLayout.addView(tvemail);
            linearLayout.addView(tvphonenumber);

            pA.addView(linearLayout);


            /////////////////////////////////////////////////////////////////////////
            //                  השמה של הכפתור שינוי פרטים אישיים
            ////////////////////////////////////////////////////////////////////////

            RelativeLayout relativeLayout = new RelativeLayout(this);
            relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(-1,-2));

            btnChange = new Button(this);
            btnChange.setLayoutParams(new RelativeLayout.LayoutParams(170, -2));
            btnChange.setText("שנה פרטים אישיים");
            btnChange.setTextColor(Color.WHITE);
            btnChange.setTextSize(16);
            btnChange.setTypeface(null, Typeface.BOLD);


            btnChange.setBackground(getDrawable(R.drawable.btn_backround));

            // Set the position of the Button in the RelativeLayout
            RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(400,-2);
            buttonParams.addRule(RelativeLayout.ALIGN_PARENT_END);
            buttonParams.setMargins(0, 0, 10, 10);
            btnChange.setLayoutParams(buttonParams);

            relativeLayout.addView(btnChange);

            pA.addView(relativeLayout);
        }
    }

    @Override
    public void onClick(View v) 
    {
        if (regi == v)
        {
            Intent intent = new Intent(ProfileActivity.this,RegisterActivity.class);
            startActivity(intent);
        }
        if (btnChange == v)
        {

        }
    }
}