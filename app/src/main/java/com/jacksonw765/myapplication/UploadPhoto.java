package com.jacksonw765.myapplication;

import java.io.File;
import java.io.IOException;

import dev.niekirk.com.instagram4android.Instagram4Android;
import dev.niekirk.com.instagram4android.requests.InstagramUploadPhotoRequest;
import dev.niekirk.com.instagram4android.requests.InstagramUploadVideoRequest;

/**
 * Created by jacks on 1/5/2018.
 */

public class UploadPhoto {

    Instagram4Android instagram;

    public UploadPhoto(String username, String password) {
        instagram = Instagram4Android.builder().username("jw.optical").password("rascalm123").build();
        instagram.setup();
        try {

            instagram.login();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void upload(String thumbnailPath) {
        try {

            instagram.login();
            instagram.sendRequest(new InstagramUploadVideoRequest(
                    new File(thumbnailPath),
                    "Posted with Instagram4j @jacksonw765, how cool is that?"));
            System.out.println("Done!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
