package com.example.spei.makemyhabitpro;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HabitActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.MMHP.USERDATA";

    private ArrayList<Habit> HabitList;

    private String typeSelect;
    private String startDt;
    private int i;
    private String titleSelect;

    private String UID;
    private static final String FILENAME="Habits.SAV";
    private String jsonString="";

    private String user_data;
    private User local_user;
    private Habit habit;
    //   private ArrayList<Habit> habitList;

    private EditText titleEt;
    private EditText reasonEt;
    private EditText detailEt;
    private EditText startDateEt;
    private CheckBox sunCh,monCh,tueCh,wenCh,thuCh,friCh,satCh;
    private int titleIndex;
    private int habitListIndex;
    final List list=new ArrayList();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);

        Intent Mainintent = getIntent();
        user_data = Mainintent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        startDateEt = (EditText) findViewById(R.id.startDate);
        detailEt = (EditText) findViewById(R.id.commonEt);
        titleEt = (EditText) findViewById(R.id.titleEt);
        reasonEt = (EditText) findViewById(R.id.reasonEt);

        Button saveBt = (Button) findViewById(R.id.saveBt);
        Button deleteBt = (Button) findViewById(R.id.deleteBt);

        Gson gson2 = new Gson();
        local_user = gson2.fromJson(user_data, User.class);
        UID = local_user.getUid();

        //create type spinner
        final Spinner typeSp = (Spinner) findViewById(R.id.typeSpinner);
        final String[] types = {"study", "work", "social", "entertainment", "sport", "other"};
        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
        adapterType .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSp.setAdapter(adapterType );
        typeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int typeIndex, long l) {
                typeSelect = types[typeIndex];
                //         Toast.makeText(HabitActivity.this, "your choice:"+types[i], Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        jsonString = readFile().toString();

        Spinner titleSp=(Spinner)findViewById(R.id.titleSpinner);
        String user="";

        if (jsonString.length() > 0) {
            startDateEt=(EditText)findViewById(R.id.startDate);

            Gson gson1 = new Gson();
            Type habitListType = new TypeToken<ArrayList<Habit>>(){}.getType();
            final ArrayList<Habit> habitList = gson1.fromJson(jsonString, habitListType);
            final ArrayList<String> titles =new ArrayList<String>();
            for(i=0;i<habitList.size();i++){
                user=user+"+"+habitList.get(i).getUserId();
                if(UID.equals(habitList.get(i).getUserId())){
                    titles.add(habitList.get(i).getTitle());
                    list.add(i);
                }
            }
            if(titles.size()==0){
                saveBt.setEnabled(false);
                deleteBt.setEnabled(false);
            }

            startDateEt=(EditText)findViewById(R.id.startDate);
            //   startDateEt.setText(UID);
            //   detailEt=(EditText)findViewById(R.id.commonEt);
            //  detailEt.setText(user.toString());

            ArrayAdapter<String> adapterTitleType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, titles);
            adapterTitleType .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            titleSp.setAdapter(adapterTitleType );
            titleSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    titleSelect = titles.get(i);
                    titleIndex=i;
                    habit=habitList.get((Integer) list.get(i));
                    setView(habit);
                    Toast.makeText(HabitActivity.this,  String.valueOf(list.get(i)), Toast.LENGTH_LONG).show();
                    String user="";
                    for(int  j=0;j<6;j++){
                        if(types[j].equals(habit.getType())){
                            typeSp.setSelection(j);
                            break;
                        }
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });



            //       ArrayList<Habit> habitList = gson.fromJson(jsonString, new TypeToken<ArrayList<Habit>>() {
            //      }.getType());
            //     Habit habit=new Habit();
            //     habit.setDetail(detailEt.getText().toString());
            //      habitList.set(i,habit);
        }
        else{
            saveBt.setEnabled(false);
            deleteBt.setEnabled(false);
        }

    }
    //button
    public void deleteHabit(View view){

        Gson gson = new Gson();
        Type habitListType = new TypeToken<ArrayList<Habit>>(){}.getType();
        final ArrayList<Habit> habitList = gson.fromJson(jsonString, habitListType);
        int habitListIndex=(Integer)list.get(titleIndex);
        habitList.remove(habitListIndex);
        jsonString=gson.toJson(habitList);
        writeFile(jsonString);
        Intent intent = new Intent(this, HabitActivity.class);
        intent.putExtra(EXTRA_MESSAGE,user_data);
        startActivityForResult(intent,RESULT_OK);

    }
    public void saveHabit(View view){
        Gson gson3 = new Gson();
        Type habitListType = new TypeToken<ArrayList<Habit>>(){}.getType();
        final ArrayList<Habit> habitList = gson3.fromJson(jsonString, habitListType);
        int habitListIndex=(Integer)list.get(titleIndex);
        habit=getHabit(UID);
        habitList.set(habitListIndex,habit);
        jsonString=gson3.toJson(habitList);



        writeFile(jsonString);

        Intent intent = new Intent(this, HabitActivity.class);
        intent.putExtra(EXTRA_MESSAGE,user_data);
        startActivityForResult(intent,RESULT_OK);



    }
    public void addHabit(View view){
        Intent intent = new Intent(this, AddHabitActivity.class);
        intent.putExtra(EXTRA_MESSAGE,user_data);
        startActivityForResult(intent,RESULT_OK);
    }
    //       /button
    public void selectDate(View view){
        //create date picker
        Calendar calendar = Calendar.getInstance();
        Dialog dialog = null;
        DatePickerDialog.OnDateSetListener dateListener =
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker,
                                          int year, int month, int dayOfMonth) {
                        EditText editText =(EditText) findViewById(R.id.startDate);
                        //Calendar月份是从0开始,所以month要加1
                        String startDt=Integer.toString(year);
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

                        editText.setText(startDt.toString());
                    }
                };
        dialog = new DatePickerDialog(this,
                dateListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();

    }
    private void writeFile(String content){
        try{
            FileOutputStream fos=openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(content.getBytes());
            fos.close();
        }catch(Exception e){
            e.printStackTrace();}
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
    public Habit getHabit(String uer) {
        Habit habit = new Habit();

        habit.setTitle(titleSelect.toString());
        reasonEt = (EditText) findViewById(R.id.reasonEt);
        habit.setReason(reasonEt.getText().toString());
        detailEt = (EditText) findViewById((R.id.commonEt));
        habit.setDetail(detailEt.getText().toString());
        startDateEt = (EditText) findViewById(R.id.startDate);
        habit.setStartDate(startDateEt.getText().toString());

        sunCh = (CheckBox) findViewById(R.id.sunCheck);
        habit.setSun(sunCh.isChecked());
        monCh = (CheckBox) findViewById(R.id.monCheck);
        habit.setMon(monCh.isChecked());
        tueCh = (CheckBox) findViewById(R.id.tueCheck);
        habit.setTue(tueCh.isChecked());
        wenCh = (CheckBox) findViewById(R.id.wenCheck);
        habit.setWen(wenCh.isChecked());
        thuCh = (CheckBox) findViewById(R.id.thuCheck);
        habit.setThu(thuCh.isChecked());
        friCh = (CheckBox) findViewById(R.id.friCheck);
        habit.setFri(friCh.isChecked());
        satCh = (CheckBox) findViewById(R.id.satCheck);
        habit.setSat(satCh.isChecked());


        habit.setUserId(UID);
        habit.setType(typeSelect);

        Date curDate = new Date(System.currentTimeMillis());
        habit.setLastActive(curDate);
        return habit;
    }
    public void setView(Habit habit){
        reasonEt = (EditText) findViewById(R.id.reasonEt);
        reasonEt.setText(habit.getReason());
        detailEt = (EditText) findViewById((R.id.commonEt));
        detailEt.setText(habit.getDetail());
        startDateEt = (EditText) findViewById(R.id.startDate);
        startDateEt.setText(habit.getStartDate());

        sunCh = (CheckBox) findViewById(R.id.sunCheck);
        sunCh.setChecked(habit.getSun());
        monCh = (CheckBox) findViewById(R.id.monCheck);
        monCh.setChecked(habit.getMon());
        tueCh = (CheckBox) findViewById(R.id.tueCheck);
        tueCh.setChecked(habit.getTue());
        wenCh = (CheckBox) findViewById(R.id.wenCheck);
        wenCh.setChecked(habit.getWen());
        thuCh = (CheckBox) findViewById(R.id.thuCheck);
        thuCh.setChecked(habit.getThu());
        friCh = (CheckBox) findViewById(R.id.friCheck);
        friCh.setChecked(habit.getFri());
        satCh = (CheckBox) findViewById(R.id.satCheck);
        satCh.setChecked(habit.getSat());


        //   habit.setUserId(UID);
        //    habit.setType(typeSelect);

        //   Date curDate = new Date(System.currentTimeMillis());

    }







}