package com.example.listoflections.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class LecturerSpinnerAdapter extends BaseAdapter {

    private final List<String> mLecturers;

    public LecturerSpinnerAdapter(@NonNull List<String> lecturers) {
        this.mLecturers = new ArrayList<>(lecturers);
    }

    @Override
    public int getCount() {
        return mLecturers.size();
    }

    @Override
    public String getItem(int position) {
        return mLecturers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            ViewHolder viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.mName.setText(getItem(position));
        return convertView;
    }

    private class ViewHolder{
        private final TextView mName;

        private ViewHolder(View view) {
            mName = view.findViewById(android.R.id.text1);
        }
    }
}
