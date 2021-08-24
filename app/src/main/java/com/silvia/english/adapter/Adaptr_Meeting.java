package com.silvia.english.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.silvia.english.ApiServer;
import com.silvia.english.PilihanActivity;
import com.silvia.english.R;
import com.silvia.english.model.Model_Meeting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Adaptr_Meeting extends RecyclerView.Adapter<Adaptr_Meeting.ViewHolder> {

    Context context;
    List<Model_Meeting> dataMeeting;

    public Adaptr_Meeting(List<Model_Meeting> dataMeeting) {
        this.dataMeeting = dataMeeting;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_meeting,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model_Meeting data =dataMeeting.get(position);
        ApiServer api = new ApiServer();
        holder.nama_meeting.setText(data.getMeeting_nama());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PilihanActivity.class);
                i.putExtra("meeting_id", data.getMeeting_id());
                context.startActivity(i);
            }
        });



    }


    @Override
    public int getItemCount() {
        return dataMeeting.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama_meeting;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            nama_meeting = itemView.findViewById(R.id.meeting);
        }
    }
}
