package com.ndahapp.deceive.kerehure.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ndahapp.deceive.kerehure.R;

import java.util.ArrayList;

/**
 * Created by Deceive on 3/22/2018.
 */

public class ConfigAdapterList extends ArrayAdapter {
    Context context;
    String[] tittle_array;
    String[] content_array;
    ArrayList<ConfigEntity> list;

    public ConfigAdapterList(@NonNull Context context, ArrayList<ConfigEntity> list) {
        super(context, R.layout.config_row);
        this.list = list;
        this.context = context;
        this.tittle_array = tittle_array;
        this.content_array = content_array;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.config_row, parent, false);
        TextView titleConfig = (TextView) rowView.findViewById(R.id.titleTextConfigRow);
        TextView contentConfig = (TextView) rowView.findViewById(R.id.contentTextConfigRow);
        titleConfig.setText(list.get(position).config_name);
        contentConfig.setText(list.get(position).config_value);
        return rowView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }
}
