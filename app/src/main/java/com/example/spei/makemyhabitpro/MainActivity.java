/*
 * Copyright (c) 2017Team X, CMPUT301, University of Alberta-All Rights Reserved
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact spei@ualberta.ca
 */

package com.example.spei.makemyhabitpro;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String FILENAME="Eventl.SAV";
    private Map<String,String> messages;
    private HistoryListActivity his;
    private EventListActivity el;
    private FriendActivity F;
//    private HabitActivity h;
    private TodoActivity T;
    private ArrayAdapter<Event> adapter;
    private User local_user;
    private String user_data;
    private ArrayList<Event> mainList;
    private ListView oldmailList;
    private String UID;
    private Connection connection;
    public static final String EXTRA_MESSAGE = "com.example.MMHP.USERDATA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        user_data=  intent.getStringExtra(LogInActivity.EXTRA_MESSAGE);
        Gson gson = new Gson();
        local_user=gson.fromJson(user_data,User.class);
        UID=local_user.getUid();

        connection = new Connection(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Toast.makeText(getApplicationContext(), local_user.toString(),Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), local_user.get_lvl(),Toast.LENGTH_SHORT).show();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                end();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        oldmailList = (ListView) findViewById(R.id.mainList);
        oldmailList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(MainActivity.this, EventDetailActivity.class);

                Event clickEvent = mainList.get(position);
                String clickId = clickEvent.getId();
                intent.putExtra("eventId", clickId);
                intent.putExtra("UID", UID);
                startActivityForResult(intent,RESULT_OK);
            }
        });
    }

    /**
     * end this activity
     */
    private void end(){
        this.finish();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * According to different click, go to different activity
     * @param item the menu
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.MyHabit) {
            HabitA();

        } else if (id == R.id.Todo) {
            Todo();
        } else if (id == R.id.Eventlist) {
            Eventl();
        } else if (id == R.id.Histortlist) {
            Historylist();
        } else if (id == R.id.Friend) {
            Friend();
        } else if (id == R.id.Map) {
            tMap();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * start the Historylist Activity
     */
    public void Historylist(){
        Intent intent = new Intent(this, HistoryListActivity.class);
        intent.putExtra(EXTRA_MESSAGE,user_data);
        startActivityForResult(intent,RESULT_OK);
    }

    /**
     * start the Event
     */

    public void Eventl(){
        Intent intent = new Intent(this, EventListActivity.class);
        intent.putExtra(EXTRA_MESSAGE,user_data);
        startActivityForResult(intent,RESULT_OK);
    }

    public void HabitA(){
        Intent intent = new Intent(this, HabitActivity.class);
        intent.putExtra(EXTRA_MESSAGE,user_data);

        startActivityForResult(intent,RESULT_OK);
        //    Intent intent=new Intent(MainActivity.this,HabitActivity.class);
        //    startActivity(intent);
    }

    public void Todo(){
        Intent intent = new Intent(this, TodoActivity.class);
        intent.putExtra(EXTRA_MESSAGE,user_data);
        startActivityForResult(intent,RESULT_OK);
    }
    private void Friend(){
        Intent intent=new Intent(this,FriendActivity.class);
        intent.putExtra(EXTRA_MESSAGE,user_data);
        startActivityForResult(intent,RESULT_OK);
    }
    private void tMap(){
        Intent intent=new Intent(this,MapActivity.class);
        startActivityForResult(intent,RESULT_OK);

    }
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

//        if (connection.isConnected()){
//            mainList = new ArrayList<Event>();
//
//
//            ElasticsearchEvent.GetEvents getEvents=new ElasticsearchEvent.GetEvents();
//            getEvents.execute("");
//            try {
//                mainList = getEvents.get();
//            }catch (Exception e){
//            }
//        }else {
//            loadFromFile();
//
//            if (mainList == null) {
//
//                mainList = new ArrayList<Event>();
//            }
//        }

        loadFromFile();

        if (mainList == null) {

            mainList = new ArrayList<Event>();
        }

        Collections.sort(mainList, new Comparator<Event>() {
            @Override
            public int compare(Event e1, Event e2) {
                return e2.getHabitDate().compareTo(e1.getHabitDate());
            }
        });



        adapter = new ArrayAdapter<Event>(this,
                R.layout.list_item, mainList);

        oldmailList.setAdapter(adapter);


    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();


            Type listType = new TypeToken<ArrayList<Event>>() {
            }.getType();

            mainList = gson.fromJson(in, listType);


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            mainList = new ArrayList<Event>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
}
