package com.silvia.english;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.silvia.english.databinding.ActivityRecordBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class RecordActivity extends AppCompatActivity {
    private ActivityRecordBinding binding;

    private MediaRecorder mRecorder;
    private MediaPlayer mPlayerRecord;
    private static String mFileName = null;
    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;
    private static final String LOG_TAG = "AudioRecording";

    //Listening
    MediaPlayer mmPlayer;
    MediaPlayer mPlayer;
    static int onTime = 0, startTime = 0, endTime = 0;
    Handler hdlr = new Handler();
    String audio1,audio2,materi,id_materi,id_siswa,tipe;
    ApiServer api;
    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AndroidNetworking.initialize(this);
        api = new ApiServer();
        tinyDB = new TinyDB(this);
         audio1 = tinyDB.getString("meeting_materi_audio1");
         audio2 = tinyDB.getString("meeting_materi_audio2");
         materi = tinyDB.getString("meeting_materi_teks");

        id_materi = tinyDB.getString("meeting_materi_id2");
        id_siswa = tinyDB.getString("keyIdSiswa");
        Log.e("id","materi"+id_materi);
        Log.e("siswa",id_siswa);
        mPlayer = MediaPlayer.create(getApplication(), Uri.parse(api.SUARA+audio1));
        mmPlayer = MediaPlayer.create(getApplication(), Uri.parse(api.SUARA+audio2));

        binding.txtMateri.setText(Html.fromHtml(materi));
        binding.btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                inputRecord();
                inputRecordFile();
            }
        });
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Listening
        binding.A.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!mPlayer.isPlaying()){
                    mPlayer.start();
                    binding.imgA.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
                    tipe = "A";


                } else{
                    mPlayer.pause();

                    binding.imgA.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);

                }
            }

        });

        binding.B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mmPlayer.isPlaying()){
                    tipe = "B";
                    mmPlayer.start();

                    binding.imgB.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
                } else{
                    mmPlayer.pause();

                    binding.imgB.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                }
            }
        });

//
//        endTime = mPlayer.getDuration();
//        startTime = mPlayer.getCurrentPosition();
//        if (onTime == 0 ){
//            onTime = 1;
//        }
//
//        endTime = mmPlayer.getDuration();
//        startTime = mmPlayer.getCurrentPosition();
//        if (onTime == 0 ){
//             onTime = 1;
//        }




        //Record
        binding.btnStop.setBackgroundColor(getResources().getColor(R.color.hijau));
        binding.btnPlay.setBackgroundColor(getResources().getColor(R.color.hijau));



        binding.btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckPermissions()) {

                    binding.btnStop.setBackgroundColor(getResources().getColor(R.color.hijau));
                    binding.btnRecord.setBackgroundColor(getResources().getColor(R.color.hijau_200));
                    binding.btnPlay.setBackgroundColor(getResources().getColor(R.color.hijau_200));
                    mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();


                    SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                            "yyyy-MM-dd-HH.mm.ss");
                    mFileName += "/audio_" + timeStampFormat.format(new Date())
                            + ".mp3";


                    mRecorder = new MediaRecorder();
                    mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    mRecorder.setOutputFile(mFileName);

                    Log.e("suara uri", mFileName);
                    try {
                        mRecorder.prepare();
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "prepare() failed");
                    }
                    mRecorder.start();
                    Toast.makeText(getApplicationContext(), "Recording Started", Toast.LENGTH_LONG).show();
                }
                else
                {
                    RequestPermissions();
                }
            }
        });
        binding.btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.btnStop.setBackgroundColor(getResources().getColor(R.color.hijau_200));
                binding.btnRecord.setBackgroundColor(getResources().getColor(R.color.hijau_200));
                binding.btnPlay.setBackgroundColor(getResources().getColor(R.color.hijau));

                mRecorder.stop();
                mRecorder.release();
                mRecorder = null;
                Toast.makeText(getApplicationContext(), "Recording Stopped", Toast.LENGTH_LONG).show();
            }
        });

          binding.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.btnStop.setBackgroundColor(getResources().getColor(R.color.hijau_200));
                binding.btnRecord.setBackgroundColor(getResources().getColor(R.color.hijau_200));
                mPlayerRecord = new MediaPlayer();
                try {
                    mPlayerRecord.setDataSource(mFileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    mPlayerRecord.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mPlayerRecord.start();
                Toast.makeText(RecordActivity.this, "Recording Started Playing", Toast.LENGTH_SHORT).show();


            }
        });
   
        binding.btnStopPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayerRecord.release();
                mPlayerRecord = null;
                binding.btnStop.setBackgroundColor(getResources().getColor(R.color.hijau_200));
                binding.btnRecord.setBackgroundColor(getResources().getColor(R.color.hijau));
                binding.btnPlay.setBackgroundColor(getResources().getColor(R.color.hijau));
                binding.btnStopPlay.setBackgroundColor(getResources().getColor(R.color.hijau_200));
                Toast.makeText(getApplicationContext(),"Playing Audio Stopped", Toast.LENGTH_SHORT).show();
            }
        });

    }


    //Record
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_AUDIO_PERMISSION_CODE:
                if (grantResults.length > 0) {
                    boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean permissionToStore = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (permissionToRecord && permissionToStore) {
                        Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    private boolean CheckPermissions() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void RequestPermissions() {
        ActivityCompat.requestPermissions(RecordActivity.this, new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);
    }

    private void inputRecordFile() {
        Uri myUri = Uri.parse(mFileName);
        File suara = new File(myUri.getPath());
        Log.e("record",api.UPLOAD_SUARA+"?siswa_id="+id_siswa+"&meeting_materi_id="+id_materi+"&meeting_record_tipe="+tipe);
        AndroidNetworking.upload(api.UPLOAD_SUARA+"?siswa_id="+id_siswa+"&meeting_materi_id="+id_materi+"&meeting_record_tipe="+tipe)
                .addMultipartFile("suara", suara)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                          if (response.getString("status").equalsIgnoreCase("true")){
                              Toast.makeText(RecordActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(RecordActivity.this,MeetingActivity.class);
                                startActivity(i);

                            }
                            else {
                                Toast.makeText(RecordActivity.this, "Gagal", Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("eror", "code :"+anError);
                        Toast.makeText(RecordActivity.this, ""+anError, Toast.LENGTH_SHORT).show();
                    }
                });
    }



    @Override
    protected void onPause() {
        super.onPause();
        if (mPlayer.isPlaying()){
            mPlayer.pause();
            binding.imgA.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
        }

        if (mmPlayer.isPlaying()){
            mmPlayer.pause();
            binding.imgB.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer.isPlaying()){
            mRecorder.stop();
            mmPlayer.release();
            binding.imgA.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
        }

        if (mmPlayer.isPlaying()){
            mRecorder.stop();
            mmPlayer.release();
            binding.imgB.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
        }
    }
}