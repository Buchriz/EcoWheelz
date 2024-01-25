package com.example.ecowheelztest1.Ui.Settings;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.ecowheelztest1.R;
import com.example.ecowheelztest1.Ui.AboutUs.AboutUs;
import com.example.ecowheelztest1.Ui.Maps.MapsActivity;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView btnbackS;
    private LinearLayout changeHLocLayout, contactUsLayout, aboutUsLayout, logOutLayout;
    private static SwitchCompat swNightMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        swNightMode = findViewById(R.id.swNightMode);


        btnbackS = findViewById(R.id.btnBackS);
        btnbackS.setOnClickListener(this);
        changeHLocLayout = findViewById(R.id.ChangeHLocLayout);
        changeHLocLayout.setOnClickListener(this);
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
            startActivity(intent);
        }
        if (v == changeHLocLayout)
        {
            Toast.makeText(this, "לא עובד בינתיים", Toast.LENGTH_SHORT).show();
        }
        if (v == contactUsLayout)
        {
            CreateContactUsDialog();
        }
        if (v == aboutUsLayout)
        {
            Intent intent = new Intent(SettingActivity.this, AboutUs.class);
            startActivity(intent);
        }
        if (v == logOutLayout)
        {
            CreateLogOutDialog();
        }
    }

    public void CreateLogOutDialog()
    {
        Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.log_out_dialog);
        Button yes = dialog.findViewById(R.id.btnYes);
        Button no  = dialog.findViewById(R.id.btnNo);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(SettingActivity.this, "צריך לנתק משתמש מהאפליקציה", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        no.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

    public void CreateContactUsDialog()
    {
        Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.contact_us_dialog);
        ImageView close = dialog.findViewById(R.id.close);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(lp);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}