package com.silvia.english;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.silvia.english.databinding.ActivityPilihanBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PilihanActivity extends AppCompatActivity {
    private ActivityPilihanBinding binding;
    ApiServer api;
    String id_meeting,audio1,materi,audio2,id_materi,nama_meeting,meeting,id_materi2;
    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPilihanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        tinyDB = new TinyDB(this);
        Intent i = getIntent();
        id_meeting = i.getStringExtra("meeting_id");
        nama_meeting = i.getStringExtra("meeting_nama");

        binding.namaMeeting.setText(nama_meeting);


        AndroidNetworking.initialize(this);
        api = new ApiServer();
         binding.learningListening.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 getListening();
             }
         });
         binding.speakingListening.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(PilihanActivity.this,SpeakingActivity.class);
                 getSpeaking();
                 startActivity(intent);

             }
         });
         binding.back.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
             }
         });
         binding.listeningQuestion.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i = new Intent(PilihanActivity.this,QuizActivity.class);
                 getListening();
                 startActivity(i);

             }
         });
         binding.speakingQuestion.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i = new Intent(PilihanActivity.this,RecordActivity.class);
                 getSpeaking();
                 startActivity(i);

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
                                     id_materi  = res.getString("meeting_materi_id");
                                     meeting = res.getString("meeting_nama");

                                    tinyDB.putString("meeting_materi_id",id_materi);
                                    tinyDB.putString("meeting_id",id_meeting);
                                    tinyDB.putString("meeting_materi_audio1",audio1);
                                    tinyDB.putString("meeting_materi_teks",materi);
                                    tinyDB.putString("meeting_nama",meeting);

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

    private void getSpeaking() {
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
                                    id_materi2 = res.getString("meeting_materi_id");


                                    tinyDB.putString("meeting_id",id_meeting);
                                    tinyDB.putString("meeting_materi_id2",id_materi2);
                                    tinyDB.putString("meeting_materi_audio1",audio1);
                                    tinyDB.putString("meeting_materi_audio2",audio2);
                                    tinyDB.putString("meeting_materi_teks",materi);
                                    Log.e("materiid",id_materi2);


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