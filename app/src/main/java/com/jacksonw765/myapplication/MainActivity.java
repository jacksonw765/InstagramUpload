package com.jacksonw765.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.MediaStore.Video.Media;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kbeanie.multipicker.api.ImagePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.VideoPicker;
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback;
import com.kbeanie.multipicker.api.callbacks.VideoPickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenImage;
import com.kbeanie.multipicker.api.entity.ChosenVideo;

import java.io.File;
import java.io.IOException;
import java.util.List;

import dev.niekirk.com.instagram4android.Instagram4Android;
import dev.niekirk.com.instagram4android.requests.InstagramUploadPhotoRequest;
import dev.niekirk.com.instagram4android.requests.InstagramUploadVideoRequest;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private VideoPicker videoPicker;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        videoPicker = new VideoPicker(MainActivity.this);
                        videoPicker.setVideoPickerCallback(new VideoPickerCallback() {
                            @Override
                            public void onVideosChosen(List<ChosenVideo> list) {
                                Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                                final ChosenVideo chosenVideo = list.get(0);

                                Thread t = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        /*
                                        UploadPhoto uploadPhoto = new UploadPhoto(null, null);
                                        uploadPhoto.upload(chosenVideo.getOriginalPath());
                                        System.out.println("picked");
                                        */
                                        Instagram4Android instagram = Instagram4Android.builder().username("jw.optical").password("rascalm123").build();
                                        instagram.setup();
                                        try {
                                            instagram.login();
                                            instagram.sendRequest(new InstagramUploadVideoRequest(
                                                    new File(chosenVideo.getOriginalPath()),
                                                    "Posted with Instagram4j @jacksonw765, how cool is that?"));
                                            System.out.println("Done!");
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }); t.start();

                            }


                            @Override
                            public void onError(String s) {
                                System.out.println(s);
                            }
                        });

                        /*
                        ImagePicker imagePicker = new ImagePicker(MainActivity.this);
                        imagePicker.setImagePickerCallback(new ImagePickerCallback(){
                            @Override
                            public void onImagesChosen(List<ChosenImage> images) {
                                ChosenImage chosenImage = images.get(0);

                                UploadPhoto uploadPhoto = new UploadPhoto(null, null);
                                uploadPhoto.upload(chosenImage.getThumbnailPath());
                                System.out.println("picked");
                            }

                            @Override
                            public void onError(String message) {
                                // Do error handling
                            }
                        }
                        );
                        imagePicker.pickImage();




                        System.out.println("***BUILDING***");
                        Instagram4Android instagram = Instagram4Android.builder().username("jw.optical").password("rascalm123").build();
                        instagram.setup();
                        try {

                            instagram.login();
                            instagram.sendRequest(new InstagramUploadVideoRequest(
                                    new File("C:\\Users\\jacks\\Google Drive\\McEw_Photos\\433\\test.mp4"),
                                    "Posted with Instagram4j @jacksonw765, how cool is that?"));
                            System.out.println("Done!");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        */
                        videoPicker.pickVideo();
                    }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         {
             ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);

                 //File write logic here
                 if(requestCode == Picker.PICK_VIDEO_DEVICE) {
                     videoPicker.submit(data);
                 }


        }
    }
    public class MakeConnection extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            System.out.println("***BUILDING***");
            Instagram4Android instagram = Instagram4Android.builder().username("jw.optical").password("rascalm123").build();
            instagram.setup();
            try {
                instagram.login();
                instagram.sendRequest(new InstagramUploadPhotoRequest(
                        new File("C:\\Users\\jacks\\Google Drive\\McEw_Photos\\gilesCustom\\test.mp4"),
                        "Posted with Instagram4j @jacksonw765, how cool is that?"));
                System.out.println("Done!");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}