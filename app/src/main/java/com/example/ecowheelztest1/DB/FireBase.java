package com.example.ecowheelztest1.DB;

import com.google.firebase.auth.FirebaseAuth;

public class FireBase {


    private FirebaseAuth firebaseAuth;


    public FireBase() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void register(String email, String phoneNumber)
    {
        firebaseAuth.createUserWithEmailAndPassword(email,phoneNumber);
    }

    public void LogIn(String email, String phoneNumber)
    {
        firebaseAuth.signInWithEmailAndPassword(email, phoneNumber);
    }

    public void LogOut()
    {
        firebaseAuth.signOut();
    }
}
