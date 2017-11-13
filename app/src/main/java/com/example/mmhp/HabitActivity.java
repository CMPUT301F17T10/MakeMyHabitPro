package com.example.mmhp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * Created by BBC on 2017/11/11.
 */



@RequiresApi(api = Build.VERSION_CODES.N)
public class HabitActivity extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static final String EXTRA_MESSAGE = "com.example.MMHP.USERDATA";


    private static final String FILENAME="Habits.SAV";

    private String user_data;
    private User local_user;
    private HabitList Hlist;
    private ArrayList<Habit> HabitList;

    private String title;
    private String reason;
    private String comment;
    private String startDt;


    public String UID;
    private Habit habit;

   // private Habit.HabitEntity habitEntity;
    private int i;
    private String typeSelect;

   // private Habit.HabitEntity.UserHabitEntity.PlanEntity plan;
   // private String jsonString;


    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);


        final EditText startDateEt =(EditText)findViewById(R.id.startDate);
        EditText commEt=(EditText)findViewById(R.id.commonEt);
        EditText titleEt=(EditText)findViewById(R.id.titleEt);
        EditText reasonEt=(EditText)findViewById(R.id.reasonEt);

        title = titleEt.getText().toString();
        reason = reasonEt.getText().toString();
        comment = commEt.getText().toString();


//
        Intent Mainintent = getIntent();
        user_data = Mainintent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        titleEt.setText(user_data);
        Gson gson = new Gson();
        local_user = gson.fromJson(user_data, User.class);
        UID = local_user.getUid();
        //titleEt.setText(UID.toString());




        // ****setup calendar picker dialog
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayofMonth = calendar.get(Calendar.DAY_OF_MONTH);
        final DatePickerDialog dateDialog = new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String startDt = Integer.toString(year);
                if (month + 1 < 10) {
                    startDt = startDt + "/" + "0" + Integer.toString(month + 1);
                } else {
                    startDt = startDt + "/" + Integer.toString(month + 1);
                }
                if (dayOfMonth < 10) {
                    startDt = startDt + "/" + "0" + Integer.toString(dayOfMonth);
                } else {
                    startDt = startDt + "/" + Integer.toString(dayOfMonth);
                }
                startDateEt.setText(startDt);
                Toast.makeText(getApplicationContext(), year + "-" + (month + 1) + "-" + dayOfMonth, Toast.LENGTH_LONG).show();
            }
        }, year, month, dayofMonth);

        startDateEt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (MotionEvent.ACTION_UP == event.getAction()) {
                    dateDialog.show();
                    ;
                }
                return true;
            }
        });

        //create type spinner
        Spinner typeSp=(Spinner)findViewById(R.id.typeSpinner);
        typeSp.setOnItemSelectedListener((new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] types = {"study","work","social","entertainment","sport","other"};
                //String[] types = getResources().getStringArray(R.array.type);
              //  startDateEt.setText(types[i].toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }));


        addH();
        saveInFile();



    }
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();


        if(Hlist!=null){
            HabitList = Hlist.getHabits();
        }else{
            HabitList = new ArrayList<Habit>();
        }



    }


    public void addH(){

        Intent intent = new Intent(HabitActivity.this, AddHabitActivity.class);
        // startActivity(intent);
        intent.putExtra(EXTRA_MESSAGE,user_data);
        startActivityForResult(intent,RESULT_OK);
    }

    public void habitB(View view){
        Intent intent = new Intent(HabitActivity.this, MainActivity.class);

        startActivity(intent);
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();


            Hlist = gson.fromJson(in, HabitList.class);


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            HabitList = new ArrayList<Habit>();
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
            gson.toJson(Hlist, EventList.class ,out);
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
