package com.jacksonw765.myapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kbeanie.multipicker.api.ImagePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenImage;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.File;
import java.util.ArrayList;
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
    private ListView listView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);

        button = findViewById(R.id.buttonNewPhoto);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNewPhotoDialog(view);
            }
        });
    }

    //This method is very messy! Sorry future me :/
    public void showNewPhotoDialog(View view) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View promptView = layoutInflater.inflate(R.layout.upload_photo, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        dateTimePicker = new DateTimePicker();
        buttonPickImage = promptView.findViewById(R.id.buttonPickImage);
        buttonSetTime = promptView.findViewById(R.id.buttonSetTime);
        buttonSetDate = promptView.findViewById(R.id.buttonSetDate);
        buttonSchedule = promptView.findViewById(R.id.buttonSchedule);
        imagePreview = promptView.findViewById(R.id.imageView);
        textDate = promptView.findViewById(R.id.textViewDate);
        textTime = promptView.findViewById(R.id.textViewTime);
        textViewCaption = promptView.findViewById(R.id.textCaption);

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);

        buttonSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateTimePicker.displayDatePicker(getFragmentManager());
            }
        });

        buttonSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Date currentTime = Calendar.getInstance().getTime();
                dateTimePicker.displayTimePicker(getFragmentManager());
            }
        });

        buttonPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                imagePicker = new ImagePicker(MainActivity.this);
                imagePicker.setImagePickerCallback(new ImagePickerCallback() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onImagesChosen(List<ChosenImage> list) {
                            photo = new File(list.get(0).getOriginalPath());
                            Bitmap myBitmap = BitmapFactory.decodeFile((photo.getAbsolutePath()));
                            imagePreview.setImageBitmap(myBitmap);

                            double width = myBitmap.getWidth();
                            double height = myBitmap.getHeight();

                            /*
                            if(((width/height) == 1.0)) {
                                AlertDialog.Builder builder;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    builder = new AlertDialog.Builder(view.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                                } else {
                                    builder = new AlertDialog.Builder(view.getContext());
                                }
                                builder.setTitle("Warning")
                                        .setMessage("The image you selected is not in proper Instagram format. This could create some problems when uploading." +
                                                " It is recommended to resize the image and then select it again. Thank you.")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // continue with delete
                                            }
                                        })
                                        .show();
                            }
                            */

                        System.out.println(myBitmap.getHeight());
                        System.out.println(myBitmap.getWidth());
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
                            ScheduleUploadPhoto scheduleUploadPhoto = new ScheduleUploadPhoto(getApplicationContext());
                            scheduleUploadPhoto.scheduleUpload(new File((photo.getPath())), caption, timeTillUpload);
                            Toast.makeText(getApplicationContext(), "Photo Scheduled", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.dismiss();
                    thread.start();
                    adapter.add(caption);
                    //alertDialogBuilder.dis

                }
            }
        });
        alertDialogBuilder.setView(promptView);
        dialog = alertDialogBuilder.show();
    }

    @NonNull
    private String fixFilePath(String badPath) {
        String[] split = photo.getAbsolutePath().split(":");
        return split[1].substring(1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                 //File write logic here
                 if(requestCode == Picker.PICK_IMAGE_DEVICE) {
                     imagePicker.submit(data);
                 }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            //Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }

}
