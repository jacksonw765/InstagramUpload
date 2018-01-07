package com.jacksonw765.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.kbeanie.multipicker.api.ImagePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenImage;

import java.io.File;
import java.io.IOException;
import java.util.List;

import dev.niekirk.com.instagram4android.Instagram4Android;
import dev.niekirk.com.instagram4android.requests.InstagramUploadPhotoRequest;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private ImagePicker imagePicker;
    private ImageView imageView;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //imageView = findViewById(R.id.im)

        button = findViewById(R.id.buttonNewPhoto);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                                        ScheduleUploadPhoto scheduleUploadPhoto = new ScheduleUploadPhoto(getApplicationContext());
                                        scheduleUploadPhoto.scheduleUpload(new File(file.getPath()), "this is a test",1);
                                    }
                                });
                                t.start();
                            }
                            @Override
                            public void onError(String s) {
                                System.out.println(s);
                            }
                        });


                        imagePicker.pickImage();
                    }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         {
             //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);

                 //File write logic here
                 if(requestCode == Picker.PICK_IMAGE_DEVICE) {
                     imagePicker.submit(data);
                 }


        }
    }
}
