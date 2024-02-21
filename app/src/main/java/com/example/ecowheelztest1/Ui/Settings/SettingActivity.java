package com.example.ecowheelztest1.Ui.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.ecowheelztest1.R;
import com.example.ecowheelztest1.Ui.AboutUs.AboutUs;
import com.example.ecowheelztest1.Ui.Maps.MapsActivity;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView btnbackS;
    private LinearLayout changeHLocLayout, changeWLocLayout, contactUsLayout, aboutUsLayout, logOutLayout;
    private RelativeLayout ChangeHLocMidLayout, ChangeWLocMidLayout;
    private static SwitchCompat swNightMode;
    private SettingsModule settingsModule;
    private NightModeSwitch nightModeSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        swNightMode = findViewById(R.id.swNightMode);
        nightModeSwitch = new NightModeSwitch();
        swNightMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nightModeSwitch.SetNightModSwitch(swNightMode.isChecked());
            }
        });
        swNightMode.setChecked(nightModeSwitch.GetNightModSwitch());

        settingsModule = new SettingsModule(this);

        btnbackS = findViewById(R.id.btnBackS);
        btnbackS.setOnClickListener(this);

        changeHLocLayout = findViewById(R.id.ChangeHLocLayout);
        changeWLocLayout = findViewById(R.id.ChangeWLocLayout);

        ChangeHLocMidLayout = findViewById(R.id.ChangeHLocMidLayout);
        ChangeHLocMidLayout.setOnClickListener(this);

        ChangeWLocMidLayout = findViewById(R.id.ChangeWLocMidLayout);
        ChangeWLocMidLayout.setOnClickListener(this);

        contactUsLayout = findViewById(R.id.ContactUsLayout);
        contactUsLayout.setOnClickListener(this);

        aboutUsLayout = findViewById(R.id.AboutUsLayout);
        aboutUsLayout.setOnClickListener(this);

        logOutLayout = findViewById(R.id.LogOutLayout);
        logOutLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v == btnbackS)
        {
            Intent intent = new Intent(SettingActivity.this, MapsActivity.class);
            btnbackS.setClickable(false);
            startActivity(intent);
        }
        if (v == ChangeHLocMidLayout)
        {
            if (settingsModule.getIsLoggedIn())
            {
                LinearLayout linearLayout = settingsModule.ChangeHomeLocationNewLayout(changeHLocLayout,ChangeHLocMidLayout);
                changeHLocLayout.removeView(ChangeHLocMidLayout);
                changeHLocLayout.addView(linearLayout);
            }
        }
        if (v == ChangeWLocMidLayout)
        {
            if (settingsModule.getIsLoggedIn())
            {
                LinearLayout linearLayout = settingsModule.ChangeWorkLocationNewLayout(changeWLocLayout,ChangeWLocMidLayout);
                changeWLocLayout.removeView(ChangeWLocMidLayout);
                changeWLocLayout.addView(linearLayout);
            }
        }
        if (v == contactUsLayout)
        {
            settingsModule.CreateContactUsDialog();
        }
        if (v == aboutUsLayout)
        {
            Intent intent = new Intent(SettingActivity.this, AboutUs.class);
            aboutUsLayout.setClickable(false);
            startActivity(intent);
        }
        if (v == logOutLayout)
        {
            settingsModule.CreateLogOutDialog();
        }
    }
}