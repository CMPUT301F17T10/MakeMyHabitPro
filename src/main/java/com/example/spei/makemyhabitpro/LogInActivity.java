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
    private ArrayList<User> registerd;
    private EditText emailText;
    private EditText passText;
    public static final String EXTRA_MESSAGE = "com.example.MMHP.USERDATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Button LoginButton = (Button) findViewById(R.id.Login_button);
        Button SignupButton = (Button) findViewById(R.id.Signup_button);
        emailText = (EditText) findViewById(R.id.Email);
        passText = (EditText) findViewById(R.id.logPass);


        LoginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                if(!emailText.getText().toString().equals("") && !passText.getText().toString().equals("")) {
                    String Input_email=emailText.getText().toString();
                    String Input_pass=passText.getText().toString();
                    local_user=elogin(Input_email,Input_pass);

                    // 4.a check if username and password are OK
                    if(local_user != null )
                    {
                        // Not null and OK, launch the activity
                        Gson gson = new Gson();
                        String user=gson.toJson(local_user);
                        Toast.makeText(getApplicationContext(), "Welcome",Toast.LENGTH_SHORT).show();
                        updateUser();
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
                    ElasticsearchUser.RegUserTask RegUserTask
                            = new ElasticsearchUser.RegUserTask();
                    RegUserTask.execute(reg);
                    Toast.makeText(getApplicationContext(), "Signed Up",Toast.LENGTH_SHORT).show();

                } else {
                    // The fields are not filled.
                    // Display an error message like "Please enter username/password
                    Toast.makeText(getApplicationContext(), "Email and Password Are needed",Toast.LENGTH_SHORT).show();


                }
            }

        });
    }
    protected void updateUser(){

        ElasticsearchUser.DeleteUser update= new ElasticsearchUser.DeleteUser();
        if (local_user!=null){
            update.execute(local_user.getName());
            ElasticsearchUser.RegUserTask RegUserTask
                    = new ElasticsearchUser.RegUserTask();
            RegUserTask.execute(local_user);
        }
    }
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
    private User elogin(String Email,String Pass){
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
    private void to_Main(String u){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_MESSAGE,u);
        startActivityForResult(intent,RESULT_OK);
    }
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();

    }
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
}
