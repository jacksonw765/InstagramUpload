package com.jacksonw765.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by jacks on 1/8/2018.
 */

public class UploadPhoto {
    private ScheduleUploadPhoto scheduleUploadPhoto;
    private Button buttonSetDate, buttonSetTime, buttonPickImage, buttonSchedule;
    private ImageView imagePreview;
    private TextView textDate, textTime;
    private DateTimePicker dateTimePicker;
    private View view;

    public UploadPhoto() {
        this.view = view;
        //scheduleUploadPhoto = new ScheduleUploadPhoto(dialog.getContext());
        dateTimePicker = new DateTimePicker();
        buttonPickImage = view.findViewById(R.id.buttonPickImage);
        buttonSetDate = view.findViewById(R.id.buttonSetDate);
        buttonSetTime = view.findViewById(R.id.buttonSetTime);
        buttonSchedule = view.findViewById(R.id.buttonSchedule);
        imagePreview = view.findViewById(R.id.imageView);
        textDate = view.findViewById(R.id.textViewDate);
        textTime = view.findViewById(R.id.textViewTime);
    }
}
