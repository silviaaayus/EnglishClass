package com.silvia.english;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.silvia.english.databinding.ActivityQuizBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {
    private ActivityQuizBinding binding;
    TinyDB tinyDB;
    ApiServer api;

    int skor=0;
    int arr; //untuk menampung nilai panjang array
    int x;
    String kunci, id_meeting,id_siswa;
    int panjang;


    ArrayList<String> arraySoal = new ArrayList<>();
    ArrayList<ArrayList<String>> arrayJawaban = new ArrayList<>();
    ArrayList<String> arrayKunci = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        AndroidNetworking.initialize(this);
        api = new ApiServer();
        tinyDB = new TinyDB(this);


        id_meeting = tinyDB.getString("meeting_id");
        id_siswa = tinyDB.getString("keyIdSiswa");
        Log.e("id",id_meeting);
        Log.e("id_siswa",id_siswa);

        binding.tvSkor.setText(""+skor);
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                cekJawaban();
            }
        });
        ambilData();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setKonten() {
        binding.rgPilihanJawaban.clearCheck();
        arr = arraySoal.size();
        if (x >= arr) { //jika nilai x melebihi nilai arr(panjang array) maka akan pindah activity (kuis selesai)
            //menjadikan skor menjadi string
            String jumlahSkor = String.valueOf(skor);
            Log.e("nilai", "" + jumlahSkor);
            Intent i = new Intent(QuizActivity.this, HasilKuisActivity.class);

            i.putExtra("activity", "PilihanGanda");
            i.putExtra("skorAkhir", jumlahSkor);


            startActivity(i);
        } else {
            //setting text dengan mengambil text dari method getter di kelas SoalPilihanGanda

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.tvSoal.setText(Html.fromHtml(arraySoal.get(x), Html.FROM_HTML_MODE_COMPACT));
                binding.rbPilihanJawaban1.setText(Html.fromHtml(arrayJawaban.get(x).get(0), Html.FROM_HTML_MODE_COMPACT));
                binding.rbPilihanJawaban2.setText(Html.fromHtml(arrayJawaban.get(x).get(1), Html.FROM_HTML_MODE_COMPACT));
                binding.rbPilihanJawaban3.setText(Html.fromHtml(arrayJawaban.get(x).get(2), Html.FROM_HTML_MODE_COMPACT));
                binding.rbPilihanJawaban4.setText(Html.fromHtml(arrayJawaban.get(x).get(3), Html.FROM_HTML_MODE_COMPACT));
                binding.rbPilihanJawaban5.setText(Html.fromHtml(arrayJawaban.get(x).get(3), Html.FROM_HTML_MODE_COMPACT));
                kunci = Html.fromHtml(arrayKunci.get(x), Html.FROM_HTML_MODE_COMPACT).toString();
            } else {
                binding.tvSoal.setText(Html.fromHtml(arraySoal.get(x)));
                binding.rbPilihanJawaban1.setText(Html.fromHtml(arrayJawaban.get(x).get(0)));
                binding.rbPilihanJawaban2.setText(Html.fromHtml(arrayJawaban.get(x).get(1)));
                binding.rbPilihanJawaban3.setText(Html.fromHtml(arrayJawaban.get(x).get(2)));
                binding.rbPilihanJawaban4.setText(Html.fromHtml(arrayJawaban.get(x).get(3)));
                binding.rbPilihanJawaban5.setText(Html.fromHtml(arrayJawaban.get(x).get(3)));
                kunci = String.valueOf(Html.fromHtml(arrayKunci.get(x)));
            }


        }
        x++;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void cekJawaban() {
        if (binding.rbPilihanJawaban1.isChecked()) { //jika radio button 1 diklik
            //jika text yang tertulis di radio button tsb = nilai dari var jawaban
            if (binding.rbPilihanJawaban1.getText().toString().equals(kunci)) {
                skor = skor + (100/panjang);
                Log.e("skor",""+skor);
                binding.tvSkor.setText("" + skor);    //mtvSkor diset nilainya = var skor
                Toast.makeText(this, "Jawaban Benar", Toast.LENGTH_SHORT).show(); //keluar notifikasi "Jawaban Benar"
                setKonten(); //update next konten
            } else {
                binding.tvSkor.setText("" + skor);
                Toast.makeText(this, "Jawaban Salah", Toast.LENGTH_SHORT).show();
                setKonten();
            }
        } else if (binding.rbPilihanJawaban2.isChecked()) {
            //jika text yang tertulis di radio button tsb = nilai dari var jawaban
            if (binding.rbPilihanJawaban2.getText().toString().equals(kunci)) {
                skor = skor + (100/panjang);
                binding.tvSkor.setText("" + skor);    //mtvSkor diset nilainya = var skor
                Toast.makeText(this, "Jawaban Benar", Toast.LENGTH_SHORT).show(); //keluar notifikasi "Jawaban Benar"
                setKonten(); //update next konten
            } else {
                binding.tvSkor.setText("" + skor);
                Toast.makeText(this, "Jawaban Salah", Toast.LENGTH_SHORT).show();
                setKonten();
            }
        } else if (binding.rbPilihanJawaban3.isChecked()) {
            //jika text yang tertulis di radio button tsb = nilai dari var jawaban
            if (binding.rbPilihanJawaban3.getText().toString().equals(kunci)) {
                skor = skor + (100/panjang);
                binding.tvSkor.setText("" + skor);    //mtvSkor diset nilainya = var skor
                Toast.makeText(this, "Jawaban Benar", Toast.LENGTH_SHORT).show(); //keluar notifikasi "Jawaban Benar"
                setKonten(); //update next konten
            } else {
                binding.tvSkor.setText("" + skor);
                Toast.makeText(this, "Jawaban Salah", Toast.LENGTH_SHORT).show();
                setKonten();
            }
        } else if (binding.rbPilihanJawaban4.isChecked()) {
            //jika text yang tertulis di radio button tsb = nilai dari var jawaban
            if (binding.rbPilihanJawaban4.getText().toString().equals(kunci)) {
                skor = skor + (100/panjang);
                binding.tvSkor.setText("" + skor);    //mtvSkor diset nilainya = var skor
                Toast.makeText(this, "Jawaban Benar", Toast.LENGTH_SHORT).show(); //keluar notifikasi "Jawaban Benar"
                setKonten(); //update next konten
            } else {
                binding.tvSkor.setText("" + skor);
                Toast.makeText(this, "Jawaban Salah", Toast.LENGTH_SHORT).show();
                setKonten();
            }
        }else if (binding.rbPilihanJawaban5.isChecked()) {
            //jika text yang tertulis di radio button tsb = nilai dari var jawaban
            if (binding.rbPilihanJawaban5.getText().toString().equals(kunci)) {
                skor = skor + (100/panjang);
                binding.tvSkor.setText("" + skor);    //mtvSkor diset nilainya = var skor
                Toast.makeText(this, "Jawaban Benar", Toast.LENGTH_SHORT).show(); //keluar notifikasi "Jawaban Benar"
                setKonten(); //update next konten
            } else {
                binding.tvSkor.setText("" + skor);
                Toast.makeText(this, "Jawaban Salah", Toast.LENGTH_SHORT).show();
                setKonten();
            }
        }
        else {
            Toast.makeText(this, "Silahkan pilih jawaban dulu!", Toast.LENGTH_SHORT).show();
        }
    }



    public void ambilData() {
        Log.e("apiSoal",api.SOAL+id_meeting);
        AndroidNetworking.get(api.SOAL+id_meeting)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            if (response.getString("status").equalsIgnoreCase("sukses")) {
                                JSONArray data = response.getJSONArray("res");
                                panjang = data.length();
                                Log.e("panjang",""+panjang);
                                for (int i = 0; i < data.length(); i++) {
                                    ArrayList<String> subJawaban = new ArrayList<>();
                                    JSONObject obj = data.getJSONObject(i);
                                    String id_kuis = obj.getString("meeting_kuis_id");

                                    String soal = obj.getString("meeting_kuis_soal");

                                    String pil_a = obj.getString("meeting_kuis_pil_a");
                                    subJawaban.add(pil_a);

                                    String pil_b = obj.getString("meeting_kuis_pil_b");
                                    subJawaban.add(pil_b);

                                    String pil_c = obj.getString("meeting_kuis_pil_c");
                                    subJawaban.add(pil_c);

                                    String pil_d = obj.getString("meeting_kuis_pil_d");
                                    subJawaban.add(pil_d);

                                    String pil_e = obj.getString("meeting_kuis_pil_e");
                                    subJawaban.add(pil_e);


                                    String kunci = obj.getString("meeting_kuis_jawaban");
                                    arraySoal.add(soal);
                                    arrayJawaban.add(subJawaban);
                                    arrayKunci.add(kunci);

                                    tinyDB.putString("keyIdKuis",id_kuis);



                                }
                                setKonten();
                            } else {
                                Toast.makeText(QuizActivity.this,"Gagal", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }


                });


    }
}

