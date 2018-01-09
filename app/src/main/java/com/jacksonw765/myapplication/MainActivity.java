package com.jacksonw765.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kbeanie.multipicker.api.ImagePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenImage;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import dev.niekirk.com.instagram4android.Instagram4Android;
import dev.niekirk.com.instagram4android.requests.InstagramUploadPhotoRequest;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

    private Button button;
    private ImagePicker imagePicker;
    private ImageView imageView;
    private Context context;
    private UploadPhoto uploadPhoto;

    private File photo = null;
    private String caption = null;
    private long timeTillUpload = -1;

    //private ScheduleUploadPhoto scheduleUploadPhoto;
    private Button buttonSetDate, buttonSetTime, buttonPickImage, buttonSchedule;
    private ImageView imagePreview;
    private TextView textDate, textTime;
    private DateTimePicker dateTimePicker;


    private int day;
    private int hour;
    private int minute;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.buttonNewPhoto);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNewPhotoDialog(view);

                /*

                uploadPhoto = new UploadPhoto(dialog);

                //showPhotoDialog(view);


                dpd.show(getFragmentManager(), "Datepickerdialog");

                        imagePicker = new ImagePicker(MainActivity.this);
                        imagePicker.setImagePickerCallback(new ImagePickerCallback() {
                            @Override
                            public void onImagesChosen(final List<ChosenImage> list) {
                                Thread t = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Looper.prepare();
                                        ChosenImage chosenImage = list.get(0);

                                        Post post = new Post();

                                        File file = new File(chosenImage.getOriginalPath());
                                        System.out.println(file.canRead());
                                        System.out.println(file.canExecute());
                                        System.out.println(file.canWrite());
                                        if(file.exists()) {
                                            System.out.println("file exists");
                                        }

                                        ScheduleUploadPhoto scheduleUploadPhoto = new ScheduleUploadPhoto(context);
                                        scheduleUploadPhoto.scheduleUpload(new File(file.getPath()), "this is a test",0);
                                    }
                                });
                                t.start();
                            }
                            @Override
                            public void onError(String s) {
                                System.out.println(s);
                            }
                        });



                        Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();*/
                    }
        });

    }


    public void showNewPhotoDialog(View view) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.upload_photo, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        //ScheduleUploadPhoto scheduleUploadPhoto = new ScheduleUploadPhoto(getApplicationContext());
        dateTimePicker = new DateTimePicker();
        buttonPickImage = promptView.findViewById(R.id.buttonPickImage);
        buttonSetTime = promptView.findViewById(R.id.buttonSetTime);
        buttonSetDate = promptView.findViewById(R.id.buttonSetDate);
        buttonSchedule = promptView.findViewById(R.id.buttonSchedule);
        imagePreview = promptView.findViewById(R.id.imageView);
        textDate = promptView.findViewById(R.id.textViewDate);
        textTime = promptView.findViewById(R.id.textViewTime);


        buttonSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateTimePicker.displayDatePicker(getFragmentManager());
            }
        });

        buttonSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateTimePicker.displayTimePicker(getFragmentManager());
            }
        });

        buttonPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePicker = new ImagePicker(MainActivity.this);
                imagePicker.setImagePickerCallback(new ImagePickerCallback() {
                    @Override
                    public void onImagesChosen(List<ChosenImage> list) {
                        photo = new File(list.get(0).getOriginalPath());
                        Bitmap myBitmap = BitmapFactory.decodeFile(photo.getAbsolutePath());
                        imagePreview.setImageBitmap(myBitmap);
                    }
                    @Override
                    public void onError(String s) {

                    }
                });
                imagePicker.pickImage();
            }
        });

        buttonSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(photo == null || caption == null || timeTillUpload == -1) {
                    Toast.makeText(view.getContext(), "Not all variables set!", Toast.LENGTH_SHORT).show();


                }
            }
        });
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.show();

    }

    private int getCalculatedTime() {

        //ToDo
        Calendar cal = Calendar.getInstance();
        int currentDay = cal.get(Calendar.DAY_OF_MONTH);
        int currentHour = cal.get(Calendar.HOUR);
        int currentMinute = cal.get(Calendar.MINUTE);

        int dayDif, hourDif, minDif;

        if(currentDay == day) {

        }
        if(currentHour == hour) {

        }

        if(currentMinute == minute) {

        }

    }

    private void getDate(int day) {
        this.day = day;
    }

    private void getTime(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
             //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);

                 //File write logic here
                 if(requestCode == Picker.PICK_IMAGE_DEVICE) {
                     imagePicker.submit(data);
                 }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        getDate(dayOfMonth);
        textDate.setText(dayOfMonth);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        getTime(hourOfDay, minute);
    }
}
