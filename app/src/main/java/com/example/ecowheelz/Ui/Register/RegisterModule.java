package com.example.ecowheelz.Ui.Register;

import android.content.Context;

import com.example.ecowheelz.Repository.Repository;

import java.util.LinkedList;
import java.util.Queue;

public class RegisterModule {

    private String userName, email, fullName, phoneNumber;
    private static Repository repository;
    private Context context;

    public RegisterModule(Context context,String userName, String email, String fullName, String phoneNumber) {
        this.userName = userName;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        repository = new Repository(context);
        this.context = context;
    }


    ////////////////////////////////////////////////////
    //      Checking the Register Information
    ////////////////////////////////////////////////////
    Queue<String> checkErrors()
    {
        Queue<String> qErrors = new LinkedList<>();

        //////////////////////
        //   בדיקות שם משתמש
        /////////////////////
        if (this.userName.length()==0)
        {
            qErrors.add("Enter UserName");
            //return false;
        }

        //////////////////////
        //   בדיקות אימייל
        /////////////////////
        if (email.length() == 0)
        {
            qErrors.add("Enter Email");
            //return false;
        }
        int x = 0;
        boolean b = false;
        while (x <= email.length())
        {
            if (email.charAt(x) == '@')
                b = true;
            x++;
        }
        if (!b){
            qErrors.add("Email needs a @");
            //return false;
        }

        //////////////////////
        //   בדיקות שם מלא
        /////////////////////
        if (fullName.length() == 0)
        {
            qErrors.add("Enter Full Name");
            //return false;
        }


        ////////////////////////
        //  בדיקות מספר טלפון
        ////////////////////////
        if (String.valueOf(phoneNumber).length() == 0)
        {
            qErrors.add("Enter Phone");
            //return false;
        }

        return qErrors;
    }



    ////////////////////////////////////
    //      Add User To SQLite
    ////////////////////////////////////
    boolean OnSuccess(){
        return repository.addUser(userName,email,fullName,phoneNumber);
    }
}
