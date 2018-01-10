package com.jacksonw765.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private ImagePicker imagePicker;
    private ImageView imageView;
    private Context context;
    private UploadPhoto uploadPhoto;

    private File photo = null;
    private String caption = null;
    private int timeTillUpload = -1;

    private AlertDialog dialog;


    private Button buttonSetDate, buttonSetTime, buttonPickImage, buttonSchedule;
    private ImageView imagePreview;
    private TextView textDate, textTime, textViewCaption;
    private DateTimePicker dateTimePicker;

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

    //This method is very messy! Sorry future me :/
    public void showNewPhotoDialog(View view) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View promptView = layoutInflater.inflate(R.layout.upload_photo, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);


        //ScheduleUploadPhoto scheduleUploadPhoto = new ScheduleUploadPhoto(getApplicationContext());
        dateTimePicker = new DateTimePicker();
        buttonPickImage = promptView.findViewById(R.id.buttonPickImage);
        buttonSetTime = promptView.findViewById(R.id.buttonSetTime);
        buttonSetDate = promptView.findViewById(R.id.buttonSetDate);
        buttonSchedule = promptView.findViewById(R.id.buttonSchedule);
        imagePreview = promptView.findViewById(R.id.imageView);
        textDate = promptView.findViewById(R.id.textViewDate);
        textTime = promptView.findViewById(R.id.textViewTime);
        textViewCaption = promptView.findViewById(R.id.textCaption);


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

        //FINAL METHOD CALLED BE CAREFUL!!!!
        buttonSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    caption = String.valueOf(textViewCaption.getText());
                    timeTillUpload = dateTimePicker.getCalculatedTime();
                } catch (Exception e) {
                    Toast.makeText(promptView.getContext(), "Caption can't be blank", Toast.LENGTH_SHORT).show();
                }
                if(photo == null || caption == null || timeTillUpload == -1) {
                    Toast.makeText(view.getContext(), "Not all variables set!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Looper.prepare();
                            ScheduleUploadPhoto scheduleUploadPhoto = new ScheduleUploadPhoto(context);
                            scheduleUploadPhoto.scheduleUpload(new File(photo.getPath()), caption, timeTillUpload);
                            Toast.makeText(getApplicationContext(), "Photo Scheduled", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.dismiss();
                    thread.start();
                    //alertDialogBuilder.dis

                }
            }
        });
        alertDialogBuilder.setView(promptView);
        dialog = alertDialogBuilder.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                 //File write logic here
                 if(requestCode == Picker.PICK_IMAGE_DEVICE) {
                     imagePicker.submit(data);
                 }
    }

}
