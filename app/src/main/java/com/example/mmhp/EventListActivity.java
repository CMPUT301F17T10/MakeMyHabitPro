package com.example.mmhp;

import android.content.Intent;
import android.support.constraint.solver.SolverVariable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class EventListActivity extends AppCompatActivity {

    private EventList Eventl;
    private ArrayList<Event> EventList;
    private ListView oldEventList;
    private ArrayAdapter<Event> adapter;
    private User local_user;
    private String user_data;
    private static final String FILENAME="Eventl.SAV";
    public String UID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        Intent Mainintent = getIntent();
        user_data=  Mainintent.getStringExtra(LogInActivity.EXTRA_MESSAGE);
        Gson gson = new Gson();
        local_user=gson.fromJson(user_data,User.class);
        UID=local_user.getUid();

        Button addEventButton = (Button) findViewById(R.id.addevent);
        oldEventList = (ListView) findViewById(R.id.eventlist);

        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                startActivity(new Intent(EventListActivity.this,DoneHabitListActivity.class));
            }
        });

        oldEventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();

        if(Eventl!=null){
            EventList = Eventl.getEvents();
        }else{
            EventList = new ArrayList<Event>();
        }

        adapter = new ArrayAdapter<Event>(this,
                R.layout.list_item, EventList);

        oldEventList.setAdapter(adapter);

    }


    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();


            Eventl = gson.fromJson(in, EventList.class);


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            EventList = new ArrayList<Event>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
}
