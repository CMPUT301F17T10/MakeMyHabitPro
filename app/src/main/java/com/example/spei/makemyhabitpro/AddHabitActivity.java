/*
 * Copyright (c) 2017Team X, CMPUT301, University of Alberta-All Rights Reserved
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact spei@ualberta.ca
 */

package com.example.spei.makemyhabitpro;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AddHabitActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.MMHP.USERDATA";

    private String typeSelect;
    private String startDt;
    private int i;
    private String titleSelect;
    private int titleIndex;
    private String UID;
    private String title;
    private String reason;
    private String detail;
    private EditText startDate;
    private boolean Sun,Mon,Tue,Wen,Thu,Fri,Sat;
    private static final String FILENAME="Habits.SAV";
    private String jsonString;


    private String user_data;
    private User local_user;
    private ArrayList<Habit> habitList;

    private EditText titleEt;
    private EditText reasonEt;
    private EditText detailEt;
    private EditText startDateEt;
    private CheckBox sunCh,monCh,tueCh,wenCh,thuCh,friCh,satCh;
    private List<String> titles;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);

        Intent Mainintent = getIntent();
        user_data = Mainintent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        //   Button deleteBn=(Button)findViewById(R.id.deleteBt);
        Button saveBn=(Button)findViewById(R.id.saveBt);
        //type spinner
        Spinner typeSp = (Spinner) findViewById(R.id.typeSpinner);
        final String[] types = {"study", "work", "social", "entertainment", "sport", "other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSp.setAdapter(adapter);
        typeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(AddHabitActivity.this, "type:" + types[i], Toast.LENGTH_LONG).show();
                typeSelect = types[i].toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    public void saveNewHabit(View view){
        Gson gson = new Gson();
        local_user = gson.fromJson(user_data, User.class);
        UID = local_user.getUid();
        Habit habit=new Habit();

        jsonString=readFile();
        ArrayList<String> titles =new ArrayList<String>();

        if(jsonString.toString().length()>0){
            Gson gson1 = new Gson();
            Type habitListType = new TypeToken<ArrayList<Habit>>(){}.getType();
            ArrayList<Habit> habitList = gson1.fromJson(jsonString, habitListType);
            for(i=0;i<habitList.size();i++){
                if(UID.equals(habitList.get(i).getUserId())){
                    titles.add(habitList.get(i).getTitle());
                }
            }
            habitList.add(getHabit(UID));
            jsonString=gson1.toJson(habitList,habitListType);

        }
        else{
            habitList=new ArrayList<Habit>();
            habitList.add(getHabit(UID));
            Gson gson1=new Gson();
            jsonString=gson1.toJson(habitList);
        }



        if(titles.contains(titleEt.getText().toString())){
            Toast.makeText(AddHabitActivity.this,titleEt.getText().toString()+" already exist",Toast.LENGTH_LONG).show();
        }
        else if(titleEt.getText().toString().length()==0||startDateEt.getText().toString().length()==0||
                reasonEt.getText().toString().length()==0)
        {
            Toast.makeText(AddHabitActivity.this,"Some field is empty",Toast.LENGTH_LONG).show();
        }
        else if(!sunCh.isChecked()&&!monCh.isChecked()&&!tueCh.isChecked()&&!wenCh.isChecked()&&!thuCh.isChecked()&&!friCh.isChecked()
                &&!satCh.isChecked()){
            Toast.makeText(AddHabitActivity.this,"Check the plan, Pleas",Toast.LENGTH_LONG).show();

        }
        else{
            writeFile(jsonString);
            Intent intent = new Intent(this, HabitActivity.class);
            intent.putExtra(EXTRA_MESSAGE, user_data);
            startActivityForResult(intent, RESULT_OK);
            AddHabitActivity.this.finish();
        }
    }


    public void selectDate(View view){
        //create date picker
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        Dialog dialog = null;
        DatePickerDialog.OnDateSetListener dateListener =
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker,
                                          int year, int month, int dayOfMonth) {
                        EditText editText =(EditText) findViewById(R.id.startDate);
                        //Calendar月份是从0开始,所以month要加1
                        String startDt=Integer.toString(year);
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

                        editText.setText(startDt.toString());
                    }
                };
        dialog = new DatePickerDialog(this,
                dateListener,
                calendar.get(java.util.Calendar.YEAR),
                calendar.get(java.util.Calendar.MONTH),
                calendar.get(java.util.Calendar.DAY_OF_MONTH));
        dialog.show();

    }
    private void writeFile(String content){
        try{
            FileOutputStream fos=openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(content.getBytes());
            fos.close();
        }catch(Exception e){
            e.printStackTrace();}
    }
    private String readFile(){
        String str="";
        try{
            FileInputStream fis=openFileInput(FILENAME);
            byte[] buffer=new byte[fis.available()];
            fis.read(buffer);
            str= new String(buffer);
        }catch (Exception e){
            e.printStackTrace();
        }
        return str;
    }
    public Habit getHabit(String uer) {
        Habit habit = new Habit();
        titleEt = (EditText) findViewById(R.id.titleEt);
        habit.setTitle(titleEt.getText().toString());
        reasonEt = (EditText) findViewById(R.id.reasonEt);
        habit.setReason(reasonEt.getText().toString());
        detailEt = (EditText) findViewById((R.id.commonEt));
        habit.setDetail(detailEt.getText().toString());
        startDateEt = (EditText) findViewById(R.id.startDate);
        habit.setStartDate(startDateEt.getText().toString());

        sunCh = (CheckBox) findViewById(R.id.sunCheck);
        habit.setSun(sunCh.isChecked());
        monCh = (CheckBox) findViewById(R.id.monCheck);
        habit.setMon(monCh.isChecked());
        tueCh = (CheckBox) findViewById(R.id.tueCheck);
        habit.setTue(tueCh.isChecked());
        wenCh = (CheckBox) findViewById(R.id.wenCheck);
        habit.setWen(wenCh.isChecked());
        thuCh = (CheckBox) findViewById(R.id.thuCheck);
        habit.setThu(thuCh.isChecked());
        friCh = (CheckBox) findViewById(R.id.friCheck);
        habit.setFri(friCh.isChecked());
        satCh = (CheckBox) findViewById(R.id.satCheck);
        habit.setSat(satCh.isChecked());


        habit.setUserId(UID);
        habit.setType(typeSelect);

        Date curDate = new Date(System.currentTimeMillis());
        habit.setLastActive(curDate);
        return habit;
    }

}