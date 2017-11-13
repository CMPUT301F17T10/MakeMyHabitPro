package com.example.mmhp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


public class AddHabitActivity extends AppCompatActivity {

    private static final String FILENAME="Habits.SAV";
    private String user_data;
    private User local_user;
    private HabitList Hlist;
    private ArrayList<Habit> HabitList;
    private String title;
    private String reason;
    private String comment;
    private String startDt;

  //  private List<HabitR.HabitEntity> habitEntityList;
  //  private List<HabitR.HabitEntity.UserHabitEntity> userHabitEntityList;
    public String UID;
    private String jsonString;
    private int i;
    private String typeSelect;

   // private HabitR.HabitEntity habitEntity;
    //    private HabitR.HabitEntity.UserHabitEntity.PlanEntity planEntity;
   // private HabitR.HabitEntity.UserHabitEntity userHabitEntity;


    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);

        final EditText startDateEt =(EditText)findViewById(R.id.startDate);
        EditText commEt=(EditText)findViewById(R.id.commonEt);
        EditText titleEt=(EditText)findViewById(R.id.titleEt);
        EditText reasonEt=(EditText)findViewById(R.id.reasonEt);

        title = titleEt.getText().toString();
        reason = reasonEt.getText().toString();
        comment = commEt.getText().toString();



        Intent Mainintent = getIntent();
        user_data = Mainintent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        commEt.setText(user_data);
        Gson gson = new Gson();
        local_user = gson.fromJson(user_data, User.class);
        UID = local_user.getUid();
        //titleEt.setText(UID);



        //reasonEt.setText(jsonString);




        // ****setup calendar picker dialog
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayofMonth = calendar.get(Calendar.DAY_OF_MONTH);
        final DatePickerDialog dateDialog = new DatePickerDialog(this,  new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                startDt = Integer.toString(year);
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
              //  startDateEt.setText(startDt);
                Toast.makeText(getApplicationContext(), year + "-" + (month + 1) + "-" + dayOfMonth, Toast.LENGTH_LONG).show();
            }
        }, year, month, dayofMonth);

        startDateEt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (MotionEvent.ACTION_UP == event.getAction()) {
                    dateDialog.show();                    ;
                }
                return true;
            }
        });

        //create type spinner
        Spinner typeSp=(Spinner)findViewById(R.id.typeSpinner);
        typeSp.setOnItemSelectedListener((new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] types = {"study","work","social","entertainment","sport","other"};
                //String[] types = getResources().getStringArray(R.array.type);
                typeSelect=types[i].toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }));

        saveNewHabit();
        saveInFile();
    }


    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();


        if(Hlist!=null){
            HabitList = Hlist.getHabits();
        }else{
            HabitList = new ArrayList<Habit>();
        }



    }

    public void saveNewHabit(){



        Habit habit=new Habit(title,reason, comment, typeSelect, startDt);
      //  List<HabitR.HabitEntity> habitEntityList=habit.getHabit();
    //    HabitR.HabitEntity habitEntity=new HabitR.HabitEntity();
    //    List<HabitR.HabitEntity.UserHabitEntity> userHabitEntityList=habitEntity.getUserHabit();
   //     HabitR.HabitEntity.UserHabitEntity userHabitEntity=new HabitR.HabitEntity.UserHabitEntity();
   //     HabitR.HabitEntity.UserHabitEntity.PlanEntity planEntity=new HabitR.HabitEntity.UserHabitEntity.PlanEntity();

//        try {
//            FileInputStream fis = openFileInput("Habit.sav");
//            byte[] buffer = new byte[fis.available()];
//            fis.read(buffer);
//            jsonString = new String(buffer);
//        }catch (Exception e) {
//            e.printStackTrace();
//            jsonString="";
//        }
        CheckBox sunB=(CheckBox)findViewById(R.id.sunCheck);
        CheckBox monB=(CheckBox)findViewById(R.id.monCheck);
        CheckBox tueB=(CheckBox)findViewById(R.id.tueCheck);
        CheckBox wenB=(CheckBox)findViewById(R.id.wenCheck);
        CheckBox thuB=(CheckBox)findViewById(R.id.thuCheck);
        CheckBox friB=(CheckBox)findViewById(R.id.friCheck);
        CheckBox satB=(CheckBox)findViewById(R.id.satCheck);

//        EditText titleT=(EditText)findViewById(R.id.titleEt);
//        EditText reasonT=(EditText)findViewById(R.id.reasonEt);
//        EditText commonT=(EditText)findViewById(R.id.commonEt);
//        EditText startDateT=(EditText)findViewById(R.id.startDate);


        habit.setSun(sunB.isChecked());
        habit.setMon(monB.isChecked());
        habit.setTue(tueB.isChecked());
        habit.setWen(wenB.isChecked());
        habit.setThu(thuB.isChecked());
        habit.setFri(friB.isChecked());
        habit.setSat(satB.isChecked());

        HabitList.add(habit);

//        userHabitEntity.setTitle(titleT.getText().toString());
//        userHabitEntity.setReason(reasonT.getText().toString());
//        userHabitEntity.setDetail(commonT.getText().toString());
//        userHabitEntity.setStartDate(startDateT.getText().toString());
//        userHabitEntity.setType(typeSelect);
//        userHabitEntity.setPlan(planEntity);


//        Gson gson=new Gson();
//        if(jsonString==""){
//            userHabitEntityList.add(userHabitEntity);
//            habitEntity.setUserId(UID);
//            habitEntity.setUserHabit(userHabitEntityList);
//            habitEntityList.add(habitEntity);
//            habit.setHabit(habitEntityList);
//        }
//        else{
//            habit=gson.fromJson(jsonString,HabitR.class);
          //  habitEntityList=habit.getHabit();
           // int flag=0;
            //     for(i=0;i<habitEntityList.size();i++){
            //          if(habitEntityList.get(i).getUserId()==UID){
            //              flag=1;
            //              break;
            //       }
            //     if(flag==1){
            //            userHabitEntityList.add(userHabitEntity);
            //             habitEntityList.get(i).setUserHabit(userHabitEntityList);
            //            habit.setHabit(habitEntityList);
            //    }
            //  else{
            //       userHabitEntityList.add(userHabitEntity);
            //       habitEntity.setUserId(UID);
            //        habitEntity.setUserHabit(userHabitEntityList);
            //        habitEntityList.add(habitEntity);
            //      habit.setHabit(habitEntityList);
            //      }
        }



        //      }
//        jsonString=gson.toJson(habit);
//        try{
//            FileOutputStream fos=openFileOutput("Habit.sav",MODE_PRIVATE);
//            fos.write(jsonString.getBytes());
//            fos.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();


            Hlist = gson.fromJson(in, HabitList.class);


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            HabitList = new ArrayList<Habit>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(Hlist, EventList.class ,out);
            out.flush();
            fos.close();
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }


}
