package com.example.ecowheelztest1.Repository;

import com.google.firebase.database.FirebaseDatabase;

public class FireBase {

    FirebaseDatabase firebaseDatabase;

    public FireBase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
    }


}
