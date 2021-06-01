package com.example.logcatdemo.welpaper;

import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.IOException;

public class fullscreenItems extends AppCompatActivity {

    String orginalUrl= "";
    PhotoView photoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_fullscreen_items);


        Intent intent  = getIntent ();
        orginalUrl =intent.getStringExtra ("original");
        photoView = findViewById (R.id.photoview);
        Glide.with (this).load (orginalUrl).into (photoView);
    }

    public void btn_wellpaper(View view) {

        WallpaperManager wallpaperManager =  WallpaperManager.getInstance (this);
        Bitmap bitmap = ((BitmapDrawable) photoView.getDrawable ()).getBitmap ();
        try {
            wallpaperManager.setBitmap (bitmap);
            Toast.makeText (this, "Wellpaper Set", Toast.LENGTH_SHORT).show ();
        } catch (IOException e) {
            e.printStackTrace ();
        }


    }


    public void dounlode(View view) {
        DownloadManager downloadManager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse (orginalUrl);
        DownloadManager.Request request = new DownloadManager.Request (uri);
        request.setNotificationVisibility (DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        downloadManager.enqueue (request);
        Toast.makeText (this, "Dounlodeing start", Toast.LENGTH_SHORT).show ();
    }
}