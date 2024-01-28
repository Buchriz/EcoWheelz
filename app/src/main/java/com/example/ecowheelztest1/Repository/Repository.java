package com.example.ecowheelztest1.Repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.widget.Toast;

import com.example.ecowheelztest1.DB.FireBase;
import com.example.ecowheelztest1.DB.MyDatabaseHelper;

public class Repository {

    private FireBase fireBase;
    private MyDatabaseHelper databaseHelper;
    private static boolean isRegistered;
    private static boolean isLoggedIn;
    private static String userNameLogin, emailLogin, fullNameLogin, phoneNumberLogin;
    private Context context;

    private SharedPreferences sharedPreferences;
    private void setSharedPreferences()
    {
        sharedPreferences = context.getSharedPreferences("sharedPreferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("isRegistered",isRegistered);
        editor.putBoolean("isLoggedIn",isLoggedIn);

        editor.apply();
    }
    private void getSharedPreferences()
    {
        sharedPreferences = context.getSharedPreferences("sharedPreferences",Context.MODE_PRIVATE);
        isRegistered = sharedPreferences.getBoolean("isRegistered",true);
        isLoggedIn = sharedPreferences.getBoolean("isLoggedIn",true);
    }

    public Repository(Context context) {
        fireBase = new FireBase();
        databaseHelper = new MyDatabaseHelper(context);
        this.context = context;
    }

    public void addUser(User user)
    {
        addNewUserToFireBase(user);
        addNewUserToSQLite(user);
    }
    public boolean getIsRegistered()
    {
        return isRegistered;
    }
    public void setIsRegistered(boolean b)
    {
        isRegistered = b;
    }

    public boolean getIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(boolean b) {
        isLoggedIn = b;
    }

    public void addNewUserToSQLite(User user)
    {
        databaseHelper.addUser(user.getUserName(), user.getEmail(), user.getFullName(), user.getPhoneNumber());
    }

    public void addNewUserToFireBase(User user)
    {
        fireBase.register(user.getEmail(), user.getPhoneNumber());
    }
    public User getUserFromSQLite() {
        Cursor cursor = databaseHelper.readAllData();

        int n = cursor.getCount();
        cursor.moveToFirst();

        String username = null;
        String email = null;
        String fullname = null;
        String phonenumber = null;

        for (int i = 0; i < n; i++) {
            username = cursor.getString(1);
            email = cursor.getString(2);
            fullname = cursor.getString(3);
            phonenumber = cursor.getString(4);
            cursor.moveToNext();
        }
        setSharedPreferences();
        return new User(username, email, fullname, phonenumber);
    }



    public void LogIn(String emailLogIn, String phoneLogIn)
    {
        fireBase.LogIn(emailLogIn,phoneLogIn);

        Cursor cursor = databaseHelper.readAllData();

        int n = cursor.getCount();
        cursor.moveToFirst();

        String username = null;
        String email = null;
        String fullname = null;
        String phonenumber = null;
        boolean same = false;

        for (int i = 0; i < n; i++) {
            email = cursor.getString(2);
            phonenumber = cursor.getString(4);

            if (email.equals(emailLogIn) && phonenumber.equals(phoneLogIn)) {
                username = cursor.getString(1);
                fullname = cursor.getString(3);
                same = true;
                break;
            }

            cursor.moveToNext();
        }

        if (!same) {
            Toast.makeText(context, "משתמש לא נמצא", Toast.LENGTH_SHORT).show();
            isLoggedIn = false;
        }
        else
        {
            userNameLogin = username;
            emailLogin = email;
            fullNameLogin = fullname;
            phoneNumberLogin = phonenumber;
            isLoggedIn = true;
            setSharedPreferences();
        }

    }
    public User getLogIn(){
        return new User(userNameLogin, emailLogin, fullNameLogin, phoneNumberLogin);
    }

    public void LogOut()
    {
        fireBase.LogOut();
        databaseHelper.deleteAllData();
        setIsRegistered(false);
        setIsLoggedIn(false);
    }
}
