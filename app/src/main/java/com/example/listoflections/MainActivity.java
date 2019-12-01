package com.example.listoflections;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listoflections.adapters.LecturerSpinnerAdapter;
import com.example.listoflections.adapters.LearningProgramAdapter;
import com.example.listoflections.adapters.WeekSpinnerAdapter;
import com.example.listoflections.models.Lecture;
import com.example.listoflections.providers.LearningProgramProvider;

public class MainActivity extends AppCompatActivity {

    private static final int POSITION_ALL = 0;

    private LearningProgramProvider mLearningProgramProvider = new LearningProgramProvider();
    private LearningProgramAdapter mLearningProgramAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null){
            new LecturesLoading().execute();
        }
    }

    private void initLecturerSpinner() {
        Spinner lecturersSpinner = findViewById(R.id.lection_spinner);
        final List<String> lecturers = mLearningProgramProvider.provideLecturers();
        Collections.sort(lecturers);
        lecturers.add(POSITION_ALL, getString(R.string.all));
        lecturersSpinner.setAdapter(new LecturerSpinnerAdapter(lecturers));
        lecturersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == POSITION_ALL){
                    mLearningProgramAdapter.setLectures(mLearningProgramProvider.provideLectures());
                }
                else{
                    mLearningProgramAdapter.setLectures(mLearningProgramProvider.filterByLecturer(lecturers.get(position)));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void initWeekSpinner() {
        Spinner weekSpinner = findViewById(R.id.week_spinner);
        final List<String> typesOfGroupedByWeek = new ArrayList<>();
        typesOfGroupedByWeek.add(LearningProgramAdapter.UNGROUPED, getString(R.string.ungrouped));
        typesOfGroupedByWeek.add(LearningProgramAdapter.GROUPED_BY_WEEK, getString(R.string.ungrouped_by_weeks));
        weekSpinner.setAdapter(new WeekSpinnerAdapter(typesOfGroupedByWeek));
        weekSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mLearningProgramAdapter.setGroupType(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initRecyclerView(List<Lecture> lectures) {
        RecyclerView recyclerView = findViewById(R.id.lection_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        mLearningProgramAdapter = new LearningProgramAdapter();
        mLearningProgramAdapter.setLectures(lectures);
        DividerItemDecoration decoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(mLearningProgramAdapter);
        Lecture currentLecture = mLearningProgramProvider.getNextLecture(new Date());
        recyclerView.scrollToPosition(lectures.indexOf(currentLecture));
    }

    private class LecturesLoading extends AsyncTask<Void, Void, List<Lecture>>{

        @Override
        protected List<Lecture> doInBackground(Void... voids) {
            return mLearningProgramProvider.loadFromInternet();
        }

        @Override
        protected void onPostExecute(List<Lecture> lectures) {
            if(lectures != null){
                initLecturerSpinner();
                initWeekSpinner();
                initRecyclerView(lectures);
            } else {
                Toast.makeText(getApplicationContext(), "Не удалось загрузить данные", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
