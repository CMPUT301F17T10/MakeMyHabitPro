package com.example.spei.makemyhabitpro;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Map<String,String> messages;
    private HistoryListActivity his;
    private MapActivity m;
    private EventListActivity el;
    private FriendActivity F;
//    private HabitActivity h;
    private TodoActivity T;
    private User local_user;
    private String user_data;
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Toast.makeText(getApplicationContext(), local_user.to_string(),Toast.LENGTH_SHORT).show();
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
    }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.MyHabit) {
            HabitA();
            // Handle the camera action
        } else if (id == R.id.Todo) {
            Todo();
        } else if (id == R.id.Eventlist) {
            Eventl();
        } else if (id == R.id.Histortlist) {
            Historylist();
        } else if (id == R.id.Friend) {

        } else if (id == R.id.Map) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void dataget(){}
    public void Historylist(){
        Intent intent = new Intent(this, HistoryListActivity.class);
        intent.putExtra(EXTRA_MESSAGE,user_data);
        startActivityForResult(intent,RESULT_OK);
    }

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
}
