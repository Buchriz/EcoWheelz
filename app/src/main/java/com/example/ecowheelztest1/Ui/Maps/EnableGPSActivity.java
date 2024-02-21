package com.example.ecowheelztest1.Ui.Maps;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecowheelztest1.R;

public class EnableGPSActivity extends AppCompatActivity {
private Button enable_gps_continue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enable_gpsactivity);

        enable_gps_continue = findViewById(R.id.enable_gps_continue);

        enable_gps_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        
        if (isGPSEnabled())
        {
            Intent intent = new Intent(EnableGPSActivity.this,MapsActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "הפעל מיקום", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}