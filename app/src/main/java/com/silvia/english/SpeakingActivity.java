package com.silvia.english;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.silvia.english.databinding.ActivitySpeakingBinding;

import java.util.concurrent.TimeUnit;

public class SpeakingActivity extends AppCompatActivity {
    private ActivitySpeakingBinding binding;

    MediaPlayer mPlayerA;
    MediaPlayer mPlayerB;
    static int onTimeA = 0, startTimeA = 0, endTimeA = 0;
    static int onTimeB = 0, startTimeB = 0, endTimeB = 0;
    Handler hdlr = new Handler();
    String audio1,audio2, materi;
    ApiServer api;
    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpeakingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AndroidNetworking.initialize(this);
        api = new ApiServer();
        tinyDB = new TinyDB(this);
        audio1 = tinyDB.getString("meeting_materi_audio1");
        audio2 = tinyDB.getString("meeting_materi_audio2");
        materi = tinyDB.getString("meeting_materi_teks");
        mPlayerA = MediaPlayer.create(getApplication(), Uri.parse(api.SUARA+audio1));
        mPlayerB = MediaPlayer.create(getApplication(), Uri.parse(api.SUARA+audio2));

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SpeakingActivity.this,RecordActivity.class);
                startActivity(i);
            }
        });

        binding.A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mPlayerA.isPlaying()){
                    mPlayerA.start();
                    binding.imgA.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
                } else{
                    mPlayerA.pause();
                    binding.imgA.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                }
            }
        });

        binding.B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mPlayerB.isPlaying()){
                    mPlayerB.start();
                    binding.imgB.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
                } else{
                    mPlayerB.pause();
                    binding.imgB.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                }
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


//        endTimeA = mPlayerA.getDuration();
//        startTimeA = mPlayerA.getCurrentPosition();
//
//        if (onTimeA == 0 ){
//           onTimeA = 1;
//      }
//
//        endTimeB = mPlayerB.getDuration();
//        startTimeB = mPlayerB.getCurrentPosition();
//        if (onTimeB == 0 ){
//            onTimeB = 1;
//        }



        binding.txtMateri.setText(Html.fromHtml(materi));



    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mPlayerA.isPlaying()){
            mPlayerA.pause();
            binding.imgA.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
        }

        if (mPlayerB.isPlaying()){
            mPlayerB.pause();
            binding.imgB.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayerA.isPlaying()){
            mPlayerA.release();
            binding.imgA.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
        }

        if (mPlayerB.isPlaying()){

            mPlayerB.release();
            binding.imgB.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
        }
    }


}