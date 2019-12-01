package com.example.listoflections.providers;


import com.example.listoflections.models.Lecture;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;


public class LearningProgramProvider {

    private static final String URL = "http://landsovet.ru/learning_program.json";

    public List<Lecture> mLectures;

    public List<Lecture> provideLectures(){
        return mLectures;
    }

    public List<String> provideLecturers(){
        Set<String> lecturers = new HashSet<>();
        for (Lecture lecture: mLectures) {
            lecturers.add(lecture.getLecturer());
        }
        return new ArrayList<>(lecturers);
    }

    public List<Lecture> filterByLecturer(String lecturer){
        List<Lecture> result = new ArrayList<>();
        for (Lecture lecture : mLectures) {
            if(lecture.getLecturer().equals(lecturer)){
                result.add(lecture);
            }
        }
        return result;
    }

    public Lecture getNextLecture(Date date){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        for(Lecture lecture: mLectures){
            try{
                if(format.parse(lecture.getDate()).after(date)){
                    return lecture;
                }
            }catch (ParseException e){
                e.printStackTrace();
            }
        }
        return mLectures.get(mLectures.size()-1);
    }

    public List<Lecture> loadFromInternet(){
        if(mLectures != null){
            return mLectures;
        }
        InputStream inputStream = null;
        try {
            final URL url = new URL(URL);
            URLConnection connection = url.openConnection();
            inputStream = connection.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            Lecture[] lectures = mapper.readValue(inputStream, Lecture[].class);
            mLectures = Arrays.asList(lectures);
            return  mLectures;
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
