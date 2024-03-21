package com.example.ecowheelz.Ui.Maps;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

import com.example.ecowheelz.R;
import com.example.ecowheelz.Repository.Repository;

import java.io.IOException;
import java.util.List;

public class MapsModule {

    private Repository repository;
    private Context context;

    public MapsModule(Context c){
        context = c;
        repository = new Repository(c);
    }


    ////////////////////////////////////////////
    //         Get User Logged-In
    ////////////////////////////////////////////
    public boolean getIsLoggedIn()
    {
        return this.repository.getIsLoggedIn();
    }



    ////////////////////////////////////////////
    //         Get Home/Work Names
    ////////////////////////////////////////////
    public String getHomeLocation(){return repository.getSharedPreferences().getHomeLocation();}
    public String getWorkLocation(){return repository.getSharedPreferences().getWorkLocation();}



    ////////////////////////////////////////////
    //         Get Home/Work Address
    ////////////////////////////////////////////
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
            Toast.makeText(context, context.getString(R.string.Error_Finding_Location), Toast.LENGTH_SHORT).show();
        }

        return address;
    }

}
