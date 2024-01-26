package com.example.ecowheelztest1.Repository;

import android.content.Context;
import android.database.Cursor;

import com.example.ecowheelztest1.DB.FireBase;
import com.example.ecowheelztest1.DB.MyDatabaseHelper;

public class Repository {

    FireBase fireBase;
    MyDatabaseHelper databaseHelper;

    public Repository(Context context) {
        fireBase = new FireBase();
        databaseHelper = new MyDatabaseHelper(context);
    }

    public void addUser(User user)
    {
        addNewUserToFireBase(user);
        addNewUserToSQLite(user);
    }

    public void addNewUserToSQLite(User user)
    {
        databaseHelper.addUser(user.getUserName(), user.getEmail(), user.getFullName(), user.getPhoneNumber());
    }

    public void addNewUserToFireBase(User user)
    {
        fireBase.register(user.getEmail(), user.getPhoneNumber());
    }
    public User getUserFromSQLite()
    {
        Cursor cursor = databaseHelper.readAllData();
        String username = cursor.getString(1);
        String email = cursor.getString(2);
        String fullname = cursor.getString(3);
        String phonenumber = cursor.getString(4);
        return new User(username,email,fullname,phonenumber);
    }



    public void LogIn(String emailLogIn, String phoneLogIn)
    {
        fireBase.LogIn(emailLogIn,phoneLogIn);
    }
    public void LogOut()
    {
        fireBase.LogOut();
        databaseHelper.deleteAllData();
    }
}
