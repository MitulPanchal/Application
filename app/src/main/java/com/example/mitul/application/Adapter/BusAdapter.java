package com.example.mitul.application.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mitul.application.BusInfo;
import com.example.mitul.application.LocationActivity;
import com.example.mitul.application.R;

import java.util.ArrayList;
import java.util.List;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.BusViewHolder>{

    private List<BusInfo> busData = new ArrayList<>();

    public void changeData(List<BusInfo> data){
        busData = data;
        notifyDataSetChanged();
    }

    @Override
    public BusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BusViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_row, parent, false));
    }

    @Override
    public void onBindViewHolder(BusViewHolder holder, int position) {
        BusInfo current = busData.get(position);
        holder.list_station.setText(current.getBus_name());
        holder.thai_gayu.setText(String.valueOf(current.getBus_id()));
    }

    @Override
    public int getItemCount() {
        return busData.size();
    }

    public class BusViewHolder extends RecyclerView.ViewHolder {
        TextView list_station;
        TextView thai_gayu;
        public BusViewHolder(final View itemView) {
            super(itemView);

            list_station = itemView.findViewById(R.id.busList_text);
            thai_gayu = itemView.findViewById(R.id.thai_gayu);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO Display all bus for this station.

//                    itemView.getContext().startActivity(new Intent(itemView.getContext(), LocationActivity.class));
                }
            });
        }
    }
}
