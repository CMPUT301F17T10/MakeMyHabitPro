/*
 * Copyright (c) 2017Team X, CMPUT301, University of Alberta-All Rights Reserved
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact spei@ualberta.ca
 */

package com.example.spei.makemyhabitpro;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.example.spei.makemyhabitpro.R.id.imageView;
import static com.example.spei.makemyhabitpro.R.id.saveBt;

/**
 * This class shows a event
 * user can change parameters of event and add comment
 * @author spei
 *
 * @since 1.0
 * @see EventListActivity
 * @see java.io.BufferedReader
 * @see PubCommentActivity
 */
public class EventDetailActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 147;
    private static final String FILENAME="Eventl.SAV";
    private Event event;
    private ArrayList<Event> eventList;
    private EditText editComment;
    private EditText editLocation;
    private Bitmap image;
    private Bitmap newImage;
    private ImageView imageView;
    private TextView titletext;
    private TextView detailtext;
    private String location;
    private Habit habit;
    private String comment;
    private int position;
    private String eventId;
    private String id;
    private String UID;
    private Button saveButton;
    private Button deleteButton;
    private Button imageButton;
    private Button locationButton;
    private Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        Intent intent = getIntent();
        eventId = intent.getStringExtra("eventId");
        UID =  intent.getStringExtra("UID");

        connection = new Connection(this);

        editComment = (EditText) findViewById(R.id.comment);
        editLocation = (EditText) findViewById(R.id.location);


        titletext = (TextView) findViewById(R.id.habittextView);
        detailtext = (TextView) findViewById(R.id.habitdetailtextView);

        imageView = (ImageView) findViewById(R.id.eventImageView3);
        imageButton = (Button) findViewById(R.id.eventImageButton3);

        locationButton = (Button) findViewById(R.id.locationbutton);




        loadFromFile();

        if(eventList == null){

            eventList = new ArrayList<Event>();
        }

//        if (connection.isConnected()){
//
//            ElasticsearchEvent.GetEvents getEvents=new ElasticsearchEvent.GetEvents();
//            getEvents.execute("");
//            try {
//                eventList = getEvents.get();
//            }catch (Exception e){
//            }
//        }

        int temp = 0;
        for (Event event1 : eventList) {
            if (eventId.equals(event1.getId())) {
                position = temp;
                break;
            }
            temp++;

        }

        event = eventList.get(position);
        habit = event.getHabit();
        id = event.getId();
        String encodeImage4 = event.getImg();
        byte [] encodeByte=Base64.decode(encodeImage4,Base64.DEFAULT);
        image = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        location = event.getLocation();

        titletext.setText("Title: "+habit.getTitle());
        detailtext.setText("Detail: "+habit.getDetail());
        editComment.setText(event.getOwner_comment());
        imageView.setImageBitmap(image);
        if (!location.equals("None")) {editLocation.setText(location);}



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //imageView.setImageDrawable(null);

                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });



        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                newImage =  ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                if (newImage.getByteCount() >= 65536*100){
                    Toast.makeText(getApplicationContext(), " The image need to be under 65536 bytes.",Toast.LENGTH_SHORT).show();
                    newImage = null;
                }


            }
        });


        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);



            }
        });


        saveButton = (Button) findViewById(R.id.save_edit);
        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);

                saveEvent();

            }
        });

        deleteButton = (Button) findViewById(R.id.delete);

        deleteButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);

                deleteEvent();

            }
        });

        Button pub_commentButton = (Button) findViewById(R.id.pub_comment);

        pub_commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventDetailActivity.this, PubCommentActivity.class);
                intent.putExtra("position02", position);
                startActivityForResult(intent,RESULT_OK);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            imageView.setImageURI(selectedImage);
        }
    }

//    private void dataGet(){}
//    public void getEventList(){}
//    public void getEvent(){}
//    public void editEventList(){}

    private void saveEvent(){
        comment = editComment.getText().toString();
        if (comment != null && !comment.equals("") && comment.length() < 20) {
            event.setOwner_comment(comment);
            if (newImage != null ){
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                newImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                String encodeImage2 = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

                event.setImg(encodeImage2);
            }
            if(location != null){
                event.setLocation(location);
            }


//            if (connection.isConnected()){
//                ElasticsearchEvent.DeleteEventTask deleteEventTask = new ElasticsearchEvent.DeleteEventTask();
//                deleteEventTask.execute(event);
//                ElasticsearchEvent.AddEventTask addEventTask = new ElasticsearchEvent.AddEventTask();
//                addEventTask.execute(event);
//
//                connection.updateAll();
//            }else{
//                connection.editEvent(event);
//            }

            Toast.makeText(getApplicationContext(), " Change the event",Toast.LENGTH_SHORT).show();
            saveInFile();
            finish();
        }else{
            Toast.makeText(getApplicationContext(), "Comment cannot be empty or longer than 20 characters!",Toast.LENGTH_SHORT).show();}

    }

    private void deleteEvent(){
        Event deleteEvent = eventList.get(position);

//        if (connection.isConnected()) {
//            ElasticsearchEvent.DeleteEventTask deleteEventTask = new ElasticsearchEvent.DeleteEventTask();
//            deleteEventTask.execute(deleteEvent);
//
//            connection.updateAll();
//        }else {
//            connection.deleteEvent(deleteEvent);
//        }

        eventList.remove(position);
        Toast.makeText(getApplicationContext(), " Delete the event",Toast.LENGTH_SHORT).show();
        saveInFile();

        finish();
    }


    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();




        if (!UID.equals(event.getUID())){
            saveButton.setEnabled(false);
            deleteButton.setEnabled(false);
            editComment.setEnabled(false);
            editLocation.setEnabled(false);
            imageView.setEnabled(false);
            imageButton.setEnabled(false);
            locationButton.setEnabled(false);
        }








    }




//    private void loadFromFile() {
//        try {
//            FileInputStream fis = openFileInput(FILENAME);
//            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
//
//            Gson gson = new Gson();
//
//
//            Eventl = gson.fromJson(in, EventList.class);
//
//
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            EventList = new ArrayList<Event>();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            throw new RuntimeException();
//        }
//    }
//
//    private void saveInFile() {
//        try {
//            FileOutputStream fos = openFileOutput(FILENAME,
//                    Context.MODE_PRIVATE);
//            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
//
//            Gson gson = new Gson();
//            gson.toJson(Eventl, EventList.class, out);
//            out.flush();
//            fos.close();
//        }
//        catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            throw new RuntimeException();
//        }
//        catch (IOException e) {
//            // TODO Auto-generated catch block
//            throw new RuntimeException();
//        }
//    }

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



    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(eventList,out);
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