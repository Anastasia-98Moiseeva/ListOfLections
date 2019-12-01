package com.example.listoflections.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WeekSpinnerAdapter extends BaseAdapter {

    private final List<String> mWeeks;

    public WeekSpinnerAdapter(List<String> weeks) {
        mWeeks = new ArrayList<>(weeks);
    }

    @Override
    public int getCount() {
        return mWeeks.size();
    }

    @Override
    public String getItem(int position) {
        return mWeeks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(
                    parent.getContext()).inflate(
                    android.R.layout.simple_list_item_1, parent, false);
            ViewHolder holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.mChoiceWeekMode.setText(getItem(position));
        return convertView;
    }

    private class ViewHolder{
        private final TextView mChoiceWeekMode;

        ViewHolder(View view){
            mChoiceWeekMode = view.findViewById(android.R.id.text1);
        }
    }
}
