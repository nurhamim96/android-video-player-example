package com.tutorial.videoplayerinandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {
    private VideoView videoView;
    ArrayList<Integer> videoList = new ArrayList<>();
    int currvideo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         videoView = findViewById(R.id.videoView);
         videoView.setMediaController(new MediaController(this));
         videoView.setOnCompletionListener(this);

         // video name should be in lower case alphabet
        videoList.add(R.raw.sample);
        videoList.add(R.raw.sample1);
        setVideo(videoList.get(0));

    }

    public void setVideo(int id) {
        String uriPath = "android.resource://" + getPackageName() + "/" + id;
        Uri uri = Uri.parse(uriPath);
        videoView.setVideoURI(uri);
        videoView.start();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        AlertDialog.Builder obj = new AlertDialog.Builder(this);
        obj.setTitle("Playback Finished!");
        obj.setIcon(R.mipmap.ic_launcher);
        MyListener m = new MyListener();
        obj.setPositiveButton("Replay", m);
        obj.setNegativeButton("Next", m);
        obj.setMessage("Want to replay or play next video?");
        obj.show();
    }

    class MyListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if (i == -1) {
                videoView.seekTo(0);
                videoView.start();
            } else {
                ++currvideo;
                if (currvideo == videoList.size())
                    currvideo = 0;
                setVideo(videoList.get(currvideo));
            }
        }
    }
}

