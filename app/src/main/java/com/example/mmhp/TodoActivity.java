package com.example.mmhp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class TodoActivity extends AppCompatActivity {

    private ArrayList<Habit> TodoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
    }

    public void sortList(){}
    public void showList(){}
    public void dateGet(){}
}
