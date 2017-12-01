/*
 * Copyright (c) 2017Team X, CMPUT301, University of Alberta-All Rights Reserved
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact spei@ualberta.ca
 */

package com.example.spei.makemyhabitpro;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.ParseException;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodoActivity extends AppCompatActivity {

    private ArrayList<Habit> TodoList;
    private String user_data;
    private User local_user;
    private Habit habit;
    private String UID;
    private static final String FILENAME="Habits.SAV";
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        Intent Mainintent = getIntent();
        user_data = Mainintent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        Gson gson2 = new Gson();
        local_user = gson2.fromJson(user_data, User.class);
        UID = local_user.getUid();

        List titles=new ArrayList<String>();
        TextView remarkTv=(TextView)findViewById(R.id.remark);
        try{titles=getData();} catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        if(titles.size()==0){
            remarkTv.setText("Today no habit!");
        }
        else{
            remarkTv.setText("Today habit!");
            ListView titleLt=(ListView)findViewById(R.id.titleList);
            titleLt.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1,titles));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<String> getData() throws java.text.ParseException {
        List<String> data = new ArrayList<String>();
        int week=getWeek(getToday());

        Gson gson1 = new Gson();
        local_user = gson1.fromJson(user_data, User.class);
        UID = local_user.getUid();

        String jsonString = readFile().toString();
        if(jsonString.length() > 0){
            Gson gson2 = new Gson();
            Type habitListType = new TypeToken<ArrayList<Habit>>(){}.getType();
            ArrayList<Habit> habitList = gson1.fromJson(jsonString, habitListType);
            for(int i=0;i<habitList.size();i++){
                String today=getToday();
                Boolean earlyDate=true;
                try{
                    earlyDate=compare(today,habitList.get(i).getStartDate());}
                catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
                if(UID.equals(habitList.get(i).getUserId())&&!earlyDate){
                    switch(week){
                        case 0:
                            if(habitList.get(i).getSun()){
                                data.add(habitList.get(i).getTitle());
                            }
                            break;
                        case 1:
                            if(habitList.get(i).getMon()){
                                data.add(habitList.get(i).getTitle());
                            }
                            break;
                        case 2:
                            if(habitList.get(i).getTue()){
                                data.add(habitList.get(i).getTitle());
                            }
                            break;
                        case 3:
                            if(habitList.get(i).getWen()){
                                data.add(habitList.get(i).getTitle());
                            }
                            break;
                        case 4:
                            if(habitList.get(i).getThu()){
                                data.add(habitList.get(i).getTitle());
                            }
                            break;
                        case 5:
                            if(habitList.get(i).getFri()){
                                data.add(habitList.get(i).getTitle());
                            }
                            break;
                        case 6:
                            if(habitList.get(i).getSat()){
                                data.add(habitList.get(i).getTitle());
                            }
                            break;
                        default:
                            break;

                    }
                }

            }
        }
        return data;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private int getWeek(String pTime) {
        int Week =0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return c.get(Calendar.DAY_OF_WEEK)-1;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private  boolean compare(String time1, String time2) throws java.text.ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
        Date a=sdf.parse(time1);
        Date b=sdf.parse(time2);
        if(a.before(b))
            return true;
        else
            return false;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getToday(){
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy/MM/dd/");
        Date curDate =new Date(System.currentTimeMillis());
        String today= formatter.format(curDate);
        return today;
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
    public void backM(View view){
        this.finish();
    }







}
