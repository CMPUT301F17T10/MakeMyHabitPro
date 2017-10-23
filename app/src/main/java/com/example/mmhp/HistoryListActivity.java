package com.example.mmhp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HistoryListActivity extends AppCompatActivity {
    private HabitList Hlist;
    private Habit h;
    private MapActivity Map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);
    }
    private void dataGet(){}
    private void sort(){}
    private void filter(){}
    private void search(String sth){}
    private void gotoMap(){}
}
