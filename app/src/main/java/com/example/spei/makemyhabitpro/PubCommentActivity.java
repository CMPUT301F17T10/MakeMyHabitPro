package com.example.spei.makemyhabitpro;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class PubCommentActivity extends AppCompatActivity {

    private static final String FILENAME="Eventl.SAV";
    private int position;
    private ArrayList<String> pub_commentList;
    private String pub_comment;
    private Event event;
    private ArrayList<Event> EventList;
    private ListView oldpubcomment;
    private EditText editComment;
    private ArrayAdapter<String> adapter;
    private Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_comment);

        Intent intent = getIntent();
        position = intent.getIntExtra("position02",0);

        connection = new Connection(this);

        oldpubcomment = (ListView)findViewById(R.id.pub_commentList);

        editComment = (EditText)findViewById(R.id.pub_comment);


        Button add = (Button) findViewById(R.id.pub_commentAdd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pub_comment = editComment.getText().toString();
                pub_commentList.add(pub_comment);

                if (connection.isConnected()){
                    ElasticsearchEvent.DeleteEventTask deleteEventTask = new ElasticsearchEvent.DeleteEventTask();
                    deleteEventTask.execute(event);
                    ElasticsearchEvent.AddEventTask addEventTask = new ElasticsearchEvent.AddEventTask();
                    addEventTask.execute(event);
                }else{
                    connection.editEvent(event);
                }



                adapter.notifyDataSetChanged();
                saveInFile();

            }
        });

        Button back = (Button) findViewById(R.id.pub_commentBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();

//        if(EventList == null){
//
//            EventList = new ArrayList<Event>();
//        }

        if (connection.isConnected()){
            ElasticsearchEvent.GetEvents getEvents=new ElasticsearchEvent.GetEvents();
            getEvents.execute("");
            try {
                EventList = getEvents.get();
            }catch (Exception e){
            }
        }



        event = EventList.get(position);
        pub_commentList = event.getPub_comment();

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, pub_commentList);

        oldpubcomment.setAdapter(adapter);



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
