package com.example.mmhp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddHabitActivity extends AppCompatActivity {

    private String name;
    private Date startDate;
    private Date lastActive;
    private ArrayList doDate;
    private int htype;
    private EventList events;
    private int owner;
    private Habit h;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);
    }
    private ArrayList<Habit> HabitList;

    public void ModifyHabitList(){}
    public void dateGet(){}
}
