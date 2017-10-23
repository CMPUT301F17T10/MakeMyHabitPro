package com.example.mmhp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class HabitActivity extends AppCompatActivity {
    private Habit j;
    private ArrayList<Habit> habitList;
    private AddHabitActivity A;
    private DetailHabitActivity D;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);
    }

    public void showList(){}
    public void dateGet(){}

}
