package com.example.ecowheelztest1.Ui.AboutUs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecowheelztest1.R;
import com.example.ecowheelztest1.Ui.Settings.SettingActivity;

public class AboutUs extends AppCompatActivity {

    private ImageView btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        btn = findViewById(R.id.btnBackAU);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutUs.this, SettingActivity.class);
                btn.setClickable(false);
                startActivity(intent);
            }
        });
    }
}