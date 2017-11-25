package com.example.spei.makemyhabitpro;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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

public class EventDetailActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
    private static final String FILENAME="Eventl.SAV";
    private Event event;
    private ArrayList<Event> eventList;
    private EditText editComment;
    private EditText editLocation;
    private String encodeImage;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        Intent intent = getIntent();
        eventId = intent.getStringExtra("eventId");
        UID =  intent.getStringExtra("UID");



        editComment = (EditText) findViewById(R.id.comment);
        editLocation = (EditText) findViewById(R.id.location);


        titletext = (TextView) findViewById(R.id.habittextView);
        detailtext = (TextView) findViewById(R.id.habitdetailtextView);






        imageView = (ImageView) findViewById(R.id.eventImageView3);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);

                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });


        imageButton = (Button) findViewById(R.id.eventImageButton3);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                Bitmap image =  ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                encodeImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);


            }
        });

        locationButton = (Button) findViewById(R.id.locationbutton);
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
        if (requestCode == RESULT_LOAD_IMAGE && requestCode == RESULT_OK && data != null) {
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
            if (encodeImage != null && location != null){
                event.setLocation(location);
                event.setUrl_img(encodeImage);
            }else if (encodeImage != null) {
                event.setUrl_img(encodeImage);
            }else if(location != null){
                event.setLocation(location);
            }

            Toast.makeText(getApplicationContext(), " Change the event",Toast.LENGTH_SHORT).show();
            saveInFile();
            finish();
        }else{
            Toast.makeText(getApplicationContext(), "Comment cannot be empty or longer than 20 characters!",Toast.LENGTH_SHORT).show();}

    }

    private void deleteEvent(){
        eventList.remove(position);
        Toast.makeText(getApplicationContext(), " Delete the event",Toast.LENGTH_SHORT).show();
        saveInFile();
        finish();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();

        if(eventList == null){

            eventList = new ArrayList<Event>();
        }


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
        encodeImage = event.getUrl_img();
        location = event.getLocation();

        if (!UID.equals(event.getUID())){
            saveButton.setEnabled(false);
            deleteButton.setEnabled(false);
            editComment.setEnabled(false);
            editLocation.setEnabled(false);
            imageView.setEnabled(false);
            imageButton.setEnabled(false);
            locationButton.setEnabled(false);
        }


        titletext.setText("Title: "+habit.getTitle());
        detailtext.setText("Detail: "+habit.getDetail());
        editComment.setText(event.getOwner_comment());
        if (!encodeImage.equals("None")) {}
        if (!location.equals("None")) {editLocation.setText(location);}
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