package com.silvia.english;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.ColorSpace;
import android.os.Bundle;
import android.service.voice.AlwaysOnHotwordDetector;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.silvia.english.adapter.Adapter_History;
import com.silvia.english.databinding.ActivityHistoryBinding;
import com.silvia.english.model.Model_History;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private ActivityHistoryBinding binding;
    ApiServer api;
    TinyDB tinyDB;
    String id;

    private List<Model_History> dataHistory;
    private RecyclerView recycler_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AndroidNetworking.initialize(this);
        api = new ApiServer();
        tinyDB = new TinyDB(this);
        id = tinyDB.getString("keyIdSiswa");


        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.recyclerHistory.setHasFixedSize(true);
        binding.recyclerHistory.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        dataHistory = new ArrayList<>();
        getHistory();
    }

    public void getHistory(){
        Log.e("history",api.HISTORY+id);
        AndroidNetworking.get(api.HISTORY+id)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            if (response.getString("status").equalsIgnoreCase("sukses")) {

                                JSONArray res = response.getJSONArray("res");
                                Gson gson = new Gson();
                                dataHistory.clear();
                                for (int i = 0; i < res.length(); i++) {
                                    JSONObject data = res.getJSONObject(i);
                                     Model_History Isi = gson.fromJson(data + "", Model_History.class);
                                    dataHistory.add(Isi);
                                }
                                Adapter_History adapter = new Adapter_History(dataHistory);
                                binding.recyclerHistory.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }else {

                                Toast.makeText(HistoryActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
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