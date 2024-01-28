package com.example.ecowheelztest1.Ui.Profile;

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
import android.widget.Toast;

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
    private Button register, btnChange;
    private String userName, email, fullName,phoneNumber ;
    private ProfileModule profileModule;
    private User user;


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
        boolean isRegistered = profileModule.getIsRegistered();
        boolean isLoggedIn = profileModule.getIsLoggedIn();
        
        if (isRegistered || isLoggedIn)
        {
            if (isRegistered)
            {
                user = profileModule.getUserRegister();
                userName = user.getUserName();
                email = user.getEmail();
                fullName = user.getFullName();
                phoneNumber = user.getPhoneNumber();
                NewLayout();
            }
            else if (isLoggedIn)
            {
                user = profileModule.getUserLogIn();
                userName = user.getUserName();
                email = user.getEmail();
                fullName = user.getFullName();
                phoneNumber = user.getPhoneNumber();
                NewLayout();
            }
        }
        else
        {
            pA.removeAllViews();
            pA.addView(header_titleLayout);
            pA.addView(profilepicLayout);
            pA.addView(regiButtonLayout);
        }
    }
    
    public void NewLayout() {
        /////////////////////////////////////////////////////////////////////////
        //                   השמה של הפרטים האישיים
        ////////////////////////////////////////////////////////////////////////
        pA.removeView(regiButtonLayout);
        tvName.setText(userName);

        LinearLayout infoLinearLayout = new LinearLayout(this);
        infoLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, 0, 1)); // Weighted height
        infoLinearLayout.setOrientation(LinearLayout.VERTICAL);
        infoLinearLayout.setGravity(Gravity.CENTER);

        TextView tvfullname = new TextView(this);
        tvfullname.setTextSize(20);
        tvfullname.setTextColor(Color.WHITE);
        tvfullname.setTypeface(null, Typeface.BOLD);
        tvfullname.setGravity(Gravity.CENTER);
        tvfullname.setBackground(getDrawable(R.drawable.round_background_settings));
        tvfullname.setText("Full Name: " + fullName);


        TextView tvemail = new TextView(this);
        tvemail.setTextSize(20);
        tvemail.setTextColor(Color.WHITE);
        tvemail.setTypeface(null, Typeface.BOLD);
        tvemail.setGravity(Gravity.CENTER);
        tvemail.setBackground(getDrawable(R.drawable.round_background_settings));
        tvemail.setText("Email: " + email);


        TextView tvphonenumber = new TextView(this);
        tvphonenumber.setTextSize(20);
        tvphonenumber.setTextColor(Color.WHITE);
        tvphonenumber.setTypeface(null, Typeface.BOLD);
        tvphonenumber.setGravity(Gravity.CENTER);
        tvphonenumber.setBackground(getDrawable(R.drawable.round_background_settings));
        tvphonenumber.setText("Phone Number: " + phoneNumber);


        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(-1,200);
        textParams.setMargins(5,30,5,0);
        tvfullname.setLayoutParams(textParams);
        tvemail.setLayoutParams(textParams);
        tvphonenumber.setLayoutParams(textParams);


        infoLinearLayout.addView(tvfullname);
        infoLinearLayout.addView(tvemail);
        infoLinearLayout.addView(tvphonenumber);

        pA.addView(infoLinearLayout);


        /////////////////////////////////////////////////////////////////////////
        //                  השמה של הכפתור שינוי פרטים אישיים
        ////////////////////////////////////////////////////////////////////////

        RelativeLayout infoRelativeLayout = new RelativeLayout(this);
        infoRelativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(-1,-2));

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
        btnChange.setOnClickListener(this);

        infoRelativeLayout.addView(btnChange);

        pA.addView(infoRelativeLayout);
    }
    

    @Override
    public void onClick(View v) 
    {
        if (btnbackP == v)
        {
            Intent intent = new Intent(ProfileActivity.this, MapsActivity.class);
            startActivity(intent);
        }
        if (register == v)
        {
            Intent intent = new Intent(ProfileActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
        if (alreadyRegistered == v)
        {
            Intent intent = new Intent(ProfileActivity.this, LogInActivity.class);
            startActivity(intent);
        }
        if (btnChange == v)
        {
            Toast.makeText(this, "לפתוח דף חדש/דיאלוג לעדכון פרטים", Toast.LENGTH_SHORT).show();
        }
    }
}