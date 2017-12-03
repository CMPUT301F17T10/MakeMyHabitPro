/*
 * Copyright (c) 2017Team X, CMPUT301, University of Alberta-All Rights Reserved
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact spei@ualberta.ca
 */

package com.example.spei.makemyhabitpro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class FriendActivity extends AppCompatActivity {
    private User local_user;
    private String user_data;
    private ArrayList<String> friends;
    private ArrayAdapter<String> adapter;
    private String Select;
    public static final String EXTRA_MESSAGE = "com.example.MMHP.USERDATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        Intent Mainintent = getIntent();
        user_data = Mainintent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        Gson gson2 = new Gson();
        local_user = gson2.fromJson(user_data, User.class);
        ListView Lt = (ListView) findViewById(R.id.Friendlist);
        friends=local_user.getFriends();
        this.adapter = new ArrayAdapter<String>(this,
                R.layout.list_item, friends);//adapter converts tweet to string
        Lt.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,friends));
        Lt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Select=friends.get(i);
            }
        });
        Button search = (Button)findViewById(R.id.Fsearchbutton);
        search.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                SearchFriend();
            }
        });
        Button request =(Button) findViewById(R.id.Request);
        request.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                setResult(RESULT_OK);
                RequestList();


            }
        });
        
    }
    public void friends_update(){
        friends.clear();
        ArrayList<String> f=local_user.getFriends();
        for(String s:f){
            friends.add(s);
        }


    };

    private void SearchFriend(){
        Intent intent=new Intent(this,SearchFriendActivity.class);
        intent.putExtra(EXTRA_MESSAGE,user_data);
        startActivityForResult(intent,RESULT_OK);

    }
    private void SearchNewFriend(){

    }
    private void FriendList(){

    }
    private void AddFriend(){

    }
    private void Display(){

    }
    private void RequestList(){
        Intent intent=new Intent(this,RequestListActivity.class);
        intent.putExtra(EXTRA_MESSAGE,user_data);
        startActivityForResult(intent,RESULT_OK);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==RESULT_OK){
            friends_update();
        }
    }
    @Override
    public void onStart(){
        super.onStart();
        if(!search(local_user.getName())){
            Toast.makeText(getApplicationContext(), "Sth wrong plz relog in",Toast.LENGTH_SHORT).show();
        }else{
            friends_update();
        }
        adapter.notifyDataSetChanged();

    }
    private void AcceptRequest(){

    }
    private void RefuseRequest(){

    }
    private boolean search(String searchTerm){

        if (existedUser(searchTerm)){
            ElasticsearchUser.GetUserTask g = new ElasticsearchUser.GetUserTask();
            g.execute(searchTerm);
            try {
                local_user=g.get();
            }catch (Exception e){
                return false;
            }
            return true;
        }
        return false;
    }
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
}

