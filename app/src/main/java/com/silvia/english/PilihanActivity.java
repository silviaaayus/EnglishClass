package com.silvia.english;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.silvia.english.adapter.Adaptr_Meeting;
import com.silvia.english.databinding.ActivityPilihanBinding;
import com.silvia.english.model.Model_Meeting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;

public class PilihanActivity extends AppCompatActivity {
    private ActivityPilihanBinding binding;
    ApiServer api;
    String id_meeting,audio1,materi,audio2;
    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPilihanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        tinyDB = new TinyDB(this);
        Intent i = getIntent();
        id_meeting = i.getStringExtra("meeting_id");


        AndroidNetworking.initialize(this);
        api = new ApiServer();
        binding.listening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListening();
            }

        });
        binding.speaking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              getRecord();
            }

        });





    }

    private void getListening() {
        Log.e("api", api.LISTENING+id_meeting);
        AndroidNetworking.get(api.LISTENING+id_meeting)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("totalJalan", "data : "+response);
                            if (response.getString("status").equalsIgnoreCase("sukses")){
                                JSONArray data = response.getJSONArray("res");
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject res = data.getJSONObject(i);
                                     materi = res.getString("meeting_materi_teks");
                                     audio1  = res.getString("meeting_materi_audio1");


                                    tinyDB.putString("meeting_id",id_meeting);
                                    tinyDB.putString("meeting_materi_audio1",audio1);
                                    tinyDB.putString("meeting_materi_teks",materi);



                                    Intent intent = new Intent(PilihanActivity.this,ListeningActivity.class);
                                    startActivity(intent);


                                }

                            }else {
                                Log.d("listening", "kosong : "+response);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("listening", "eror : "+anError);
                    }
                });
    }

    private void getRecord() {
        Log.e("api", api.SPEAKING+id_meeting);
        AndroidNetworking.get(api.SPEAKING+id_meeting)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("totalJalan", "data : "+response);
                            if (response.getString("status").equalsIgnoreCase("sukses")){
                                JSONArray data = response.getJSONArray("res");
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject res = data.getJSONObject(i);
                                    materi = res.getString("meeting_materi_teks");
                                    audio1  = res.getString("meeting_materi_audio1");
                                    audio2 = res.getString("meeting_materi_audio2");

                                    tinyDB.putString("meeting_id",id_meeting);
                                    tinyDB.putString("meeting_materi_audio1",audio1);
                                    tinyDB.putString("meeting_materi_audio2",audio2);
                                    tinyDB.putString("meeting_materi_teks",materi);



                                    Intent intent = new Intent(PilihanActivity.this,RecordActivity.class);
                                    startActivity(intent);


                                }

                            }else {
                                Log.d("listening", "kosong : "+response);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("listening", "eror : "+anError);
                    }
                });
    }




}