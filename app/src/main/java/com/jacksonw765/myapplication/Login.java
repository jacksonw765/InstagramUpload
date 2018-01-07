package com.jacksonw765.myapplication;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import dev.niekirk.com.instagram4android.Instagram4Android;

public class Login {

    private Instagram4Android instagram;
    private Context context;

    public Login(Context context) {
        if(instagram == null || !instagram.isLoggedIn()) {
            login();

        }
        this.context = context;
    }

    public void login() {
        instagram = Instagram4Android.builder().username("jw.optical").password("rascalm123").build();
        instagram.setup();
        try {

            instagram.login();
            //Toast.makeText(context, "Logged into Instagram", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Instagram4Android getInstagram(){
        return instagram;
    }

    public boolean isLoggedIn() {
        return instagram.isLoggedIn();
    }
}