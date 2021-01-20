package com.example.myrecorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    // drawable->New->Vector Asset->Clip Art
    // need to add RECORD_AUDIO, READ_EXTERNAL_STORAGE and WRITE_EXTERNAL_STORAGE permission in manifest
    TextView tv;
    ImageButton playBtn, recordBtn, stopBtn;
    MediaRecorder mediaRecorder;
    String filename="recording.3gp";
    String filepath= "data/data/com.example.myrecorder/"+filename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playBtn=findViewById(R.id.playBtn);
        stopBtn=findViewById(R.id.stopBtn);
        recordBtn=findViewById(R.id.recordBtn);
        tv=findViewById(R.id.textView);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playFile();
            }
        });
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopFile();
            }
        });
        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordFile();
            }
        });
        

    }

    private void recordFile() {
        mediaRecorder=new MediaRecorder();
        try {
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},0);
            }
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            mediaRecorder.setOutputFile(filepath);
            mediaRecorder.prepare();
            mediaRecorder.start();
            tv.setText("Recording ...");
        } catch (IOException e) {
            e.printStackTrace();
        }
        stopBtn.setVisibility(View.VISIBLE);
    }

    private void stopFile() {
        mediaRecorder.stop();
        mediaRecorder.release();
        stopBtn.setVisibility(View.INVISIBLE); //hide the stopBtn
        tv.setText("Recording stopped ...");
    }

    private void playFile() {
        MediaPlayer mediaPlayer=new MediaPlayer();
        try {
            mediaPlayer.setDataSource(filepath);
            mediaPlayer.prepare();
            mediaPlayer.start();
            tv.setText("Playing the recording ...");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}