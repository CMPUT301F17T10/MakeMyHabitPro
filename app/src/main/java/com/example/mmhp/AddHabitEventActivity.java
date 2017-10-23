package com.example.mmhp;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class AddHabitEventActivity extends AppCompatActivity {

    private ArrayList<Event> EventList;
    private Habit habit;
    private String comment;
    private Location CurrentLocation;
    private String img;
    private Event e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit_event);
    }

    private void dataGet(){}
    public void getEventList(){}
    public void editEventList(){}
}
