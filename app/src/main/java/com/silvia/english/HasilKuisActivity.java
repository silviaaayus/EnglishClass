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
import com.silvia.english.databinding.ActivityHasilKuisBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class HasilKuisActivity extends AppCompatActivity {
    private ActivityHasilKuisBinding binding;
    ApiServer api;
    String id_materi, id_siswa, skorPilGan;
    TinyDB tinyDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHasilKuisBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AndroidNetworking.initialize(this);
        api = new ApiServer();
        tinyDB = new TinyDB(this);
        id_materi = tinyDB.getString("meeting_materi_id");
        id_siswa = tinyDB.getString("keyIdSiswa");
        Log.e("idsiswa",id_siswa);
        setSkor();

        binding.btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HasilKuisActivity.this,MeetingActivity.class);
                startActivity(i);
            }
        });
    }

    public void setSkor(){
        String activity = getIntent().getStringExtra("activity");
        skorPilGan = getIntent().getStringExtra("skorAkhir");


        if(activity.equals("PilihanGanda")){
            String motto = null;
            if (Integer.valueOf(skorPilGan) <= 50){
                motto = " Semangat Kamu Pasti Bisa";
            }else if (Integer.valueOf(skorPilGan)<=70 && Integer.valueOf(skorPilGan)>=51){
                motto = "Hebat, Tingkatkan Lagi";
            }else if (Integer.valueOf(skorPilGan)<=100 && Integer.valueOf(skorPilGan)>=71){
                motto="Selamat, Pertahankan Skormu";
            }
            binding.tvSkorAkhir.setText("Skor Kamu : "+skorPilGan +"\n  "+motto);
            inputNilai();
        }
    }

    public void onBackPressed(){
        Intent i = new Intent(HasilKuisActivity.this,MeetingActivity.class);
        startActivity(i);

    }

    private void inputNilai() {
        AndroidNetworking.post(api.SKOR)
                .addBodyParameter("siswa_id",id_siswa)
                .addBodyParameter("meeting_materi_id",id_materi)
                .addBodyParameter("skor", skorPilGan)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            String message = response.getString("message");
                            Log.d("sukses", "code"+response);
                            if (code == 1){
                                Toast.makeText(HasilKuisActivity.this, "Nilai Tersimpan", Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Toast.makeText(HasilKuisActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("eror", "code :"+anError);
                        Toast.makeText(HasilKuisActivity.this, ""+anError, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}