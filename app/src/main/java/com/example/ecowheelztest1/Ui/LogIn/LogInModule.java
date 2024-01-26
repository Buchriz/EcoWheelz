package com.example.ecowheelztest1.Ui.LogIn;

import android.content.Context;

import com.example.ecowheelztest1.Repository.Repository;

public class LogInModule {
    private String emailLogIn;
    private int phoneLogIn;
    private Context context;
    Repository repository;

    public LogInModule(Context context, String emailLogIn, int phoneLogIn) {
        this.emailLogIn = emailLogIn;
        this.phoneLogIn = phoneLogIn;
        this.context = context;
        repository = new Repository(context);
    }

    public void LogIn()
    {
        repository.LogIn(emailLogIn,String.valueOf(phoneLogIn));
    }


}
