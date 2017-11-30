package com.example.spei.makemyhabitpro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLog;
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
import java.lang.reflect.Type;
import java.util.ArrayList;

public class EventListActivity extends AppCompatActivity {

    private ArrayList<Event> eventList;
    private ArrayList<Event> myEventList;
    private ListView oldEventList;
    private ArrayAdapter<Event> adapter;
    private User local_user;
    private String user_data;
    private static final String FILENAME="Eventl.SAV";
    private String UID;
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


        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                Intent intent = new Intent(EventListActivity.this, DoneHabitListActivity.class);
                intent.putExtra("UID", UID);
                startActivityForResult(intent,RESULT_OK);
            }
        });

        oldEventList = (ListView) findViewById(R.id.eventlist001);
        oldEventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(EventListActivity.this, EventDetailActivity.class);

                Event clickEvent = myEventList.get(position);
                String clickId = clickEvent.getId();
                intent.putExtra("eventId", clickId);
                intent.putExtra("UID", UID);
                startActivityForResult(intent,RESULT_OK);
            }
        });
    }

//    private void dataGet(){}
//    public void getEventList() {}
//    public void showList() {}

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        //loadFromFile();

//        if(eventList == null){
//
//            eventList = new ArrayList<Event>();
//        }




        myEventList = new ArrayList<Event>();

        String event_query = "{\n" +
                "  \"query\": { \n" +
                " \"match\" : { \"UID\" : \"" + UID +  "\" }}\n" +
                "}";
        ElasticsearchEvent.GetEvents getEvents=new ElasticsearchEvent.GetEvents();
        getEvents.execute(event_query);
        try {
            myEventList = getEvents.get();
        }catch (Exception e){
        }


//        for (Event event : eventList) {
//            if (UID.equals(event.getUID())) {
//                myEventList.add(event);
//            }
//
//        }


        adapter = new ArrayAdapter<Event>(this,
                R.layout.list_item, myEventList);

        oldEventList.setAdapter(adapter);

    }


    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();


            Type listType = new TypeToken<ArrayList<Event>>() {
            }.getType();

            eventList = gson.fromJson(in, listType);


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            eventList = new ArrayList<Event>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
}