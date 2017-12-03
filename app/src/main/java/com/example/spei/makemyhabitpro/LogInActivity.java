/*
 * Copyright (c) 2017Team X, CMPUT301, University of Alberta-All Rights Reserved
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact spei@ualberta.ca
 */

package com.example.spei.makemyhabitpro;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import java.util.UUID;

public class LogInActivity extends AppCompatActivity {
    private User local_user;
    private MainActivity main;
    private static final String FILENAME="USER.SAV";
    private static final String HabitFIle="Habits.SAV";
    private static final String EVENTFILE="Eventl.SAV";
    private ArrayList<User> registerd;
    private EditText emailText;
    private EditText passText;
    public static final String EXTRA_MESSAGE = "com.example.MMHP.USERDATA";
    private boolean conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Button LoginButton = (Button) findViewById(R.id.Login_button);
        Button SignupButton = (Button) findViewById(R.id.Signup_button);
        emailText = (EditText) findViewById(R.id.Email);
        passText = (EditText) findViewById(R.id.logPass);
        Connection c= new Connection(this);
        conn=c.isConnected();

        LoginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                if(!emailText.getText().toString().equals("") && !passText.getText().toString().equals("")) {
                    String Input_email=emailText.getText().toString();
                    String Input_pass=passText.getText().toString();
                    if (conn == true) {
                        local_user = elogin(Input_email, Input_pass);
                    }else{
                        local_user = logIn(Input_email,Input_pass);
                    }

                    if(local_user != null )
                    {
                        // Not null and OK, launch the activity
                        Gson gson = new Gson();
                        String user=gson.toJson(local_user);
                        Toast.makeText(getApplicationContext(), "Welcome",Toast.LENGTH_SHORT).show();
                        //online
                        if (conn){
                            updateUser();
                            getHabits();
                            getEvents();
                        }
                        to_Main(user);


                    } else {
                        Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // The fields are not filled.
                    // Display an error message like "Please enter username/password
                    Toast.makeText(getApplicationContext(), "Email and Password Are needed",Toast.LENGTH_SHORT).show();


                }
            }

        });
        SignupButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                if(!emailText.getText().toString().equals("") && !passText.getText().toString().equals("")) {
                    String Input_email=emailText.getText().toString();
                    String Input_pass=passText.getText().toString();

                    UUID uuid=UUID.randomUUID();
                    String uid = uuid.toString();

                    User reg= new User(Input_email,uid,Input_pass);
                    registerd.add(reg);
                    saveInFile();
                    if (conn){
                    ElasticsearchUser.RegUserTask RegUserTask
                            = new ElasticsearchUser.RegUserTask();
                    RegUserTask.execute(reg);}
                    Toast.makeText(getApplicationContext(), "Signed Up",Toast.LENGTH_SHORT).show();


                } else {
                    // The fields are not filled.
                    // Display an error message like "Please enter username/password
                    Toast.makeText(getApplicationContext(), "Email and Password Are needed",Toast.LENGTH_SHORT).show();


                }
            }

        });
    }

    /**
     * this function is used to updateUser for exp and last log in
     */
    protected void updateUser(){

        ElasticsearchUser.DeleteUser update= new ElasticsearchUser.DeleteUser();
        if (local_user!=null){
            update.execute(local_user.getName());
            ElasticsearchUser.RegUserTask RegUserTask
                    = new ElasticsearchUser.RegUserTask();
            RegUserTask.execute(local_user);
        }
    }

    /**
     * offline login, just check local file for users
     * @param Email the user name for log in
     * @param Pass pass word
     * @return null if failed(wrong pass or user not exist), user if find and match
     */
    private User logIn(String Email,String Pass){

        if (registerd.isEmpty()){
            return null;
        }
        for (User u:registerd
                ) {
            if (u.getName().equals(Email)){
                if(u.log_in(Pass)==1|u.log_in(Pass)==2){
                    return u;
                }
            }
        }
        return null;
    }

    /**
     * online version of log in
     * @param Email the user name for log in
     * @param Pass pass word
     * @return null if failed(wrong pass or user not exist), user if find and match
     */
    private User elogin(String Email,String Pass){
        if (!existedUser(Email)){
            return null;
        }
        ElasticsearchUser.GetUserTask get= new ElasticsearchUser.GetUserTask();
        User u;
        get.execute(Email);
        try {
            u = get.get();
        } catch (Exception e) {
            return null;
        }
        if (u.getName().equals(Email)){
            if(u.log_in(Pass)==1|u.log_in(Pass)==2){
                return u;
            }
        }
        return null;
    }

    /**
     * check the elastic search for user existence
     * @param name user name
     * @return true for find false for not find
     */
    private boolean existedUser (String name) {
        ElasticsearchUser.IsExist isExist = new ElasticsearchUser.IsExist();
        isExist.execute(name);

        try {
            if (isExist.get()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * start the main activity and pass the user data
     * @param u user data
     */
    private void to_Main(String u){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_MESSAGE,u);
        startActivityForResult(intent,RESULT_OK);


    }
    /**
     * If online, update the habits and events here
     */
    public void onDestroy() {

        super.onDestroy();
        if(conn & local_user!=null){
            updateHabits();
            updateEvents();
        }

    }

    /**
     * update the local habits file into elastic search
     */
    private void updateHabits(){
        String query= "{\n"+
                "\"query\":"+ "{\n"+
                "\"match\":"+"{\n"+"\"userId\":\""+local_user.getUid()+"\""+
                "}\n"+
                "}\n"+
                "}\n";
        ElasticsearchHabit.GetHabits getHabittask= new ElasticsearchHabit.GetHabits();
        getHabittask.execute(query);
        ArrayList<Habit> h = new ArrayList<Habit>();
        try {
            h = getHabittask.get();
        }catch (Exception e){
            h = new ArrayList<Habit>();
        }

        ArrayList<Habit> habits= new ArrayList<Habit>();
        try {
            FileInputStream fis=openFileInput(HabitFIle);
            BufferedReader in= new BufferedReader(new InputStreamReader(fis));
            Gson gson= new Gson();


            Type listType =new TypeToken<ArrayList<Habit>>(){}.getType();
            habits = gson.fromJson(in, listType);
            if (habits==null){
                habits= new ArrayList<Habit>();
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            habits= new ArrayList<Habit>();
        }


        for (Habit H:h){
            ElasticsearchHabit.DeleteHabitTask d=new ElasticsearchHabit.DeleteHabitTask();
            d.execute(H);
        }
        for (Habit ha:habits){
            ElasticsearchHabit.AddHabitTask a=new ElasticsearchHabit.AddHabitTask();
            a.execute(ha);
        }
    }

    /**
     * update Events for elastic search
     */
    private void updateEvents(){
        String query= "{\n"+
                "\"query\""+":"+ "{\n"+
                "\"match_all\""+":"+"{\n"+
                "}\n"+
                "}\n"+
                "}\n";
        ElasticsearchEvent.GetEvents getEvent= new ElasticsearchEvent.GetEvents();
        getEvent.execute(query);
        ArrayList<Event> h = new ArrayList<Event>();
        try {
            h = getEvent.get();
        }catch (Exception e){
            h = new ArrayList<Event>();
        }
        ArrayList<Event> events= new ArrayList<Event>();
        try {
            FileInputStream fis=openFileInput(EVENTFILE);
            BufferedReader in= new BufferedReader(new InputStreamReader(fis));
            Gson gson= new Gson();


            Type listType =new TypeToken<ArrayList<Event>>(){}.getType();
            events = gson.fromJson(in, listType);
            if (events==null){
                events= new ArrayList<Event>();
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            events= new ArrayList<Event>();
        }


        for (Event e:h){
            ElasticsearchEvent.DeleteEventTask d=new ElasticsearchEvent.DeleteEventTask();
            d.execute(e);
        }
        for (Event E:events){
            ElasticsearchEvent.AddEventTask a=new ElasticsearchEvent.AddEventTask();
            a.execute(E);
        }

    }

    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();

    }

    /**
     * save the user to local
     */
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);//goes stream based on filename
            BufferedWriter out = new BufferedWriter( new OutputStreamWriter(fos)); //create buffer writer
            Gson g = new Gson();
            Type listType =new TypeToken<ArrayList<User>>(){}.getType();
            g.toJson(registerd,listType,out);
            out.flush();

            fos.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * load the local users
     */
    private void loadFromFile() {
        try {
            FileInputStream fis=openFileInput(FILENAME);
            BufferedReader in= new BufferedReader(new InputStreamReader(fis));
            Gson gson= new Gson();


            Type listType =new TypeToken<ArrayList<User>>(){}.getType();
            registerd = gson.fromJson(in, listType);
            if (registerd==null){
                registerd= new ArrayList<User>();
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            registerd= new ArrayList<User>();
        }
    }

    /**
     * pull the habits from elastic search
     */
    public void getHabits(){
        String query= "{\n"+
            "\"query\":"+ "{\n"+
            "\"match\":"+"{\n"+"\"userId\":\""+local_user.getUid()+"\""+
            "}\n"+
        "}\n"+
        "}\n";
        ElasticsearchHabit.GetHabits getHabittask= new ElasticsearchHabit.GetHabits();
        getHabittask.execute(query);
        ArrayList<Habit> h = new ArrayList<Habit>();
        try {
            h = getHabittask.get();
        }catch (Exception e){
            h = new ArrayList<Habit>();
        }
        try{
        FileOutputStream fos = openFileOutput(HabitFIle, Context.MODE_PRIVATE);//goes stream based on filename
        BufferedWriter out = new BufferedWriter( new OutputStreamWriter(fos)); //create buffer writer
        Gson g = new Gson();
        Type listType =new TypeToken<ArrayList<Habit>>(){}.getType();
        g.toJson(h,listType,out);
        out.flush();

        fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * pull the events from elastic search
     */

    public void getEvents(){
        String query= "{\n"+
                "\"query\""+":"+ "{\n"+
                "\"match_all\""+":"+"{\n"+
                "}\n"+
                "}\n"+
                "}\n";
        ElasticsearchEvent.GetEvents getEvent= new ElasticsearchEvent.GetEvents();
        getEvent.execute(query);
        ArrayList<Event> h = new ArrayList<Event>();
        try {
            h = getEvent.get();
        }catch (Exception e){
            h = new ArrayList<Event>();
        }
        try{
            FileOutputStream fos = openFileOutput(EVENTFILE, Context.MODE_PRIVATE);//goes stream based on filename
            BufferedWriter out = new BufferedWriter( new OutputStreamWriter(fos)); //create buffer writer
            Gson g = new Gson();
            Type listType =new TypeToken<ArrayList<Event>>(){}.getType();
            g.toJson(h,listType,out);
            out.flush();

            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
