package com.example.ecowheelztest1.Ui.Maps;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecowheelztest1.R;
import com.example.ecowheelztest1.Repository.Repository;
import com.example.ecowheelztest1.Ui.Settings.NightModeSwitch;
import com.example.ecowheelztest1.Ui.Settings.SettingActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsModule {

    private Repository repository;
    private Context context;

    public MapsModule(Context c){
        context = c;
        repository = new Repository(c);
    }

    public boolean getIsLoggedIn()
    {
        return this.repository.getIsLoggedIn();
    }


    public RelativeLayout Saved_places(GoogleMap map){
        RelativeLayout relativeLayout = new RelativeLayout(context);

        RelativeLayout.LayoutParams RlayoutParams = new RelativeLayout.LayoutParams(640,100);
        RlayoutParams.addRule(RelativeLayout.BELOW, R.id.searchView); // Set the linear layout below the search view
        RlayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        RlayoutParams.setMargins(65,-1,0,0);
        relativeLayout.setLayoutParams(RlayoutParams);
        relativeLayout.setBackgroundColor(Color.WHITE);


        TextView tvHome = new TextView(context);
        RelativeLayout.LayoutParams tvHParams = new RelativeLayout.LayoutParams(320,-1);
        tvHParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        tvHome.setLayoutParams(tvHParams);
        tvHome.setGravity(Gravity.CENTER);
        tvHome.setText("בית");
        tvHome.setTextSize(16);
        tvHome.setTypeface(null, Typeface.BOLD);

        tvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String str = repository.getSharedPreferences().getHomeLocation();
                if (str == null){
                    Intent intent = new Intent(context, SettingActivity.class);
                    tvHome.setClickable(false);
                    context.startActivity(intent);
                    Toast.makeText(context, "הכנס מיקום בית", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Toast.makeText(context,repository.getSharedPreferences().getHomeLocation(), Toast.LENGTH_SHORT).show();
                    LatLng homeLatLng = new LatLng(getLocation(str).getLatitude(), getLocation(str).getLongitude());
                    map.addMarker(new MarkerOptions().position(homeLatLng));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, 16.7f), 1000, null);
                }
            }
        });

        TextView tvWork = new TextView(context);
        RelativeLayout.LayoutParams tvWParams = new RelativeLayout.LayoutParams(320,-1);
        tvWParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        tvWork.setLayoutParams(tvWParams);
        tvWork.setGravity(Gravity.CENTER);
        tvWork.setText("עבודה");
        tvWork.setTextSize(16);
        tvWork.setTypeface(null, Typeface.BOLD);

        tvWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String str = repository.getSharedPreferences().getWorkLocation();
                if (str == null){
                    Intent intent = new Intent(context, SettingActivity.class);
                    tvWork.setClickable(false);
                    context.startActivity(intent);
                    Toast.makeText(context, "הכנס מיקום עבודה", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Toast.makeText(context, repository.getSharedPreferences().getWorkLocation(), Toast.LENGTH_SHORT).show();
                    LatLng workLatLng = new LatLng(getLocation(str).getLatitude(), getLocation(str).getLongitude());
                    map.addMarker(new MarkerOptions().position(workLatLng));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(workLatLng, 16.7f), 1000, null);
                }
            }
        });


        NightModeSwitch nightModeSwitch = new NightModeSwitch();
        if (nightModeSwitch.GetNightModSwitch())
        {
            relativeLayout.setBackground(context.getDrawable(R.drawable.home_work_background_dark_mode));
            tvHome.setTextColor(Color.WHITE);
            tvWork.setTextColor(Color.WHITE);
        }
        else
        {
            relativeLayout.setBackground(context.getDrawable(R.drawable.home_work_background));
            tvHome.setTextColor(Color.BLACK);
            tvWork.setTextColor(Color.BLACK);
        }

        relativeLayout.addView(tvHome);
        relativeLayout.addView(tvWork);

        return relativeLayout;
    }

    private Address getLocation(String str) {

        Address address = null;
        Geocoder geocoder = new Geocoder(context);

        try {
            List<Address> addresses = geocoder.getFromLocationName(str, 1);
            if (addresses != null && !addresses.isEmpty()) {
                address = addresses.get(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error finding location. Please try again.", Toast.LENGTH_SHORT).show();
        }

        return address;
    }

}
