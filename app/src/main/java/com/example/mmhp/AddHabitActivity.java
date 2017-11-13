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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class AddHabitActivity extends AppCompatActivity {

    private static final String FILENAME="Habits.SAV";
    private String user_data;
    private User local_user;
    private HabitList Hlist;
    //  private ArrayList<Habit> HabitList;
    private String userId;
    private String title;
    private String reason;
    private String comment;
    private String startDt;
    private ArrayList<Habit> HabitList;
    private  Habit habit=new Habit();
    private HabitList habitList=new HabitList(habit);
    private  String str;

    // private Date lastActive;


    public String UID;
    private String typeSelect;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);

        final EditText startDateEt =(EditText)findViewById(R.id.startDate);
        EditText commEt=(EditText)findViewById(R.id.commonEt);
        EditText titleEt=(EditText)findViewById(R.id.titleEt);
        EditText reasonEt=(EditText)findViewById(R.id.reasonEt);
        Button saveBt = (Button)findViewById(R.id.saveBt);


        title = titleEt.getText().toString();
        reason = reasonEt.getText().toString();
        comment = commEt.getText().toString();



        Intent Mainintent = getIntent();
        user_data = Mainintent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        commEt.setText(user_data);
        Gson gson = new Gson();
        local_user = gson.fromJson(user_data, User.class);
        UID = local_user.getUid();


//        // ****setup calendar picker dialog
//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int dayofMonth = calendar.get(Calendar.DAY_OF_MONTH);
//        final DatePickerDialog dateDialog = new DatePickerDialog(this,  new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                startDt = Integer.toString(year);
//                if (month + 1 < 10) {
//                    startDt = startDt + "/" + "0" + Integer.toString(month + 1);
//                } else {
//                    startDt = startDt + "/" + Integer.toString(month + 1);
//                }
//                if (dayOfMonth < 10) {
//                    startDt = startDt + "/" + "0" + Integer.toString(dayOfMonth);
//                } else {
//                    startDt = startDt + "/" + Integer.toString(dayOfMonth);
//                }
//                startDateEt.setText(startDt);
//                //    Toast.makeText(getApplicationContext(), year + "-" + (month + 1) + "-" + dayOfMonth, Toast.LENGTH_LONG).show();
//            }
//        }, year, month, dayofMonth);
//
//        startDateEt.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                if (MotionEvent.ACTION_UP == event.getAction()) {
//                    dateDialog.show();                    ;
//                }
//                return true;
//            }
//        });

        //create type spinner
        Spinner typeSp=(Spinner)findViewById(R.id.typeSpinner);

        final String[] types = {"study","work","social","entertainment","sport","other"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSp.setAdapter(adapter);
        typeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(AddHabitActivity.this, "type:"+types[i], Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });



        //       saveNewHabit();
//        saveInFile();
    }


    // @Override
    //  protected void onStart() {
    //      // TODO Auto-generated method stub
    //     super.onStart();
    //     loadFromFile();


    //       if(Hlist!=null){
    //          HabitList = Hlist.getHabits();
    //       }else{
    //           HabitList = new ArrayList<Habit>();
    //       }



    //  }

    public void saveNewHabit(View view) throws FileNotFoundException {

        // loadFromFile();



        CheckBox sunB=(CheckBox)findViewById(R.id.sunCheck);
        CheckBox monB=(CheckBox)findViewById(R.id.monCheck);
        CheckBox tueB=(CheckBox)findViewById(R.id.tueCheck);
        CheckBox wenB=(CheckBox)findViewById(R.id.wenCheck);
        CheckBox thuB=(CheckBox)findViewById(R.id.thuCheck);
        CheckBox friB=(CheckBox)findViewById(R.id.friCheck);
        CheckBox satB=(CheckBox)findViewById(R.id.satCheck);



        habit.setSun(sunB.isChecked());
        habit.setMon(monB.isChecked());
        habit.setTue(tueB.isChecked());
        habit.setWen(wenB.isChecked());
        habit.setThu(thuB.isChecked());
        habit.setFri(friB.isChecked());
        habit.setSat(satB.isChecked());



        EditText commEd=(EditText)findViewById(R.id.commonEt);
        EditText dateEd=(EditText)findViewById(R.id.startDate);
        EditText titleEd=(EditText)findViewById(R.id.titleEt);
        EditText reasonEd=(EditText)findViewById(R.id.reasonEt);


        habit.setUserId(UID);
        habit.setTitle(titleEd.getText().toString());
        habit.setReason(reasonEd.getText().toString());
        habit.setDetail(commEd.getText().toString());
        habit.setStartDate(dateEd.getText().toString());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        habit.setLastActive(simpleDateFormat);

        try{
            str=readFile(FILENAME);
        }
        catch (Exception e){

        }
        Gson gson =new Gson();

        //    habitList=gson.fromJson(str,habitList) ;




        //   habitList.habits.add(habit);
        //    Gson gson =new Gson();
        String str=gson.toJson(habitList);

        try{
            saveFile(FILENAME,str);
        }
        catch (Exception e){

        }



        commEd.setText(str.toString());


        //  saveInFile();
    }


//    private void loadFromFile() {
    //           Hlist = gson.fromJson(in, HabitList.class);
//

    //       } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            HabitList = new ArrayList<Habit>();
    //       }// catch (IOException e) {
    // TODO Auto-generated catch block
    //    throw new RuntimeException();
    // }
    //   }

    public void saveFile(String fileName,String fileContent)throws Exception {
        FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
        fileOutputStream.write(fileContent.getBytes());
    }
    public String readFile(String fileName) throws Exception{
        byte[] buff=new byte[1024];
        int size=0;
        FileInputStream fileInputStream=openFileInput(fileName);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        while ((size=fileInputStream.read(buff))>0){
            byteArrayOutputStream.write(buff,0,size);
        }
        return byteArrayOutputStream.toString();
    }

    private void saveInFile(String jsonString) {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(habitList, EventList.class ,out);
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










