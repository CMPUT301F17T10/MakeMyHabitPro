package com.example.spei.makemyhabitpro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    private String htype = "study";
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
        Button FilterButton = (Button) findViewById(R.id.filter);
        FilterButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                to_filter();
            }

        });
        loadFromFile();
        Hlist=new HabitList(Habits.get(0));
        for (Habit h : Habits){
            Hlist.add(h);
        }
        this.adapter = new ArrayAdapter<Habit>(this,
                R.layout.list_item, Habits);//adapter converts tweet to string
        this.Habitlist.setAdapter(adapter);
        filter(htype);
        this.adapter.notifyDataSetChanged();

    }
    private void dataGet(){}

    private void filter(String t){
        ArrayList<Habit> f =new ArrayList<Habit>();
        ArrayList<Habit> H= this.Hlist.getHabits();
        Habits.clear();
        for (Habit h:H
                ) {
            if (h.getType().equals(t)){
                Habits.add(h);
            }
        }

    }
    private  void to_filter(){
        Intent fintent = new Intent(this, FilterActivity.class);
        startActivityForResult(fintent,2);
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
    private void search(String sth){}
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

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}