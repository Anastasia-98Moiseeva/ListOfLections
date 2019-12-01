package com.example.listoflections.models;

import androidx.annotation.NonNull;


public class Lecture {

    private final int mNumber;
    private final int mWeekInd;
    private final String mDate;
    private final String mTheme;
    private final String mLecturer;

    public Lecture(
            int number,
            @NonNull String date,
            @NonNull String theme,
            @NonNull String lecturer
    ) {
        this.mNumber = number;
        this.mDate = date;
        this.mTheme = theme;
        this.mLecturer = lecturer;
        this.mWeekInd = (mNumber - 1)/3;
    }

    public int getNumber() {
        return mNumber;
    }

    public String getDate() {
        return mDate;
    }

    public String getTheme() {
        return mTheme;
    }

    public String getLecturer() {
        return mLecturer;
    }

    public int getWeekInd() {
        return mWeekInd;
    }
}