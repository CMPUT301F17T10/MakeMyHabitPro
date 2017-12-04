/*
 * Copyright (c) 2017Team X, CMPUT301, University of Alberta-All Rights Reserved
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact spei@ualberta.ca
 */

package com.example.spei.makemyhabitpro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class HistoryListActivity extends AppCompatActivity {
    private HabitList Hlist;
    private Habit h;
    private MapActivity Map;
    private User local_user;
    private String user_data;
    private static final String FILENAME="Habits.SAV";
    public String UID;
    private ArrayList<Habit> Habits;
    private ArrayAdapter<Habit> adapter;
    private ListView Habitlist;
    private String htype = "all";
    private String searchTerm="";
    private EditText searchEt;
    private String Hselect;
    public static final String USER_MESSAGE = "com.example.MMHP.USERDATA";
    public static final String HABIT_MESSAGE = "com.example.MMHP.HABITDATA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);
        Intent Mainintent = getIntent();
        user_data=  Mainintent.getStringExtra(LogInActivity.EXTRA_MESSAGE);
        Gson gson = new Gson();
        local_user=gson.fromJson(user_data,User.class);
        UID=local_user.getUid();
        Habitlist=(ListView) findViewById(R.id.Habitlist);
        searchEt = (EditText) findViewById(R.id.searchText);
        final Button searchBt = (Button) findViewById(R.id.searchbutton);
        searchBt.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                searchTerm=searchEt.getText().toString();
                filter(htype);
                adapter.notifyDataSetChanged();
            }
        });
        loadFromFile();
        Hlist=new HabitList(Habits.get(0));
        for (Habit h : Habits){
            Hlist.add(h);
        }
        Hlist.sort();
        this.adapter = new ArrayAdapter<Habit>(this,
                R.layout.list_item, Habits);//adapter converts tweet to string
        this.Habitlist.setAdapter(adapter);
        Habitlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Hselect=Habits.get(i).getTitle();
                startMap();
            }
        });
        filter(htype);
        this.adapter.notifyDataSetChanged();
        Spinner typeSp=(Spinner)findViewById(R.id.historyType);
        final String[] types = {"study", "work", "social", "entertainment", "sport", "other","all"};
        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
        adapterType .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSp.setAdapter(adapterType );
        typeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int typeIndex, long l) {
                htype = types[typeIndex];
                filter(htype);
                adapter.notifyDataSetChanged();
                //         Toast.makeText(HabitActivity.this, "your choice:"+types[i], Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }
    private void dataGet(){}

    private void filter(String t){
        if(!t.equals("all")){
        ArrayList<Habit> H= this.Hlist.getHabits();
        Habits.clear();
        for (Habit h:H
                ) {
            if (h.getType().equals(t)){
                search(h);
            }
        }}else{
            ArrayList<Habit> H= this.Hlist.getHabits();
            Habits.clear();
            for (Habit h:H
                    ) {

                search(h);

            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            htype =data.getStringExtra("MESSAGE");

        }
    }
    private void startMap(){
        Intent intent = new Intent(this,HisMapActivity.class);
        intent.putExtra(USER_MESSAGE,user_data);
        intent.putExtra(HABIT_MESSAGE,Hselect);
        startActivityForResult(intent,RESULT_OK);
    }
    private void search(Habit h){
        String tilte=h.getTitle();
        String detail=h.getDetail();
        if (tilte.contains(searchTerm)|detail.contains(searchTerm)){
            Habits.add(h);
        }
    }
    private void gotoMap(){}
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();


    }
    private void loadFromFile() {
        try {
            FileInputStream fis=openFileInput(FILENAME);
            BufferedReader in= new BufferedReader(new InputStreamReader(fis));
            Gson gson= new Gson();
            Type habitListType = new TypeToken<ArrayList<Habit>>(){}.getType();
            Habits = gson.fromJson(in, habitListType);


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            Toast.makeText(getApplicationContext(), "No records Find",Toast.LENGTH_SHORT).show();

        }
    }
}