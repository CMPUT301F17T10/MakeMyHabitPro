package com.example.spei.makemyhabitpro;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

public class AddHabitEventActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
    private static final String FILENAME="Eventl.SAV";
    private ArrayList<Event> EventList;
    private TextView titletext;
    private TextView detailtext;
    private EditText editComment;
    private ImageView imageView;
    private Bitmap image;
    private Bitmap img;
    private String location;
    private Event newEvent;
    private Habit habit;
    private String comment;
    private String CurrentLocation;
    private int position;
    private String id;
    private String habitS;
    private  String UID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit_event);

        Intent intent = getIntent();
        habitS =  intent.getStringExtra(LogInActivity.EXTRA_MESSAGE);
        Gson gson = new Gson();
        habit=gson.fromJson(habitS,Habit.class);
        UID =  intent.getStringExtra("UID");





        editComment = (EditText) findViewById(R.id.comment);

        titletext = (TextView) findViewById(R.id.habittextView1);
        detailtext = (TextView) findViewById(R.id.habitdetailtextView1);

        titletext.setText("Title: "+habit.getTitle());
        detailtext.setText("Detail: "+habit.getDetail());


        imageView = (ImageView) findViewById(R.id.imageView2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);

                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });


        Button imageButton = (Button) findViewById(R.id.uploadimage);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                image =  ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                if (image.getByteCount() < 65536){
                    newEvent.setImg(image);
                }else {Toast.makeText(getApplicationContext(), " The image need to be under 65536 bytes.",Toast.LENGTH_SHORT).show();}



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
        if (requestCode == RESULT_LOAD_IMAGE && requestCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            imageView.setImageURI(selectedImage);
        }
    }

        private void dataGet(){}
    public void getEventList(){}
    public void editEventList(){}

    private void saveEvent(){
        comment = editComment.getText().toString();
        if (comment != null && !comment.equals("") && comment.length() < 20) {
            id=getUUID();
            newEvent = new Event(habit, comment, id, UID);

            if (image != null ) {
                newEvent.setImg(image);
            }else {newEvent.setImg(img);}
            if(location != null){
                newEvent.setLocation(location);
            }

            ElasticsearchEvent.AddEventTask task=new ElasticsearchEvent.AddEventTask();
            task.execute(newEvent);



            EventList.add(newEvent);

            Toast.makeText(getApplicationContext(), " Add a new event",Toast.LENGTH_SHORT).show();

            saveInFile();

            finish();
        }else{
            Toast.makeText(getApplicationContext(), "Comment cannot be empty or longer than 20 characters!",Toast.LENGTH_SHORT).show();}

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();


        if(EventList == null){

            EventList = new ArrayList<Event>();
        }

        Drawable drawable = this.getResources().getDrawable(R.drawable.download);
        img = ((BitmapDrawable) drawable).getBitmap();
        imageView.setImageBitmap(img);






    }




    public static String getUUID(){

        UUID uuid=UUID.randomUUID();
        String uid = uuid.toString();
        return uid;
//        UUID uuid= UUID.randomUUID();
//        String str = uuid.toString();
//        String uuidStr=str.replace("-", "");
//        return uuidStr;
    }

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

//    private void loadFromFile1() {
//        try {
//            FileInputStream fis=openFileInput(FILENAME);
//            BufferedReader in= new BufferedReader(new InputStreamReader(fis));
//            Type habitListType = new TypeToken<ArrayList<Habit>>(){}.getType();
//            Gson gson= new Gson();
//            HabitList = gson.fromJson(in, habitListType);
//
//
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            Toast.makeText(getApplicationContext(), "No records Find",Toast.LENGTH_SHORT).show();
//
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            throw new RuntimeException();
//        }
//    }

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