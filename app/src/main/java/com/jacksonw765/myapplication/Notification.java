package com.jacksonw765.myapplication;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jacks on 1/6/2018.
 */

public class Notification extends AppCompatActivity {

    NotificationCompat.Builder mBuilder;
    Context context;

    public Notification(Context context) {
        this.context = context;
       mBuilder = new NotificationCompat.Builder(context, "056")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Upload Success!")
                .setContentText("Your post has been uploaded to Instagram!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

    public void pushNotification() {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(056, mBuilder.build());
    }
}
