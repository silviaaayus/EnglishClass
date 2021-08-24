package com.silvia.english;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.silvia.english.databinding.ActivityListeningBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ListeningActivity extends AppCompatActivity {

    MediaPlayer mPlayer;
    static int onTime = 0, startTime = 0, endTime = 0;
    Handler hdlr = new Handler();
    String id_meeting, audio1,materi;
    ApiServer api;
    TinyDB tinyDB;

    private ActivityListeningBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListeningBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AndroidNetworking.initialize(this);
        api = new ApiServer();
        tinyDB = new TinyDB(this);


        id_meeting = tinyDB.getString("meeting_id");
        audio1 = tinyDB.getString("meeting_materi_audio1");
        materi = tinyDB.getString("meeting_materi_teks");
        Log.e("id",id_meeting);

//        String audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3";

//        mPlayer = new MediaPlayer();
        mPlayer = MediaPlayer.create(getApplication(), Uri.parse("http://192.168.100.15/english/audio/"+audio1));


        endTime = mPlayer.getDuration();
        startTime = mPlayer.getCurrentPosition();
        if (onTime == 0 ){
            binding.seekSongProgressbar.setMax(endTime);
            onTime = 1;
        }

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListeningActivity.this, QuizActivity.class);
                i.putExtra("meeting_id",id_meeting);
                startActivity(i);
            }
        });

        binding.txtMateri.setText(Html.fromHtml(materi));

        binding.tvSongTotalDuration.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes(endTime),
                TimeUnit.MILLISECONDS.toSeconds(endTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(endTime))));

        binding.tvSongCurrentDuration.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes(startTime),
                TimeUnit.MILLISECONDS.toSeconds(startTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(startTime))));

        binding.seekSongProgressbar.setProgress(startTime);

        binding.seekSongProgressbar.postDelayed(UpdateSongTime, 100);

        binding.btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mPlayer.isPlaying()){
                    mPlayer.start();
                    binding.btPlay.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
                } else{
                    mPlayer.pause();
                    binding.btPlay.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                }
            }
        });

    }



    private Runnable UpdateSongTime = new Runnable() {
        @SuppressLint("DefaultLocale")
        @Override
        public void run() {
            startTime = mPlayer.getCurrentPosition();
            binding.tvSongCurrentDuration.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes(startTime),
                    TimeUnit.MILLISECONDS.toSeconds(startTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(startTime))
            ));
            binding.seekSongProgressbar.setProgress(startTime);
            hdlr.postDelayed(this, 100);
        }
    };





}