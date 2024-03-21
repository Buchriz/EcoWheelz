package com.example.ecowheelz.Repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

import com.example.ecowheelz.DB.MyDatabaseHelper;

public class Repository {

    private final MyDatabaseHelper databaseHelper;
    private final Context context;
    private SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;


    public Repository(Context context) {
        databaseHelper = new MyDatabaseHelper(context);
        this.context = context;
        sharedPreferences = context.getSharedPreferences("sharedPreferences",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    /////////////////////////////////////////
    //         SharedPreferences
    /////////////////////////////////////////
    public void setSharedPreferences(User user)
    {
        editor.putString("row_id", user.getRow_id());
        editor.putString("userName", user.getUserName());
        editor.putString("email", user.getEmail());
        editor.putString("fullName",user.getFullName());
        editor.putString("phone", user.getPhoneNumber());
        editor.putString("homeLocation",user.getHomeLocation());
        editor.putString("workLocation", user.getWorkLocation());

        editor.apply();
    }
    public User getSharedPreferences()
    {
        String row_id;
        String userName;
        String email;
        String fullName;
        String phone;
        String homeLocation;
        String workLocation;

        if (sharedPreferences.getBoolean("isLoggedIn", false))
        {
            row_id = sharedPreferences.getString("row_id",null);
            userName = sharedPreferences.getString("userName",null);
            email = sharedPreferences.getString("email",null);
            fullName = sharedPreferences.getString("fullName",null);
            phone = sharedPreferences.getString("phone", null);
            homeLocation = sharedPreferences.getString("homeLocation",null);
            workLocation = sharedPreferences.getString("workLocation",null);

            return new User(userName,email,fullName,phone,row_id,homeLocation,workLocation);
        }

        return null;
    }


    //////////////////////////////////////////////////////////////////////
    //        Add user To SQLite If The Phone Number Not Exist
    //////////////////////////////////////////////////////////////////////
    public boolean addUser(String userName, String email, String fullName, String phoneNumber) {

        Cursor cursor = databaseHelper.readAllData();

        int n = cursor.getCount();
        cursor.moveToFirst();

        for (int i = 0; i < n; i++) {
            if (phoneNumber.equals(cursor.getString(4)))
                return false;

            cursor.moveToNext();
        }

        return databaseHelper.addUser(userName,email,fullName,phoneNumber);
    }



    ////////////////////////////////////////////
    //       Get User Logged-In
    ////////////////////////////////////////////
    public boolean getIsLoggedIn() {
        return sharedPreferences.getBoolean("isLoggedIn",false);
    }
    public void setIsLoggedIn(boolean c) {
        editor.putBoolean("isLoggedIn",c);
    }





    ////////////////////////////////////////////
    //       Update User Information
    ////////////////////////////////////////////
    public void updateData(String row_id, String username, String email, String fullname, String phonenumber) {
        databaseHelper.updateData(row_id,username,email,fullname,phonenumber);
        setSharedPreferences(new User(username,email,fullname,phonenumber,row_id,getSharedPreferences().getHomeLocation(),getSharedPreferences().getWorkLocation()));
    }
    public void updateHomeLocation(String homeLoc)
    {
        databaseHelper.updateHomeLocation(getSharedPreferences().getRow_id(),homeLoc);
        setSharedPreferences(new User(getSharedPreferences().getUserName(),getSharedPreferences().getEmail(),getSharedPreferences().getFullName(),getSharedPreferences().getPhoneNumber(),getSharedPreferences().getRow_id(),homeLoc,getSharedPreferences().getWorkLocation()));
    }
    public void updateWorkLocation(String workLoc)
    {
        databaseHelper.updateWorkLocation(getSharedPreferences().getRow_id(),workLoc);
        setSharedPreferences(new User(getSharedPreferences().getUserName(),getSharedPreferences().getEmail(),getSharedPreferences().getFullName(),getSharedPreferences().getPhoneNumber(),getSharedPreferences().getRow_id(),getSharedPreferences().getHomeLocation(),workLoc));
    }





    ////////////////////////////////////////////
    //            User Log-In
    ////////////////////////////////////////////
    public boolean LogIn(String emailLogIn, String phoneLogIn) {
        Cursor cursor = databaseHelper.readAllData();

        int n = cursor.getCount();
        cursor.moveToFirst();

        String row_id = null;
        String username = null;
        String email = null;
        String fullname = null;
        String phonenumber = null;
        String homeLocation = null;
        String workLocation = null;

        boolean same = false;

        for (int i = 0; i < n; i++) {
            email = cursor.getString(2);
            phonenumber = cursor.getString(4);

            if (email.equals(emailLogIn) && phonenumber.equals(phoneLogIn)) {
                row_id = cursor.getString(0);
                username = cursor.getString(1);
                fullname = cursor.getString(3);
                homeLocation = cursor.getString(5);
                workLocation = cursor.getString(6);

                same = true;
                break;
            }

            cursor.moveToNext();
        }

        if (!same) {
            return false;
        }
        else
        {
            setIsLoggedIn(true);
            setSharedPreferences(new User(username,email,fullname,phonenumber,row_id,homeLocation,workLocation));
        }
        return true;
    }




    ////////////////////////////////////////////
    //            User Log-Out
    ////////////////////////////////////////////
    public void LogOut() {
        sharedPreferences = context.getSharedPreferences("sharedPreferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
