package com.example.mmhp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DoneHabitListActivity extends AppCompatActivity {

    private static final String FILENAME="Habits.SAV";
    private HabitList Hlist;
    private ArrayList<Habit> HabitList;
    private ArrayList<Habit> DoneHabitList;
    private ArrayAdapter<Habit> adapter;
    private ListView oldDoneHabitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_habit_list);

        ListView oldDoneHabitList = (ListView) findViewById(R.id.donehabitlist);

        oldDoneHabitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(DoneHabitListActivity.this, AddHabitEventActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }

    private void dataGet(){}
    public void getHabitList() {}
    public void showList() {}
    public void sortHabitList(){}

    private void filter(ArrayList<Habit> HabitList){

        DoneHabitList = new ArrayList<Habit>();

        if (HabitList != null) {
            for (Habit h : HabitList) {
                if (h.getDone() == true) {
                    DoneHabitList.add(h);
                }
            }
        }
    }


    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();
        if(Hlist!=null){
            HabitList=Hlist.getHabits();
        }else{
            HabitList=new ArrayList<Habit>();
        }

        filter(HabitList);

        adapter = new ArrayAdapter<Habit>(this,
                R.layout.list_item, DoneHabitList);//adapter converts tweet to string
        oldDoneHabitList.setAdapter(adapter);

    }
    private void loadFromFile() {
        try {
            FileInputStream fis=openFileInput(FILENAME);
            BufferedReader in= new BufferedReader(new InputStreamReader(fis));
            Gson gson= new Gson();
            Hlist = gson.fromJson(in, HabitList.class);


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
