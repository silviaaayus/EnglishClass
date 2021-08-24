package com.silvia.english;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.silvia.english.adapter.Adaptr_Meeting;
import com.silvia.english.databinding.ActivityMeetingBinding;
import com.silvia.english.model.Model_Meeting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MeetingActivity extends AppCompatActivity {
    ApiServer api;
    private ActivityMeetingBinding binding;
    private List<Model_Meeting> dataMeeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMeetingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AndroidNetworking.initialize(this);
        api = new ApiServer();

        binding.recyclerMeeting.setHasFixedSize(true);
        binding.recyclerMeeting.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        dataMeeting = new ArrayList<>();
        getMeeting();

    }

    public void getMeeting(){
        Log.d("api",api.MEETING);
        AndroidNetworking.get(api.MEETING)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            if (response.getString("status").equalsIgnoreCase("sukses")) {
                                JSONArray res = response.getJSONArray("res");
                                Gson gson = new Gson();
                                dataMeeting.clear();
                                for (int i = 0; i < res.length(); i++) {
                                    JSONObject data = res.getJSONObject(i);
                                    Model_Meeting Isi = gson.fromJson(data + "", Model_Meeting.class);
                                    dataMeeting.add(Isi);

                                }
                                Adaptr_Meeting adapter = new Adaptr_Meeting(dataMeeting);
                                binding.recyclerMeeting.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }else {
                                Toast.makeText(MeetingActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("tampil menu","response:"+anError);
                    }
                });

    }
}