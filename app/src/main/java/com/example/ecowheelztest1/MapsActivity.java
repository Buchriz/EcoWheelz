package com.example.ecowheelztest1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import com.example.ecowheelztest1.databinding.ActivityMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private GoogleMap map;
    private ActivityMapsBinding binding;
    private Button menu, currentLoc, startDrive, closeStartDrive;
    private SearchView searchView;
    private boolean buttonsAdded = false;

    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private FusedLocationProviderClient fusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isGPSEnabled()) {
            // If not enabled, show a dialog to enable GPS
            showGPSDisabledAlert();
        }
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        drawerLayout = findViewById(R.id.drawerLayout);
        navView = findViewById(R.id.navDrawer);
        navView.setItemIconTintList(null);
        navView.setNavigationItemSelectedListener(this);


        menu = findViewById(R.id.menu);
        menu.setOnClickListener(this);

        searchView = findViewById(R.id.searchView);
        searchView.setQuery("", false);  // Set an empty query
        searchView.setIconifiedByDefault(false);  // Ensure the SearchView is not iconified (collapsed)
        searchView.setQueryHint("לאן נוסעים?");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                Address address = null;
                if (location != null) {
                    Geocoder geocoder = new Geocoder(MapsActivity.this);

                    try {
                        List<Address> addresses = geocoder.getFromLocationName(location, 1);
                        if (addresses != null && !addresses.isEmpty()) {
                            address = addresses.get(0);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(MapsActivity.this, "Error finding location. Please try again.", Toast.LENGTH_SHORT).show();
                    }

                    if (address != null) {
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        map.addMarker(new MarkerOptions().position(latLng));
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f));
                    } else {
                        Toast.makeText(MapsActivity.this, "Location not found", Toast.LENGTH_SHORT).show();
                    }
                }


                if (!buttonsAdded) {
                    addStartDriveButtons();
                    buttonsAdded = true;
                }


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



        currentLoc = findViewById(R.id.currentLoc);
        currentLoc.setOnClickListener(this);


    }

    private void addStartDriveButtons()
    {
        /////////////////////////////////////
        //        כפתור התחל נסיעה
        /////////////////////////////////////
        startDrive = new Button(MapsActivity.this);

        RelativeLayout.LayoutParams sdriveParams = new RelativeLayout.LayoutParams(500, -2);
        sdriveParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        sdriveParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        sdriveParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        sdriveParams.setMargins(70,0,0,90);
        startDrive.setLayoutParams(sdriveParams);
        startDrive.setBackground(getDrawable(R.drawable.start_drive_background));
        startDrive.setText("התחל נסיעה");
        startDrive.setTextSize(24);
        startDrive.setTextColor(Color.WHITE);
        startDrive.setOnClickListener(this); 

        /////////////////////////////////////
        //        כפתור סגור התחל נסיעה
        /////////////////////////////////////

        closeStartDrive = new Button(MapsActivity.this);

        RelativeLayout.LayoutParams cdriveParams = new RelativeLayout.LayoutParams(250, -2);
        cdriveParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        cdriveParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        cdriveParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        cdriveParams.setMargins(0,0,210,90);
        closeStartDrive.setLayoutParams(cdriveParams);
        closeStartDrive.setBackground(getDrawable(R.drawable.close_start_drive_background));
        closeStartDrive.setText("סגור");
        closeStartDrive.setTextSize(24);
        closeStartDrive.setTextColor(Color.WHITE);




        RelativeLayout parentLayout = findViewById(R.id.layout);

        parentLayout.addView(startDrive);
        parentLayout.addView(closeStartDrive);

        closeStartDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Remove buttons from the parentLayout
                parentLayout.removeView(startDrive);
                parentLayout.removeView(closeStartDrive);
                searchView.setQuery("",false);

                // Update the flag
                buttonsAdded = false;
            }
        });

    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void showGPSDisabledAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("אנא הפעל שירותי מיקום")
                .setCancelable(false)
                .setPositiveButton("הפעל מיקום", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Open GPS settings
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("לא תודה", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        // You can handle the cancel action here (e.g., display a message or close the app)
                        Toast.makeText(MapsActivity.this, "האפליקציה חייבת שירותי מיקום בשביל לפעול", Toast.LENGTH_LONG).show();
                        finish(); // Close the app if GPS is not enabled and the user cancels
                    }
                });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMinZoomPreference(3.0f);
        //startLocationUpdates();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return; 
        }
        map.setMyLocationEnabled(true);
        fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location)
            {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude())
                        ,17f));
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (id == R.id.profile) {
            Intent intent = new Intent(MapsActivity.this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.settings) {
            Intent intent1 = new Intent(MapsActivity.this, SettingActivity.class);
            startActivity(intent1);
        }

        return false;
    }


    @Override
    public void onClick(View v) {
        if (menu == v) {
            drawerLayout.openDrawer(GravityCompat.START);
        }

        if (currentLoc == v)
        {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            map.setMyLocationEnabled(true);
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f), 1000, null);
                    }
                }
            });
        }
        
        if (startDrive == v)
        {
            Toast.makeText(this, "פה צריך להתחיל מסלול למיקום החדש", Toast.LENGTH_SHORT).show();
        }
    }




}
