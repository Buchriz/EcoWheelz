package com.example.ecowheelztest1.Ui.Profile;

import android.content.Context;

import com.example.ecowheelztest1.Repository.Repository;
import com.example.ecowheelztest1.Repository.User;

public class ProfileModule {

    Repository repository;
    private Context context;

    public ProfileModule(Context context) {
        repository = new Repository(context);
        this.context = context;
    }

    public User getUser(){
        return repository.getUserFromSQLite();
    }
}
