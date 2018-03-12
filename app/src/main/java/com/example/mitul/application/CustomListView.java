package com.example.mitul.application;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomListView extends ArrayAdapter<String> {

    private String []Station_name;
    private Activity context;
    public CustomListView(Activity context, String[] Station_name) {
        super(context,R.layout.station_list ,Station_name);

        this.context = context;
        this.Station_name = Station_name;
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertview, @NonNull ViewGroup parent){
        View V = convertview;
        ViewHolder viewHolder = null;
        if(V == null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            V = layoutInflater.inflate(R.layout.station_list,null, true);
            viewHolder = new ViewHolder(V);
            V.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) V.getTag();
        }
        viewHolder.textView2.setText(Station_name[position]);
        return V;
    }

    class ViewHolder
    {
        TextView textView2;
        ViewHolder(View V)
        {
            textView2 = V.findViewById(R.id.textView2);
        }
    }
}
