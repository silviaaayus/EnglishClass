package com.silvia.english.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.silvia.english.R;
import com.silvia.english.model.Model_History;

import java.util.List;

public class Adapter_History extends RecyclerView.Adapter<Adapter_History.ViewHolder> {

    Context context;
    List<Model_History> dataHistory;

    public Adapter_History(List<Model_History> dataHistory) {
        this.dataHistory = dataHistory;
    }

    @NonNull
    @Override
    public Adapter_History.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_meeting, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_History.ViewHolder holder, int position) {
        Model_History data = dataHistory.get(position);
        holder.meeting.setText(data.getMeeting_nama());
        holder.tipe.setText(data.getMeeting_materi_tipe());
        holder.nilai.setText(data.getSkor());



    }

    @Override
    public int getItemCount() {
        return dataHistory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView meeting,nilai,tipe;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            meeting = itemView.findViewById(R.id.meeting);
            nilai = itemView.findViewById(R.id.nilai);
            tipe = itemView.findViewById(R.id.tipeMater);
        }
    }
}
