package com.silvia.english;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.silvia.english.databinding.ActivityRecordBinding;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class RecordActivity extends AppCompatActivity {
    private ActivityRecordBinding binding;

    private MediaRecorder mRecorder;
    private MediaPlayer mPlayerRecord;
    private static String mFileName = null;
    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;
    private static final String LOG_TAG = "AudioRecording";

    //Listening
    MediaPlayer mmPlayer;
    MediaPlayer mPlayer;
    static int onTime = 0, startTime = 0, endTime = 0;
    Handler hdlr = new Handler();
    String audio1,audio2;
    ApiServer api;
    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AndroidNetworking.initialize(this);
        api = new ApiServer();
        tinyDB = new TinyDB(this);
         audio1 = tinyDB.getString("meeting_materi_audio1");
         audio2 = tinyDB.getString("meeting_materi_audio2");
        mPlayer = MediaPlayer.create(getApplication(), Uri.parse("http://192.168.100.15/english/audio/"+audio1));
        mmPlayer = MediaPlayer.create(getApplication(), Uri.parse("http://192.168.100.15/english/audio/"+audio2));

        //Listening
        binding.A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mPlayer.isPlaying()){
                    mPlayer.start();
                    binding.A.setBackgroundColor(getResources().getColor(R.color.purple_200));
                } else{
                    mPlayer.pause();
                    binding.A.setBackgroundColor(getResources().getColor(R.color.gray));

                }
            }
        });

        binding.B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mmPlayer.isPlaying()){
                    mmPlayer.start();
                             binding.B.setBackgroundColor(getResources().getColor(R.color.purple_200));
                } else{
                    mmPlayer.pause();
                            binding.B.setBackgroundColor(getResources().getColor(R.color.gray));
                }
            }
        });


        endTime = mPlayer.getDuration();
        startTime = mPlayer.getCurrentPosition();
        if (onTime == 0 ){
            binding.seekSongProgressbar.setMax(endTime);
            onTime = 1;
        }

        endTime = mmPlayer.getDuration();
        startTime = mmPlayer.getCurrentPosition();
        if (onTime == 0 ){
            binding.seekSongProgressbar.setMax(endTime);
            onTime = 1;
        }


        binding.tvSongTotalDuration.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes(endTime),
                TimeUnit.MILLISECONDS.toSeconds(endTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(endTime))));

        binding.tvSongCurrentDuration.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes(startTime),
                TimeUnit.MILLISECONDS.toSeconds(startTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(startTime))));

        binding.seekSongProgressbar.setProgress(startTime);

        binding.seekSongProgressbar.postDelayed(UpdateSongTime, 100);

        //Record
        binding.btnStop.setBackgroundColor(getResources().getColor(R.color.gray));
        binding.btnPlay.setBackgroundColor(getResources().getColor(R.color.gray));
        binding.btnstopPlay.setBackgroundColor(getResources().getColor(R.color.gray));

        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
//        mFileName = "http://localhost/english/audio";
        mFileName += "/AudioRecording.mp3";

        binding.btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckPermissions()) {
                    binding.mic.setVisibility(View.VISIBLE);
                    binding.btnStop.setBackgroundColor(getResources().getColor(R.color.purple_200));
                    binding.btnRecord.setBackgroundColor(getResources().getColor(R.color.gray));
                    binding.btnPlay.setBackgroundColor(getResources().getColor(R.color.gray));
                    binding.btnstopPlay.setBackgroundColor(getResources().getColor(R.color.gray));
                    mRecorder = new MediaRecorder();
                    mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    mRecorder.setOutputFile(mFileName);
                    try {
                        mRecorder.prepare();
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "prepare() failed");
                    }
                    mRecorder.start();
                    Toast.makeText(getApplicationContext(), "Recording Started", Toast.LENGTH_LONG).show();
                }
                else
                {
                    RequestPermissions();
                }
            }
        });
        binding.btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.mic.setVisibility(View.GONE);
                binding.btnStop.setBackgroundColor(getResources().getColor(R.color.gray));
                binding.btnRecord.setBackgroundColor(getResources().getColor(R.color.purple_200));
                binding.btnPlay.setBackgroundColor(getResources().getColor(R.color.purple_200));
                binding.btnstopPlay.setBackgroundColor(getResources().getColor(R.color.purple_200));
                mRecorder.stop();
                mRecorder.release();
                mRecorder = null;
                Toast.makeText(getApplicationContext(), "Recording Stopped", Toast.LENGTH_LONG).show();
            }
        });
        binding.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.mic.setVisibility(View.GONE);
                binding.btnStop.setBackgroundColor(getResources().getColor(R.color.gray));
                binding.btnRecord.setBackgroundColor(getResources().getColor(R.color.purple_200));
                binding.btnPlay.setBackgroundColor(getResources().getColor(R.color.gray));
                binding.btnstopPlay.setBackgroundColor(getResources().getColor(R.color.purple_200));
                mPlayerRecord = new MediaPlayer();
                try {
                    mPlayerRecord.setDataSource(mFileName);
                    mPlayerRecord.prepare();
                    mPlayerRecord.start();
                    Toast.makeText(getApplicationContext(), "Recording Started Playing", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "prepare() failed");
                }
            }
        });
        binding.btnstopPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayerRecord.release();
                mPlayerRecord = null;
                binding.btnStop.setBackgroundColor(getResources().getColor(R.color.gray));
                binding.btnRecord.setBackgroundColor(getResources().getColor(R.color.purple_200));
                binding.btnPlay.setBackgroundColor(getResources().getColor(R.color.purple_200));
                binding.btnstopPlay.setBackgroundColor(getResources().getColor(R.color.gray));
                Toast.makeText(getApplicationContext(),"Playing Audio Stopped", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Runnable UpdateSongTime = new Runnable() {
        @SuppressLint("DefaultLocale")
        @Override
        public void run() {
            startTime = mPlayer.getCurrentPosition();
            startTime = mmPlayer.getCurrentPosition();
            binding.tvSongCurrentDuration.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes(startTime),
                    TimeUnit.MILLISECONDS.toSeconds(startTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(startTime))
            ));
            binding.seekSongProgressbar.setProgress(startTime);
            hdlr.postDelayed(this, 100);
        }
    };

    //Record
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_AUDIO_PERMISSION_CODE:
                if (grantResults.length > 0) {
                    boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean permissionToStore = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (permissionToRecord && permissionToStore) {
                        Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    private boolean CheckPermissions() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void RequestPermissions() {
        ActivityCompat.requestPermissions(RecordActivity.this, new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);
    }





}