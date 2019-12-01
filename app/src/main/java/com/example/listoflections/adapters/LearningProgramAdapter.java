package com.example.listoflections.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.listoflections.R;
import com.example.listoflections.models.Lecture;

import java.util.ArrayList;
import java.util.List;

public class LearningProgramAdapter extends Adapter<LearningProgramAdapter.BaseViewHolder> {

    private final int LECTURE = 1;
    private final int WEEK = 0;
    private final int ERROR = -1;

    public static final int GROUPED_BY_WEEK = 1;
    public static final int UNGROUPED = 0;

    private int mGroupType;

    private List<Lecture> mLectures;
    private List<Object> mItems;


    @Override
    public int getItemViewType(int position) {
        Object item = mItems.get(position);
        if (item instanceof Lecture) {
            return LECTURE;
        }
        if (item instanceof String) {
            return WEEK;
        }
        return ERROR;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case LECTURE: {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.lecture_item, parent, false);
                return new LectureHolder(view);
            }
            case WEEK: {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.week_item, parent, false);
                return new WeekHolder(view);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        Object item = mItems.get(position);
        switch (getItemViewType(position)) {
            case LECTURE:
                ((LectureHolder) holder).mNumber.setText(String.valueOf(((Lecture) item).getNumber()));
                ((LectureHolder) holder).mDate.setText(((Lecture) item).getDate());
                ((LectureHolder) holder).mTheme.setText(((Lecture) item).getTheme());
                ((LectureHolder) holder).mLecturer.setText(((Lecture) item).getLecturer());
                break;
            case WEEK:
                ((WeekHolder) holder).mWeek.setText((String) item);
                break;
        }
    }


    public void setLectures(List<Lecture> lectures) {
        if (lectures == null) {
            mLectures = new ArrayList<>();
            mItems = new ArrayList<>();
        } else {
            mLectures = new ArrayList<>(lectures);
            switch (mGroupType) {
                case GROUPED_BY_WEEK:
                    groupByWeek(lectures);
                    break;
                case UNGROUPED:
                default:
                    mItems = new ArrayList<Object>(lectures);
            }
        }
        notifyDataSetChanged();
    }


    private void groupByWeek(List<Lecture> lectures) {
        mItems = new ArrayList<>();
        int weekInd = -1;
        for (Lecture lecture : lectures) {
            if(lecture.getWeekInd() > weekInd){
                weekInd = lecture.getWeekInd();
                int i = weekInd+1;
                mItems.add("Неделя " + i);
            }
            mItems.add(lecture);
        }
    }

    public void setGroupType(int type){
        mGroupType = type;
        setLectures(mLectures);
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    static abstract class BaseViewHolder extends RecyclerView.ViewHolder {
        BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    static class LectureHolder extends BaseViewHolder{

        private final TextView mNumber;
        private final TextView mDate;
        private final TextView mTheme;
        private final TextView mLecturer;

        public LectureHolder(@NonNull View itemView) {
            super(itemView);
            mNumber = itemView.findViewById(R.id.number);
            mDate = itemView.findViewById(R.id.date);
            mTheme = itemView.findViewById(R.id.theme);
            mLecturer = itemView.findViewById(R.id.lecturer);
        }
    }

    private static class WeekHolder extends BaseViewHolder {
        private final TextView mWeek;

        private WeekHolder(@NonNull View itemView) {
            super(itemView);
            mWeek = itemView.findViewById(R.id.week);
        }
    }
}
