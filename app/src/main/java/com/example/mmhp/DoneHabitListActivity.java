package com.example.mmhp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class DoneHabitListActivity extends AppCompatActivity {

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
}
