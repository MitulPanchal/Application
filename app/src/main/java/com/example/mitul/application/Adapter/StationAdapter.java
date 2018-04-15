package com.example.mitul.application.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mitul.application.R;
import com.example.mitul.application.StationInfo;

import java.util.ArrayList;
import java.util.List;

public class StationAdapter extends RecyclerView.Adapter<StationAdapter.StationViewHolder> {

    private List<StationInfo> stationData = new ArrayList<>();

    public void changeData(List<StationInfo> data){
        stationData = data;
        notifyDataSetChanged();
    }

    @Override
    public StationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row, parent, false));
    }

    @Override
    public void onBindViewHolder(StationViewHolder holder, int position) {
        StationInfo current = stationData.get(position);
        holder.list_station.setText(current.getStationName());
    }

    @Override
    public int getItemCount() {
        return stationData.size();
    }

    public class StationViewHolder extends RecyclerView.ViewHolder {
        TextView list_station;
        public StationViewHolder(View itemView) {
            super(itemView);

            list_station = itemView.findViewById(R.id.stationList_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO Display all bus for this station.
                }
            });
        }
    }
}
