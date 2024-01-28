package com.example.ecowheelztest1.Ui.Profile;

import android.content.Context;

import com.example.ecowheelztest1.Repository.Repository;
import com.example.ecowheelztest1.Repository.User;

public class ProfileModule {

    private Repository repository;
    private Context context;

    public ProfileModule(Context context) {
        repository = new Repository(context);
        this.context = context;
    }

    public User getUserRegister(){
        return repository.getUserFromSQLite();
    }
    public User getUserLogIn(){
        return repository.getLogIn();
    }
    public boolean getIsRegistered()
    {
        return repository.getIsRegistered();
    }
    public boolean getIsLoggedIn()
    {
        return repository.getIsLoggedIn();
    }

}
