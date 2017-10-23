package com.example.mmhp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class EventListActivity extends AppCompatActivity {

    private ArrayList<Event> EventList;
    private ArrayAdapter<Event> adapter;
    private DoneHabitListActivity D;
    private EventDetailActivity E;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        Button addEventButton = (Button) findViewById(R.id.addevent);
        ListView EventList = (ListView) findViewById(R.id.eventlist);

        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                startActivity(new Intent(EventListActivity.this,DoneHabitListActivity.class));
            }
        });

        EventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(EventListActivity.this, EventDetailActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }

    private void dataGet(){}
    public void getEventList() {}
    public void showList() {}
}
