package com.jacksonw765.myapplication;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jacks on 1/6/2018.
 */

public class Notification extends AppCompatActivity {

    NotificationCompat.Builder mBuilder;

    public Notification(Context context) {
        mBuilder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Post scheduler")
                        .setContentText("Your picture has been posted!");
    }

    public void pushNotification() {
        NotificationManager mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(001, mBuilder.build());
    }
}
