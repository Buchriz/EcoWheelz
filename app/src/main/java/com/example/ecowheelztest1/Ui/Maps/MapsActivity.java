package com.example.ecowheelztest1.Ui.Maps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import com.example.ecowheelztest1.R;
import com.example.ecowheelztest1.Ui.Profile.ProfileActivity;
import com.example.ecowheelztest1.Ui.Settings.NightModeSwitch;
import com.example.ecowheelztest1.Ui.Settings.SettingActivity;
import com.example.ecowheelztest1.databinding.ActivityMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private GoogleMap map;
    private ActivityMapsBinding binding;
    private Button menu, currentLoc, startDrive, closeStartDrive, backToDestination;
    private SearchView searchView;
    private TextView tvHome, tvWork;
    private boolean buttonsAdded = false, isLoggedIn;
    private RelativeLayout parentLayout, driveButtonsRelativeLayout;
    private LatLng destinationLatLng;

    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private FusedLocationProviderClient fusedLocationClient;

    private MapsModule mapsModule;
    private NightModeSwitch nightModeSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (!isGPSEnabled()) {
            Intent intent = new Intent(MapsActivity.this, EnableGPSActivity.class);
            startActivity(intent);
        }
        else {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(MapsActivity.this);
        }



        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            activityResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mapsModule = new MapsModule(this);

        parentLayout = findViewById(R.id.layout);
        drawerLayout = findViewById(R.id.drawerLayout);
        navView = findViewById(R.id.navDrawer);
        navView.setItemIconTintList(null);
        navView.setNavigationItemSelectedListener(this);
        nightModeSwitch = new NightModeSwitch();


        menu = findViewById(R.id.menu);
        menu.setOnClickListener(this);
        currentLoc = findViewById(R.id.currentLoc);
        currentLoc.setOnClickListener(this);

        searchView = findViewById(R.id.searchView);
        searchView.setQuery("", false);  // Set an empty query
        searchView.setIconifiedByDefault(false);  // Ensure the SearchView is not iconified (collapsed)
        searchView.setQueryHint("לאן נוסעים?");
        SearchViewCommit(searchView);


    }

    private final ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean b) {
            if (b)
            {
                Toast.makeText(MapsActivity.this, "Permission granted!", Toast.LENGTH_SHORT).show();
            }
        }
    });


    private boolean isGPSEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMinZoomPreference(3.0f);

        if (nightModeSwitch.GetNightModSwitch())
        {
            map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_dark_mode));
            searchView.setBackground(getDrawable(R.drawable.et_style_dark_mode));
        }
        else {
            map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_light_mode));
            searchView.setBackground(getDrawable(R.drawable.et_style));
        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
        fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location)
            {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),16.7f));
            }
        });

        isLoggedIn = mapsModule.getIsLoggedIn();
        if (isLoggedIn) {
            RelativeLayout savedPlacesLayout = Saved_places(map);
            parentLayout.addView(savedPlacesLayout);
//            tvHome.setOnClickListener(this);
//            tvWork.setOnClickListener(this);
        }
    }


    private void SearchViewCommit(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                closeStartDriveButtons(driveButtonsRelativeLayout);

                if (query.length() == 0){
                    Toast.makeText(MapsActivity.this, "Enter destination", Toast.LENGTH_SHORT).show();
                }
                else {
                    Address address = null;
                    Geocoder geocoder = new Geocoder(MapsActivity.this);

                    try {
                        List<Address> addresses = geocoder.getFromLocationName(query, 1);
                        if (addresses != null && !addresses.isEmpty()) {
                            address = addresses.get(0);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(MapsActivity.this, "Error finding location. Please try again.", Toast.LENGTH_SHORT).show();
                    }

                    if (address != null) {
                        destinationLatLng = new LatLng(address.getLatitude(), address.getLongitude());
                        map.addMarker(new MarkerOptions().position(destinationLatLng));
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(destinationLatLng, 16.7f), 1000, null);

                        if (!buttonsAdded) {
                            addStartDriveButtons(query);
                            buttonsAdded = true;
                        }
                    } else {
                        Toast.makeText(MapsActivity.this, "Location not found", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    public void addStartDriveButtons(String query) {

        /////////////////////////////////////
        //     רקע שמכיל את הכפתורים
        /////////////////////////////////////
        driveButtonsRelativeLayout = new RelativeLayout(MapsActivity.this);

        RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(-1, 400);
        rl.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rl.addRule(RelativeLayout.ALIGN_PARENT_END);
        driveButtonsRelativeLayout.setLayoutParams(rl);
        driveButtonsRelativeLayout.setBackground(getDrawable(R.drawable.start_drive_buttons_layout_background));

        // Animation for sliding in from the bottom
        Animation slideInAnimation = new TranslateAnimation(0, 0, 1000, 0); // Adjust the '1000' as needed for your layout
        slideInAnimation.setDuration(1000); // Set the duration of the animation in milliseconds
        slideInAnimation.setFillAfter(true);

        // Apply animation to the buttons
        driveButtonsRelativeLayout.startAnimation(slideInAnimation);

        /////////////////////////////////////
        //        שם המיקום הרשום
        /////////////////////////////////////
        TextView tvName = new TextView(MapsActivity.this);
        RelativeLayout.LayoutParams tvParams = new RelativeLayout.LayoutParams(-2, -2);
        tvParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        tvParams.setMargins(0,20,40,0);
        tvName.setLayoutParams(tvParams);
        tvName.setText(query);
        tvName.setTextColor(Color.BLACK);
        tvName.setTextSize(22);



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
        //      כפתור סגור התחל נסיעה
        /////////////////////////////////////
        closeStartDrive = new Button(MapsActivity.this);

        RelativeLayout.LayoutParams cdriveParams = new RelativeLayout.LayoutParams(350, -2);
        cdriveParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        cdriveParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        cdriveParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        cdriveParams.setMargins(0,0,100,90);
        closeStartDrive.setLayoutParams(cdriveParams);
        closeStartDrive.setBackground(getDrawable(R.drawable.close_start_drive_background));
        closeStartDrive.setText("סגור");
        closeStartDrive.setTextSize(24);
        closeStartDrive.setTextColor(Color.WHITE);


        /////////////////////////////////////
        //    הכנסה של הפרטים לתוך המסך
        /////////////////////////////////////

        driveButtonsRelativeLayout.addView(tvName);
        driveButtonsRelativeLayout.addView(startDrive);
        driveButtonsRelativeLayout.addView(closeStartDrive);
        parentLayout.addView(driveButtonsRelativeLayout);

        ////////////////////////////////////////////
        //   אנימציה להעלאת כפתור המיקום הנוכחי
        ////////////////////////////////////////////
        RelativeLayout.LayoutParams curLocParams1 = new RelativeLayout.LayoutParams(150,150);
        curLocParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        curLocParams1.addRule(RelativeLayout.ALIGN_PARENT_START);
        curLocParams1.setMargins(0,0,20,410);
        currentLoc.setLayoutParams(curLocParams1);

        Animation slideInAnimationForCurrentLoc1 = new TranslateAnimation(1000, 0, 0, 0);
        slideInAnimationForCurrentLoc1.setDuration(1000); // Set the duration of the animation in milliseconds
        slideInAnimationForCurrentLoc1.setFillAfter(true);

        // Apply animation to the buttons
        currentLoc.startAnimation(slideInAnimationForCurrentLoc1);


        closeStartDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              closeStartDriveButtons(driveButtonsRelativeLayout);
            }
        });

    }

    public void closeStartDriveButtons(RelativeLayout relativeLayout) {
        // Remove buttons from the parentLayout
        tvHome.setClickable(true);
        tvWork.setClickable(true);
        parentLayout.removeView(relativeLayout);
        parentLayout.removeView(backToDestination);
        searchView.setQuery("",false);
        map.clear();
        // Update the flag
        buttonsAdded = false;

        ////////////////////////////////////////////
        //   אנימציה להורדת כפתור המיקום הנוכחי
        ////////////////////////////////////////////
        RelativeLayout.LayoutParams curLocParams2 = new RelativeLayout.LayoutParams(150,150);
        curLocParams2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        curLocParams2.addRule(RelativeLayout.ALIGN_PARENT_START);
        curLocParams2.setMargins(0,0,20,80);
        currentLoc.setLayoutParams(curLocParams2);

        Animation slideOutAnimationForCurrentLoc = new TranslateAnimation(0, 0, -330, 0);
        slideOutAnimationForCurrentLoc.setDuration(1000); // Set the duration of the animation in milliseconds
        slideOutAnimationForCurrentLoc.setFillAfter(true);

        // Apply animation to the buttons
        currentLoc.startAnimation(slideOutAnimationForCurrentLoc);
    }

    public RelativeLayout Saved_places(GoogleMap map){
        RelativeLayout relativeLayout = new RelativeLayout(this);

        RelativeLayout.LayoutParams RlayoutParams = new RelativeLayout.LayoutParams(640,100);
        RlayoutParams.addRule(RelativeLayout.BELOW, R.id.searchView); // Set the linear layout below the search view
        RlayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        RlayoutParams.setMargins(65,-1,0,0);
        relativeLayout.setLayoutParams(RlayoutParams);
        relativeLayout.setBackgroundColor(Color.WHITE);


        tvHome = new TextView(this);
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

                String str = mapsModule.getHomeLocation();
                if (str == null){
                    Intent intent = new Intent(MapsActivity.this, SettingActivity.class);
                    tvHome.setClickable(false);
                    startActivity(intent);
                    Toast.makeText(MapsActivity.this, "הכנס מיקום בית", Toast.LENGTH_SHORT).show();
                }
                else {
//                    Toast.makeText(context,repository.getSharedPreferences().getHomeLocation(), Toast.LENGTH_SHORT).show();
//                    map.addMarker(new MarkerOptions().position(homeLatLng));
//                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, 16.7f), 1000, null);

                    map.clear();
                    destinationLatLng = new LatLng(mapsModule.getLocation(str).getLatitude(), mapsModule.getLocation(str).getLongitude());
                    map.addMarker(new MarkerOptions().position(destinationLatLng));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(destinationLatLng, 16.7f), 1000, null);

                    if (!buttonsAdded) {
                        addStartDriveButtons(str);
                        buttonsAdded = true;
                        tvHome.setClickable(false);
                    }
                }
            }
        });

        tvWork = new TextView(this);
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

                String str = mapsModule.getWorkLocation();
                if (str == null){
                    Intent intent = new Intent(MapsActivity.this, SettingActivity.class);
                    tvWork.setClickable(false);
                    startActivity(intent);
                    Toast.makeText(MapsActivity.this, "הכנס מיקום עבודה", Toast.LENGTH_SHORT).show();
                }
                else {
//                    Toast.makeText(context, repository.getSharedPreferences().getWorkLocation(), Toast.LENGTH_SHORT).show();
//                    map.addMarker(new MarkerOptions().position(workLatLng));
//                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(workLatLng, 16.7f), 1000, null);

                    map.clear();
                    destinationLatLng = new LatLng(mapsModule.getLocation(str).getLatitude(), mapsModule.getLocation(str).getLongitude());
                    map.addMarker(new MarkerOptions().position(destinationLatLng));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(destinationLatLng, 16.7f), 1000, null);

                    if (!buttonsAdded) {
                        addStartDriveButtons(str);
                        buttonsAdded = true;
                        tvWork.setClickable(false);
                    }
                }
            }
        });


        NightModeSwitch nightModeSwitch = new NightModeSwitch();
        if (nightModeSwitch.GetNightModSwitch())
        {
            relativeLayout.setBackground(getDrawable(R.drawable.home_work_background_dark_mode));
            tvHome.setTextColor(Color.WHITE);
            tvWork.setTextColor(Color.WHITE);
        }
        else
        {
            relativeLayout.setBackground(getDrawable(R.drawable.home_work_background));
            tvHome.setTextColor(Color.BLACK);
            tvWork.setTextColor(Color.BLACK);
        }

        relativeLayout.addView(tvHome);
        relativeLayout.addView(tvWork);

        return relativeLayout;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.close) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if (id == R.id.profile) {
            Intent intent = new Intent(MapsActivity.this, ProfileActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.settings) {
            Intent intent = new Intent(MapsActivity.this, SettingActivity.class);
            startActivity(intent);
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
            if (isGPSEnabled()) {
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                map.setMyLocationEnabled(true);
                fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.7f), 1000, null);
                        }
                    }
                });

                if (buttonsAdded) {
                    backToDestination = new Button(MapsActivity.this);

                    RelativeLayout.LayoutParams DesParams1 = new RelativeLayout.LayoutParams(150, 150);
                    DesParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    DesParams1.addRule(RelativeLayout.ALIGN_PARENT_END);
                    DesParams1.setMargins(20, 0, 0, 410);
                    backToDestination.setLayoutParams(DesParams1);
                    backToDestination.setBackground(getDrawable(R.drawable.back_to_destination));
                    parentLayout.addView(backToDestination);

                    Animation slideInAnimationForBackToDes = new TranslateAnimation(-1000, 0, 0, 0);
                    slideInAnimationForBackToDes.setDuration(500); // Set the duration of the animation in milliseconds
                    slideInAnimationForBackToDes.setFillAfter(true);

                    // Apply animation to the buttons
                    backToDestination.startAnimation(slideInAnimationForBackToDes);

                    backToDestination.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(destinationLatLng, 16.7f), 1000, null);

                            Animation slideOutAnimationForBackToDes = new TranslateAnimation(0, -300, 0, 0);
                            slideOutAnimationForBackToDes.setDuration(500); // Set the duration of the animation in milliseconds
                            slideOutAnimationForBackToDes.setFillAfter(true);

                            // Apply animation to the buttons
                            backToDestination.startAnimation(slideOutAnimationForBackToDes);

                            parentLayout.removeView(backToDestination);
                        }
                    });
                }
            }
            else {
                Intent intent = new Intent(MapsActivity.this, EnableGPSActivity.class);
                startActivity(intent);
            }
        }
        
        if (startDrive == v)
        {
            if (isGPSEnabled()) {
                Toast.makeText(this, "פה צריך להתחיל מסלול למיקום החדש", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(MapsActivity.this, EnableGPSActivity.class);
                startActivity(intent);
            }
        }

        if (tvHome == v && isLoggedIn)
        {
            String str = mapsModule.getHomeLocation();
            if (str == null){
                Intent intent = new Intent(MapsActivity.this, SettingActivity.class);
                tvHome.setClickable(false);
                startActivity(intent);
                Toast.makeText(MapsActivity.this, "הכנס מיקום בית", Toast.LENGTH_SHORT).show();
            }
            else {
                //Toast.makeText(context,repository.getSharedPreferences().getHomeLocation(), Toast.LENGTH_SHORT).show();
                LatLng homeLatLng = new LatLng(mapsModule.getLocation(str).getLatitude(), mapsModule.getLocation(str).getLongitude());
                map.addMarker(new MarkerOptions().position(homeLatLng));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, 16.7f), 1000, null);
                MapsActivity mapsActivity = new MapsActivity();
                mapsActivity.addStartDriveButtons(str);
            }
        }

    }
}
