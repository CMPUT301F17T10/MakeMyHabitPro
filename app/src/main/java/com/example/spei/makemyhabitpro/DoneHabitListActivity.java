/*
 * Copyright (c) 2017Team X, CMPUT301, University of Alberta-All Rights Reserved
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact spei@ualberta.ca
 */

package com.example.spei.makemyhabitpro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

import static com.example.spei.makemyhabitpro.MainActivity.EXTRA_MESSAGE;

/**
 * This class shows a habit list for user
 * user need to choose a habit to create a new event
 * @author spei
 *
 * @since 1.0
 * @see EventListActivity
 * @see java.io.BufferedReader
 * @see AddHabitEventActivity
 */
public class DoneHabitListActivity extends AppCompatActivity {

    private static final String FILENAME="Habits.SAV";
    private Habit habit;
    private String UID;
    private ArrayList<Habit> HabitList;
    private ArrayList<Habit> myHabitList;
    private ArrayAdapter<Habit> adapter;
    private ListView oldDoneHabitList;
    private String user_data;
    private Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_habit_list);

        oldDoneHabitList = (ListView) findViewById(R.id.habitlist);

        Intent intent = getIntent();
        user_data=  intent.getStringExtra("user_data");


        connection = new Connection(this);

        oldDoneHabitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(DoneHabitListActivity.this, AddHabitEventActivity.class);
                habit = myHabitList.get(position);

                Gson gson = new Gson();
                String habitS=gson.toJson(habit);

                intent.putExtra("User", user_data);
                intent.putExtra(EXTRA_MESSAGE,habitS);
                startActivityForResult(intent,RESULT_OK);
                back();


            }
        });
    }

    /**
     * back to EventList Activity
     */
    public void back(){
        this.finish();
    }
//    private void dataGet(){}
//    public void getHabitList() {}
//    public void showList() {}
//    public void sortHabitList(){}

//    private void filter(ArrayList<Habit> HabitList){
//
//        DoneHabitList = new ArrayList<Habit>();
//
//        if (HabitList != null) {
//            for (Habit h : HabitList) {
//                if (h.getDone() == true) {
//                    DoneHabitList.add(h);
//                }
//            }
//        }
//    }


    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();


        myHabitList = new ArrayList<Habit>();

        //check the connection
//        if (connection.isConnected()) {
//
//            String habit_query = "{\n" +
//                    "  \"query\": { \n" +
//                    " \"match\" : { \"userId\" : \"" + UID + "\" }}\n" +
//                    "}";
//            ElasticsearchHabit.GetHabits getHabits = new ElasticsearchHabit.GetHabits();
//            getHabits.execute(habit_query);
//            try {
//                myHabitList = getHabits.get();
//            } catch (Exception e) {
//            }
//        }else{
//            loadFromFile();
//
//            if(HabitList == null){
//
//                HabitList = new ArrayList<Habit>();
//            }
//
//            for (Habit habit : HabitList) {
//                if (UID.equals(habit.getUserId())) {
//                    myHabitList.add(habit);
//                }
//
//            }
//        }


        loadFromFile();

        if(HabitList == null){

            HabitList = new ArrayList<Habit>();
        }

        for (Habit habit : HabitList) {
            if (UID.equals(habit.getUserId())) {
                myHabitList.add(habit);
            }

        }


        adapter = new ArrayAdapter<Habit>(this,
                R.layout.list_item, myHabitList);//adapter converts tweet to string
        oldDoneHabitList.setAdapter(adapter);

    }

    /**
     * lode habit list from local
     */
    private void loadFromFile() {
        try {
            FileInputStream fis=openFileInput(FILENAME);
            BufferedReader in= new BufferedReader(new InputStreamReader(fis));
            Type habitListType = new TypeToken<ArrayList<Habit>>(){}.getType();
            Gson gson= new Gson();
            HabitList = gson.fromJson(in, habitListType);


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            Toast.makeText(getApplicationContext(), "No records Find",Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}