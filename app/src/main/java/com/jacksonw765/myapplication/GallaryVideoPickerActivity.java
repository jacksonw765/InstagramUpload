package com.jacksonw765.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import java.io.IOException;

/**
 * Created by jacks on 1/5/2018.
 */

class GalleryVideoPickerActivity extends Activity {
    private static final int SELECT_VIDEO = 1;

    private String selectedVideoPath;

    @ Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, SELECT_VIDEO);
    }

    @ Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_VIDEO) {
                selectedVideoPath = getPath(data.getData());
                try {
                    if(selectedVideoPath == null) {
                        //Log.e("selected video path = nu");
                        finish();
                    } else {
                        /**
                         * try to do something there
                         * selectedVideoPath is path to the selected video
                         */
                    }
                } catch (Exception e) {
                    //#debug
                    e.printStackTrace();
                }
            }
        }
        finish();
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if(cursor!=null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        else return null;
    }
}
