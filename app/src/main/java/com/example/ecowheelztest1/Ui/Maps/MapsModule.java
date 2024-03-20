package com.example.ecowheelztest1.Ui.Maps;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

import com.example.ecowheelztest1.Repository.Repository;

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

    public String getHomeLocation(){return repository.getSharedPreferences().getHomeLocation();}
    public String getWorkLocation(){return repository.getSharedPreferences().getWorkLocation();}


    public Address getLocation(String str) {

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
