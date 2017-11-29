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

public class DoneHabitListActivity extends AppCompatActivity {

    private static final String FILENAME="Habits.SAV";
    private Habit habit;
    private String UID;
    private ArrayList<Habit> HabitList;
    private ArrayList<Habit> myHabitList;
    private ArrayAdapter<Habit> adapter;
    private ListView oldDoneHabitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_habit_list);

        oldDoneHabitList = (ListView) findViewById(R.id.habitlist);

        Intent intent = getIntent();
        UID =  intent.getStringExtra("UID");

        oldDoneHabitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(DoneHabitListActivity.this, AddHabitEventActivity.class);
                habit = myHabitList.get(position);

                Gson gson = new Gson();
                String habitS=gson.toJson(habit);

                intent.putExtra("UID", UID);
                intent.putExtra(EXTRA_MESSAGE,habitS);
                startActivityForResult(intent,RESULT_OK);
                back();


            }
        });
    }
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
        loadFromFile();
//        if(Hlist!=null){
//            HabitList=Hlist.getHabits();
//        }else{
//            HabitList=new ArrayList<Habit>();
//        }

        //filter(HabitList);

        myHabitList = new ArrayList<Habit>();
        for (Habit habit : HabitList) {
            if (UID.equals(habit.getUserId())) {
                myHabitList.add(habit);
            }

        }

        adapter = new ArrayAdapter<Habit>(this,
                R.layout.list_item, myHabitList);//adapter converts tweet to string
        oldDoneHabitList.setAdapter(adapter);

    }
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