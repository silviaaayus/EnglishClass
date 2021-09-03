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
import com.silvia.english.databinding.ActivityChangePasswordBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePassword extends AppCompatActivity {
    private ActivityChangePasswordBinding binding;
    ApiServer api;
    TinyDB tinyDB;
    String id_siswa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        api = new ApiServer();
        AndroidNetworking.initialize(this);
        tinyDB = new TinyDB(this);
        id_siswa = tinyDB.getString("keyIdSiswa");


        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.newPass.getText().toString().equalsIgnoreCase(binding.confirm.getText().toString())) {
                    editPassword();

                } else {
                    Toast.makeText(ChangePassword.this, "Password Tidak Sama", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    public void editPassword(){
        Log.e("api",api.CHANGE_PASSWORD);
        AndroidNetworking.post(api.CHANGE_PASSWORD)
                .addBodyParameter("siswa_id", id_siswa)
                .addBodyParameter("siswa_password", binding.confirm.getText().toString())

                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String respon = response.getString("response");
                            if (respon.equalsIgnoreCase("Sukses")){

                                Intent i = new Intent(ChangePassword.this,LoginActivity.class);
                                startActivity(i);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Kesalahan update, Kode 2"
                                ,Toast.LENGTH_LONG).show();

                    }


                });
    }
}