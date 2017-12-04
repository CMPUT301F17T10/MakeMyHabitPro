package com.example.spei.makemyhabitpro;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DetailHabitActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_detail_habit);

        //   Intent Mainintent = getIntent();
        //   user_data = Mainintent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        Intent intent = getIntent();
        user_data = intent.getStringExtra("user_data").toString();
        titleSelect = intent.getStringExtra("title");

        Button saveBt = (Button) findViewById(R.id.saveBt);
        Button deleteBt = (Button) findViewById(R.id.deleteBt);

        Gson gson2 = new Gson();
        local_user = gson2.fromJson(user_data, User.class);
        UID = local_user.getUid();

        titleEt = (EditText) findViewById(R.id.titleEt);
        startDateEt = (EditText) findViewById(R.id.startDate);
        reasonEt = (EditText) findViewById(R.id.reasonEt);

        titleEt.setText("titleSelect:" + titleSelect);
        startDateEt.setText(user_data);

        //create type spinner
        final Spinner typeSp = (Spinner) findViewById(R.id.typeSpinner);
        final String[] types = {"study", "work", "social", "entertainment", "sport", "other"};
        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSp.setAdapter(adapterType);
        typeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int typeIndex, long l) {
                typeSelect = types[typeIndex];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        jsonString = readFile().toString();

        if (jsonString.length() > 0) {
            Gson gson1 = new Gson();
            Type habitListType = new TypeToken<ArrayList<Habit>>() {
            }.getType();
            ArrayList<Habit> habitList = gson1.fromJson(jsonString, habitListType);

            //           ArrayList<String> titles =new ArrayList<String>();
            String str = "";

            for (titleIndex = 0; titleIndex < habitList.size(); titleIndex++) {
                if (UID.equals(habitList.get(titleIndex).getUserId()) && titleSelect.equals(habitList.get(titleIndex).getTitle())) {
                    habit = habitList.get(titleIndex);
                    break;
                }
            }
            detailEt = (EditText) findViewById(R.id.commonEt);
            detailEt.setText(str.toString());
            setView(habit);

            String startDate = habit.getStartDate().toString();
            String nowDate = getToday();
            //  EditText readonEt=(EditText)findViewById(R.id.reasonEt);
            //   EditText conmmenEt=(EditText)findViewById(R.id.commonEt);
            //   EditText startEt=(EditText)findViewById(R.id.startDate);
            //    readonEt.setText(startDate);
            //   conmmenEt.setText(nowDate);


            boolean dateEarly = true;

            try {
                dateEarly = compare(startDate, nowDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            int days = 0;
            int week = 0;
            if (dateEarly) {
                List dataList = getDatesBetweenTwoDate(startDate, nowDate);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

                for (int i = 0; i < dataList.size(); i++) {
                    week=getWeek(sdf.format(dataList.get(i)).toString());
                    switch (week){
                        case 0:
                            if(habit.getSun())
                                days++;
                            break;
                        case 1:
                            if(habit.getMon())
                                days++;
                            break;
                        case 2:
                            if(habit.getTue())
                                days++;
                            break;
                        case 3:
                            if(habit.getWen())
                                days++;
                            break;
                        case 4:
                            if(habit.getThu())
                                days++;
                            break;
                        case 5:
                            if(habit.getFri())
                                days++;
                            break;
                        case 6:
                            if(habit.getSat())
                                days++;
                            break;
                        default:
                            break;
                    }

                }
                TextView messageTv=(TextView)findViewById(R.id.messageTv);
                messageTv.setText("Should do "+String.valueOf(days)+" times");
                TextView finishTimes=(TextView) findViewById(R.id.finishTimes);
                finishTimes.setText("Finish "+String.valueOf(habit.getFinished())+" times");

                //    EditText titleEt=(EditText)findViewById(R.id.titleEt);
                //    EditText reasonEt=(EditText)findViewById(R.id.reasonEt);
                //    EditText comEt=(EditText)findViewById(R.id.commonEt);
                //    titleEt.setText("first day is "+sdf.format(dataList.get(0)).toString());
                //   reasonEt.setText("last day is "+sdf.format(dataList.get(dataList.size()-1)).toString());


            }



        }
    }
    //button

    /**
     * delete the habit from file
     * @param view
     */
    public void deleteHabit(View view){
        Gson gson = new Gson();
        Type habitListType = new TypeToken<ArrayList<Habit>>(){}.getType();
        ArrayList<Habit> habitList = gson.fromJson(jsonString, habitListType);
        habitList.remove(titleIndex);
        jsonString=gson.toJson(habitList);
        writeFile(jsonString);
        DetailHabitActivity.this.finish();
        Intent intent = new Intent(this, HabitActivity.class);
        intent.putExtra(EXTRA_MESSAGE,user_data);
        startActivityForResult(intent,RESULT_OK);

    }

    /**
     * save the habit after edit
     * @param view
     */
    public void saveHabit(View view){
        Gson gson3 = new Gson();
        Type habitListType = new TypeToken<ArrayList<Habit>>(){}.getType();
        ArrayList<Habit> habitList = gson3.fromJson(jsonString, habitListType);

        habit=getHabit(UID);
        habitList.set(titleIndex,habit);
        jsonString=gson3.toJson(habitList);
        writeFile(jsonString);
        Intent intent = new Intent(this, HabitActivity.class);
        intent.putExtra(EXTRA_MESSAGE,user_data);
        startActivityForResult(intent,RESULT_OK);
        this.finish();
    }

    /**
     * call the add habit activity
     * @param view
     */
    public void addHabit(View view){
        Intent intent = new Intent(this, AddHabitActivity.class);
        intent.putExtra(EXTRA_MESSAGE,user_data);
        startActivityForResult(intent,RESULT_OK);
    }

    /**
     * select the date as YYYY/MM/DD
     * @param view
     */
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

    /**
     * back to the habit activity
     * @param view
     */
    public void habitB(View view){
        Intent intent = new Intent(this, HabitActivity.class);
        intent.putExtra(EXTRA_MESSAGE,user_data);
        startActivityForResult(intent,RESULT_OK);
        this.finish();
    }
    //       /button

    /**
     * save to file
     * @param content
     */
    private void writeFile(String content){
        try{
            FileOutputStream fos=openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(content.getBytes());
            fos.close();
        }catch(Exception e){
            e.printStackTrace();}
    }

    /**
     * load from file
     * @return
     */
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

    /**
     * get habit title, reason,common, start date and the week plan
     * @param uer
     * @return
     */
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
        titleEt=(EditText)findViewById(R.id.titleEt);
        titleEt.setText(habit.getTitle());
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

    /**
     * get the date from start date to today and check how many times should this habit do from start date to today
     * @param beginDate
     * @param endDate
     * @return
     */
    public static List getDatesBetweenTwoDate(String beginDate, String endDate) {
        Date beginD=null;
        Date endD = null;
        DateFormat fmt =new SimpleDateFormat("yyyy/MM/dd");
        try {
            beginD = fmt.parse(beginDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try{
            endD = fmt.parse(endDate);
        }catch (ParseException e){
            e.printStackTrace();
        }

        List listDate = new ArrayList();
        listDate.add(beginD);
        Calendar cal = Calendar.getInstance();

        cal.setTime(beginD);
        boolean bContinue = true;
        while (bContinue) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            if (endD.after(cal.getTime())) {
                listDate.add(cal.getTime());
            } else {
                break;
            }
        }
        listDate.add(endD);
        return listDate;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getToday(){
        android.icu.text.SimpleDateFormat formatter=new android.icu.text.SimpleDateFormat("yyyy/MM/dd/");
        Date curDate =new Date(System.currentTimeMillis());
        String today= formatter.format(curDate);
        return today;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private  boolean compare(String time1, String time2) throws java.text.ParseException {
        android.icu.text.SimpleDateFormat sdf=new android.icu.text.SimpleDateFormat("yyyy/MM/dd");
        Date a=sdf.parse(time1);
        Date b=sdf.parse(time2);
        if(a.before(b))
            return true;
        else
            return false;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private int getWeek(String pTime) {
        int Week =0;
        android.icu.text.SimpleDateFormat format = new android.icu.text.SimpleDateFormat("yyyy/MM/dd");
        android.icu.util.Calendar c = android.icu.util.Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (android.net.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return c.get(android.icu.util.Calendar.DAY_OF_WEEK)-1;
    }

}