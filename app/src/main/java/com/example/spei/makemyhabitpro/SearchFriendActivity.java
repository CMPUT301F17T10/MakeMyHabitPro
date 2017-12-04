/*
 * Copyright (c) 2017Team X, CMPUT301, University of Alberta-All Rights Reserved
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact spei@ualberta.ca
 */

package com.example.spei.makemyhabitpro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class SearchFriendActivity extends AppCompatActivity {
    private User local_user;
    private String user_data;
    private ArrayList<User> friends;
    private ArrayAdapter<User> adapter;
    private String Select;
    public static final String EXTRA_MESSAGE = "com.example.MMHP.USERDATA";
    private EditText searchEt;
    private String searchTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friend);
        Intent Mainintent = getIntent();
        user_data = Mainintent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        Gson gson2 = new Gson();
        local_user = gson2.fromJson(user_data, User.class);
        ListView Lt = (ListView) findViewById(R.id.Searchlist);
        friends=new ArrayList<User>();
        this.adapter = new ArrayAdapter<User>(this,
                R.layout.list_item, friends);//adapter converts tweet to string
        Lt.setAdapter(adapter);
        Lt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Select=friends.get(i).getName();
            }
        });
        searchEt = (EditText) findViewById(R.id.searchUText);
        Button search = (Button)findViewById(R.id.SearchUser);
        search.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                searchTerm=searchEt.getText().toString();
                if(search()){
                adapter.notifyDataSetChanged();
                }else{
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "User doesnt exist",Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button send=(Button)findViewById(R.id.addfriend);
        send.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                User R_to = friends.get(0);
                R_to.request(local_user.getName());
                ElasticsearchUserController.DeleteUser d=new ElasticsearchUserController.DeleteUser();
                d.execute(R_to.getName());
                ElasticsearchUserController.RegUserTask r=new ElasticsearchUserController.RegUserTask();
                r.execute(R_to);
                end();
            }
        });
    }
    private void end(){
        Toast.makeText(getApplicationContext(), "Request Made",Toast.LENGTH_SHORT).show();
        this.finish();
    }
    private boolean search(){
        friends.clear();
        if (existedUser(searchTerm)){
            ElasticsearchUserController.GetUserTask g = new ElasticsearchUserController.GetUserTask();
            g.execute(searchTerm);
            try {
                friends.add(g.get());
            }catch (Exception e){
                return false;
            }
            return true;
        }
        return false;
    }
    private boolean existedUser (String name) {
        ElasticsearchUserController.IsExist isExist = new ElasticsearchUserController.IsExist();
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
