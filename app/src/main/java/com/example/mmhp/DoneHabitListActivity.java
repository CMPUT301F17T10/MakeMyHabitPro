package com.example.mmhp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class DoneHabitListActivity extends AppCompatActivity {

    private ArrayList<Habit> HabitList;
    private ArrayList<Habit> DoneHabitList;
    private ArrayAdapter<Habit> adapter;
    private AddHabitEventActivity A;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_habit_list);

        ListView DoneHabitList = (ListView) findViewById(R.id.donehabitlist);

        DoneHabitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(DoneHabitListActivity.this, AddHabitEventActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }

    private void dataGet(){}
    public void getHabitList() {}
    public void showList() {}
    public void sortHabitList(){}
}
