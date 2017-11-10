package com.example.mmhp;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class EventDetailActivity extends AppCompatActivity {

    private static final String FILENAME="Eventl.SAV";
    private EventList Eventl;
    private Event event;
    private ArrayList<Event> EventList;
    private EditText editComment;
    private Image image;
    private Location location;
    private Habit habit;
    private String comment;
    private Location CurrentLocation;
    private String img;
    private int position;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail_avtivity);

        Intent intent = getIntent();
        position = intent.getIntExtra("position",0);



        editComment = (EditText) findViewById(R.id.comment);


        Button saveButton = (Button) findViewById(R.id.save);

        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);

                saveEvent();
                saveInFile();
            }
        });

        Button deleteButton = (Button) findViewById(R.id.delete);

        deleteButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);

                deleteEvent();
                saveInFile();
            }
        });
    }

    private void dataGet(){}
    public void getEventList(){}
    public void getEvent(){}
    public void editEventList(){}

    private void saveEvent(){
        comment = editComment.getText().toString();
        if (comment != null) {
            Event newEvent = new Event(habit, comment, id);
            EventList.add(newEvent);
        }else{
            Toast.makeText(getApplicationContext(), "Comment cannot be empty!",Toast.LENGTH_SHORT).show();}

    }

    private void deleteEvent(){
        EventList.remove(position);
    }

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

        event = EventList.get(position);
        habit = event.getHabit();
        id = event.getId();
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

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(EventList, out);
            out.flush();
            fos.close();
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
}
