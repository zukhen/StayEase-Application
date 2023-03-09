package com.example.qlks.Firebase;


import com.example.qlks.model.User;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDAO {

    private static final FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();



    public static void addUser(String email,String password,long balance) {

        DatabaseReference myRef = mDatabase.getReference("Users");
        myRef.push().setValue(new User(email, password, balance));
    }
}
