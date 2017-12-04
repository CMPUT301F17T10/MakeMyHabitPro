/*
 * Copyright (c) 2017Team X, CMPUT301, University of Alberta-All Rights Reserved
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact spei@ualberta.ca
 */

package com.example.spei.makemyhabitpro;

import android.app.Activity;
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

import static android.widget.AbsListView.CHOICE_MODE_SINGLE;

public class RequestListActivity extends AppCompatActivity {
    private User local_user;
    private String user_data;
    private ArrayList<String> requests;
    private ArrayAdapter<String> adapter;
    private String Select;
    public static final String EXTRA_MESSAGE = "com.example.MMHP.USERDATA";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);
        Intent Mainintent = getIntent();
        user_data = Mainintent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        Gson gson2 = new Gson();
        local_user = gson2.fromJson(user_data, User.class);
        ListView Lt = (ListView) findViewById(R.id.rlist);
        requests=local_user.getRequest();
        this.adapter = new ArrayAdapter<String>(this,
                R.layout.list_item, requests);//adapter converts tweet to string

        Lt.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,requests));
        Lt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Select=requests.get(i);
            }
        });
        Lt.setChoiceMode(CHOICE_MODE_SINGLE);
        final Button accept = (Button)findViewById(R.id.Accept);
        accept.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                if (Select!=null){
                if(accept()){
                    Toast.makeText(getApplicationContext(), "Accepted",Toast.LENGTH_SHORT).show();
                    end();
                }else{
                    Toast.makeText(getApplicationContext(), "Sth goes wrong",Toast.LENGTH_SHORT).show();
                }
            }}
        });
        final Button reject = (Button)findViewById(R.id.reject);
        reject.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                if(Select!=null){
                reject(Select);
                Toast.makeText(getApplicationContext(), "Rejected",Toast.LENGTH_SHORT).show();
                end();
                }

            }
        });
        Connection c=new Connection(this);
        if (!c.isConnected()){
            reject.setEnabled(false);
            accept.setEnabled(false);
        }
        }
    private void end(){
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_OK, resultIntent);
        this.finish();
    }
    public void reject(String uid){
        local_user.reject(uid);
        ElasticsearchUserController.DeleteUser d1=new ElasticsearchUserController.DeleteUser();
        d1.execute(local_user.getName());
        ElasticsearchUserController.RegUserTask r1= new ElasticsearchUserController.RegUserTask();
        r1.execute(local_user);
    }
    private boolean accept(){
        if (existedUser(Select)){
            ElasticsearchUserController.GetUserTask g = new ElasticsearchUserController.GetUserTask();
            g.execute(Select);
            User f=null;
            try {
                f=g.get();
            }catch (Exception e){
                return false;
            }
            local_user.add_friend(Select);
            ElasticsearchUserController.DeleteUser d1=new ElasticsearchUserController.DeleteUser();
            d1.execute(local_user.getName());
            ElasticsearchUserController.RegUserTask r1= new ElasticsearchUserController.RegUserTask();
            r1.execute(local_user);
            f.add_friend(local_user.getName());
            ElasticsearchUserController.DeleteUser d2=new ElasticsearchUserController.DeleteUser();
            d2.execute(f.getName());
            ElasticsearchUserController.RegUserTask r2= new ElasticsearchUserController.RegUserTask();
            r2.execute(f);
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
