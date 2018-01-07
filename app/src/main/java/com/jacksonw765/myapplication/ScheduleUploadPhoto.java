package com.jacksonw765.myapplication;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import dev.niekirk.com.instagram4android.Instagram4Android;
import dev.niekirk.com.instagram4android.requests.InstagramUploadPhotoRequest;
import dev.niekirk.com.instagram4android.requests.InstagramUploadVideoRequest;

/**
 * Created by jacks on 1/5/2018.
 */

public class ScheduleUploadPhoto {

    Login login;
    Instagram4Android instagram;
    Context context;

    public ScheduleUploadPhoto(Context context) {
        if(login == null || !login.isLoggedIn()) {
            login = new Login(context);
            instagram = login.getInstagram();
        }
        else {
            instagram = login.getInstagram();
        }
        this.context = context;
    }

    public void scheduleUpload(final File file, final String caption, long timeTillUpload) {
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        Toast.makeText(context, "Photo Upload Scheduled", Toast.LENGTH_LONG).show();
        exec.schedule(new Runnable(){
            @Override
            public void run() {
                uploadPhoto(file, caption);
            }
        }, timeTillUpload, TimeUnit.MINUTES);

    }

    private void uploadPhoto(File file, String caption) {
        try {
            instagram.sendRequest(new InstagramUploadPhotoRequest(
                    (file),
                    caption));
            Toast.makeText(context, "Photo Uploaded!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
