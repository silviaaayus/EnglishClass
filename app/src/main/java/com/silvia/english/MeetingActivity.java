package com.silvia.english;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.silvia.english.databinding.ActivityMeetingBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MeetingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ApiServer api;
    private ActivityMeetingBinding binding;
    TinyDB tinyDB;
    String id_dosen,id_meeting,nama_meeting,nama,nim;

    private ActionBarDrawerToggle mToggle;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMeetingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AndroidNetworking.initialize(this);
        api = new ApiServer();
        tinyDB = new TinyDB(this);
        id_dosen = tinyDB.getString("keyIdDosen");
        nama = tinyDB.getString("keyNama");
        nim = tinyDB.getString("keyNimSiswa");
        Log.e("nama",nama);

        binding.user.setText("Hi, "+nama);


        binding.nav.namaUserSide.setText(nama);
        binding.nav.nohpUser.setText(nim);

        binding.nav.btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MeetingActivity.this,ChangePassword.class);
                startActivity(i);
            }
        });

        binding.nav.btnHistori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MeetingActivity.this,HistoryActivity.class);
                startActivity(i);
            }
        });



        binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MeetingActivity.this, PilihanActivity.class);
                i.putExtra("meeting_id", id_meeting);
                i.putExtra("meeting_nama", nama_meeting);
                startActivity(i);
            }
        });

        binding.nav.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tinyDB.clear();
                Intent intent = new Intent(MeetingActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        binding.navView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        binding.btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        mToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        // tambahkan mToggle ke drawer_layout sebagai pengendali open dan close drawer
        binding.drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();



        getMeeting();

    }



    public void getMeeting() {
        Log.e("api", api.MEETING + id_dosen);
        AndroidNetworking.get(api.MEETING + id_dosen)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("totalJalan", "data : " + response);
                            if (response.getString("status").equalsIgnoreCase("sukses")) {
                                JSONArray data = response.getJSONArray("res");
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject res = data.getJSONObject(i);
                                    id_meeting = res.getString("meeting_id");
                                    nama_meeting = res.getString("meeting_nama");

                                    binding.dosen.setText(res.getString("dosen_nama"));
                                    binding.meeting.setText(res.getString("meeting_nama"));
                                    binding.user.setText(res.getString("siswa_nama"));

                                }

                            } else {
                                Log.d("listening", "kosong : " + response);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("listening", "eror : " + anError);
                    }
                });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}