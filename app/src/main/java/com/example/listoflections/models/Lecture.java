package com.example.listoflections.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;


public class Lecture implements Parcelable {

    private final int mNumber;
    private final int mWeekInd;
    private final String mDate;
    private final String mTheme;
    private final String mLecturer;
    private final List<String> mSubTopics;

    @JsonCreator
    public Lecture(
            @JsonProperty("number") int number,
            @JsonProperty("date") @NonNull String date,
            @JsonProperty("theme") @NonNull String theme,
            @JsonProperty("lector") @NonNull String lecturer,
            @JsonProperty("subtopics") @NonNull List<String> subTopics){
        mDate = date;
        mNumber = number;
        mTheme = theme;
        mLecturer = lecturer;
        mSubTopics = new ArrayList<>(subTopics);
        mWeekInd = (mNumber - 1)/3;
    }

    protected Lecture(Parcel in) {
        mNumber = in.readInt();
        mDate = in.readString();
        mTheme = in.readString();
        mLecturer = in.readString();
        mSubTopics = in.createStringArrayList();
        mWeekInd = (mNumber - 1)/3;
    }

    public static final Creator<Lecture> CREATOR = new Creator<Lecture>() {
        @Override
        public Lecture createFromParcel(Parcel in) {
            return new Lecture(in);
        }

        @Override
        public Lecture[] newArray(int size) {
            return new Lecture[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mNumber);
        parcel.writeString(mDate);
        parcel.writeString(mTheme);
        parcel.writeString(mLecturer);
        parcel.writeStringList(mSubTopics);
    }
}