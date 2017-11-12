package com.example.mmhp;

import android.app.DatePickerDialog;
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

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.List;
import java.util.Date;

/**
 * Created by BBC on 2017/11/11.
 */



@RequiresApi(api = Build.VERSION_CODES.N)
public class HabitActivity extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static final String EXTRA_MESSAGE = "com.example.MMHP.USERDATA";

    private String user_data;
    private User local_user;
    public String UID;
    private HabitR habit;
    private HabitR.HabitEntity habitEntity;
    private int i;

    private HabitR.HabitEntity.UserHabitEntity.PlanEntity plan;
    private String jsonString;


    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);


        EditText titleEt = (EditText) findViewById(R.id.titleEt);
        final EditText startDateEt = (EditText) findViewById(R.id.startDate);
        Intent Mainintent = getIntent();
        user_data = Mainintent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        titleEt.setText(user_data);
        Gson gson = new Gson();
        local_user = gson.fromJson(user_data, User.class);
        UID = local_user.getUid();
        titleEt.setText(UID.toString());

        try {
            jsonString = readFile("habit.json");
        } catch (Exception e) {
            jsonString = "";
        }
        habit=gson.fromJson(jsonString,HabitR.class);
        if(jsonString!="") {
            List<HabitR.HabitEntity> habitEntityList = habit.getHabit();
            for(i=0;i<habitEntityList.size();i++){


            }
        }
        // ****setup calendar picker dialog
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayofMonth = calendar.get(Calendar.DAY_OF_MONTH);
        final DatePickerDialog dateDialog = new DatePickerDialog(this, R.style.DateTime, new DatePickerDialog.OnDateSetListener() {
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
                String[] types = getResources().getStringArray(R.array.type);
                startDateEt.setText(types[i].toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }));



    }

    public void addH(View view){

        Intent intent = new Intent(HabitActivity.this, AddHabitActivity.class);
        // startActivity(intent);
        intent.putExtra(EXTRA_MESSAGE,user_data);
        startActivityForResult(intent,RESULT_OK);
    }

    public void habitB(View view){
        Intent intent = new Intent(HabitActivity.this, MainActivity.class);

        startActivity(intent);
    }

    public String readFile(String fileName) throws Exception{
        byte[] buff=new byte[1024];
        int size=0;
        FileInputStream fileInputStream=openFileInput(fileName);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        while ((size=fileInputStream.read(buff))>0){
            byteArrayOutputStream.write(buff,0,size);
        }
        return byteArrayOutputStream.toString();
    }

}
