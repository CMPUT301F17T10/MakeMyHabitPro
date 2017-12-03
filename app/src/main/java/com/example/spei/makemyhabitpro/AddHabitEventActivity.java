/*
 * Copyright (c) 2017Team X, CMPUT301, University of Alberta-All Rights Reserved
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact spei@ualberta.ca
 */

package com.example.spei.makemyhabitpro;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.UUID;

/**
 * This class add a event to event list
 * user need to input some parameters to create a new event
 * @author spei
 *
 * @since 1.0
 * @see java.io.BufferedReader
 * @see AddHabitEventActivity
 */
public class AddHabitEventActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 147;
    private static final String FILENAME="Eventl.SAV";
    private ArrayList<Event> EventList;
    private TextView titletext;
    private TextView detailtext;
    private EditText editComment;
    private EditText location;
    private ImageView imageView;
    private Bitmap image;
    private Bitmap img;
    private EditText location;
    private Event newEvent;
    private Habit habit;
    private String comment;
    private String CurrentLocation;
    private int position;
    private String id;
    private String habitS;
    private String UID;
    private long size;
    private String user_data;
    private User local_user;
    private Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit_event);

        Intent intent = getIntent();
        habitS =  intent.getStringExtra(LogInActivity.EXTRA_MESSAGE);
        Gson gson = new Gson();
        habit=gson.fromJson(habitS,Habit.class);

        user_data=  intent.getStringExtra("User");
        Gson gson1 = new Gson();
        local_user=gson1.fromJson(user_data,User.class);
        UID=local_user.getUid();

        connection = new Connection(this);

        editComment = (EditText) findViewById(R.id.comment);

        titletext = (TextView) findViewById(R.id.habittextView1);
        detailtext = (TextView) findViewById(R.id.habitdetailtextView1);

        titletext.setText("Title: "+habit.getTitle());
        detailtext.setText("Detail: "+habit.getDetail());


        imageView = (ImageView) findViewById(R.id.imageView2);

        img = BitmapFactory.decodeResource(getResources(),
                R.drawable.download);
        imageView.setImageBitmap(img);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //imageView.setImageDrawable(null);

                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });


        Button imageButton = (Button) findViewById(R.id.uploadimage);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                image =  ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                size = image.getByteCount();
                if (image.getByteCount() >= 65536*100){
                    Toast.makeText(getApplicationContext(), " The image need to be under 65536 bytes.",Toast.LENGTH_SHORT).show();
                    image = null;
                }



            }
        });

        location = (EditText) findViewById(R.id.editText2);
        Button locationB = (Button) findViewById(R.id.button2);
        locationB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (connection.isConnected()){


                    location.setText();
                }else {
                    Toast.makeText(getApplicationContext(), "No internet connection!",Toast.LENGTH_SHORT).show();
                }

            }
        });

        Button saveButton = (Button) findViewById(R.id.save);

        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);

                saveEvent();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {

            Uri selectedImage = data.getData();

//            try {
//                Bitmap bitmap1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
//                // Log.d(TAG, String.valueOf(bitmap));
//
//                imageView.setImageBitmap(bitmap1);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            imageView.setImageURI(selectedImage);


        }
    }

//    private void dataGet(){}
//    public void getEventList(){}
//    public void editEventList(){}

    /**
     * save the new event
     */
    private void saveEvent(){
        comment = editComment.getText().toString();
        if (comment != null && !comment.equals("") && comment.length() < 20) {
            id=getUUID();
            newEvent = new Event(habit, comment, id, UID);

            if (image != null ) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                String encodeImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                newEvent.setImg(encodeImage);
            }else {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                img.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                String encodeImage1 = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                newEvent.setImg(encodeImage1);
            }
            if(location != null){
                newEvent.setLocation(location);
            }


//            if (connection.isConnected()) {
//                ElasticsearchEvent.AddEventTask task = new ElasticsearchEvent.AddEventTask();
//                task.execute(newEvent);
//                connection.updateAll();
//
//            }else {
//                connection.addEvent(newEvent);
//            }

            EventList.add(newEvent);
            saveInFile();

            habit.doIt();
            local_user.add_exp();
            Toast.makeText(getApplicationContext(), " Add a new event",Toast.LENGTH_SHORT).show();

            finish();
        }else{
            Toast.makeText(getApplicationContext(), "Comment cannot be empty or longer than 20 characters!",Toast.LENGTH_SHORT).show();}

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();


        loadFromFile();


        if (EventList == null) {

            EventList = new ArrayList<Event>();
        }





    }


    /**
     * create a unique id for event
     * @return the id
     */

    public static String getUUID(){

        UUID uuid=UUID.randomUUID();
        String uid = uuid.toString();
        return uid;
//        UUID uuid= UUID.randomUUID();
//        String str = uuid.toString();
//        String uuidStr=str.replace("-", "");
//        return uuidStr;
    }

    /**
     * This mathod loads the json file.
     * @throws RuntimeException
     * @exception FileNotFoundException
     */
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();


            Type listType = new TypeToken<ArrayList<Event>>() {
            }.getType();

            EventList = gson.fromJson(in, listType);


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            EventList = new ArrayList<Event>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }


    /**
     * Saves EventList into FILENAME, using Gson
     */
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(EventList,out);
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