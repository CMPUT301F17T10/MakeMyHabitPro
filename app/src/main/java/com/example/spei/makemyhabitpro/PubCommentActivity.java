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
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_comment);

        Intent intent = getIntent();
        position = intent.getIntExtra("position02",0);

        oldpubcomment = (ListView)findViewById(R.id.pub_commentList);

        EditText editComment = (EditText)findViewById(R.id.pub_comment);
        pub_comment = editComment.getText().toString();

        Button add = (Button) findViewById(R.id.pub_commentAdd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addComment();

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


        event = EventList.get(position);
        pub_commentList = event.getPub_comment();

        adapter = new ArrayAdapter<String>(this,
                R.layout.activity_pub_comment);

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
