package com.silvia.english;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.silvia.english.databinding.ActivityListeningBinding;
import com.silvia.english.databinding.ActivityLoginBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    ApiServer api;
    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AndroidNetworking.initialize(this);
        api = new ApiServer();
        tinyDB = new TinyDB(this);


        binding.btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLogin();
            }
        });

        if (tinyDB.getBoolean("keyLogin")) {
            Intent intent = new Intent(LoginActivity.this, MeetingActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
    }

    private void getLogin() {
        AndroidNetworking.post(api.LOGIN)
                .addBodyParameter("siswa_nim", binding.edtNim.getText().toString())
                .addBodyParameter("siswa_password", binding.edtPass.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            int stat = response.getInt("status");
                            String message = response.getString("message");
                            Log.d("sukses", "code"+response);
                            if (stat == 1){
                                JSONObject data = response.getJSONObject("data");

                                String id = data.getString("siswa_id");
                                String nama = data.getString("siswa_nama");
                                String tgl = data.getString("siswa_tgl_lahir");
                                String notelp = data.getString("siswa_notelp");
                                String alamat = data.getString("siswa_alamat");
                                String id_kelas = data.getString("kelas_id");
                                String id_dosen = data.getString("dosen_id");
                                String nim = data.getString("siswa_nim");

                                tinyDB.putString("keyIdSiswa",id);
                                tinyDB.putString("keyNama",nama);
                                tinyDB.putString("keyTtl",tgl);
                                tinyDB.putString("keyNotelp",notelp);
                                tinyDB.putString("keyAlamat",alamat);
                                tinyDB.putString("keyIdKelas",id_kelas);
                                tinyDB.putString("keyIdDosen",id_dosen);
                                tinyDB.putString("keyNimSiswa",nim);
                                Log.e("idSiswa",id);

                                tinyDB.putBoolean("keyLogin",true);

                                Toast.makeText(LoginActivity.this, "Login Sukses", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(LoginActivity.this, MeetingActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            }
                            else {
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("eror", "code :"+anError);
                        Toast.makeText(LoginActivity.this, ""+anError, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}