package com.example.mmhp;

import android.app.DatePickerDialog;
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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class HabitActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.MMHP.USERDATA";

    private String user_data;
    private Habit j;
    private ArrayList<Habit> habitList;
    private AddHabitActivity A;
    private DetailHabitActivity D;
    private String typeSelect;
    private String startDt;
    private int i;
    private String titleSelect;
    private int titleIndex;
    private String UID;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);

        Intent Mainintent = getIntent();
        user_data = Mainintent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        EditText commEt=(EditText)findViewById(R.id.commonEt) ;
        commEt.setText(user_data);

        final EditText startDateEt =(EditText)findViewById(R.id.startDate);

        final EditText commEd=(EditText)findViewById(R.id.commonEt);
        EditText dateEd=(EditText)findViewById(R.id.startDate);
        final EditText titleEd=(EditText)findViewById(R.id.titleEt);
        final EditText reasonEd=(EditText)findViewById(R.id.reasonEt);

        //create type spinner
        final Spinner typeSp=(Spinner)findViewById(R.id.typeSpinner);

        final String[] types = {"study","work","social","entertainment","sport","other"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSp.setAdapter(adapter);
        typeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(HabitActivity.this, "你点击的是:"+types[i], Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });


//////create title spinner
//        titleSelect="";
//        Gson gson=new Gson();
//        HabitList habitList=gson.fromJson(titleSelect,HabitList.class);
//
//
//        final List<String> list = new ArrayList<String>();
//        Spinner titleSp=(Spinner)findViewById(R.id.titleSpinner);
//        for( i=0;i<ArrayList<habit>.size();i++) {
//
//            if(UID==habitList.get(i).getUserId()){
//            list.add(habitList.get(i).getTitle());
//        }
//
////    //    final HabitListn.HabitEntity.PlanEntity[] planList = {new HabitBean.HabitEntity.PlanEntity()};
//
//        //create title spinner
//
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, list);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        titleSp.setAdapter(dataAdapter);
//        titleSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view,
//                                       int pos, long id) {
//                titleSelect = list.get(pos);
//                titleIndex = pos;
//                Toast.makeText(HabitActivity.this, "你点击的是:" + list.get(pos) + "是第" + Integer.toString(pos) + "条记录", Toast.LENGTH_LONG).show();
//                titleEd.setText(habitList.get(titleIndex).getTitle());
//                reasonEd.setText(habitList.get(titleIndex).getReason());
//                commEd.setText(habitList.get(titleIndex).getDetail());
//                startDateEt.setText(habitList.get(titleIndex).getStartDate());
//
//                CheckBox sunB=(CheckBox)findViewById(R.id.sunCheck);
//                CheckBox monB=(CheckBox)findViewById(R.id.monCheck);
//                CheckBox tueB=(CheckBox)findViewById(R.id.tueCheck);
//                CheckBox wenB=(CheckBox)findViewById(R.id.wenCheck);
//                CheckBox thuB=(CheckBox)findViewById(R.id.thuCheck);
//                CheckBox friB=(CheckBox)findViewById(R.id.friCheck);
//                CheckBox satB=(CheckBox)findViewById(R.id.satCheck);
//                SpinnerAdapter apsAdapter = typeSp.getAdapter();
//                int k = apsAdapter.getCount();
//                for (int i = 0; i < k; i++) {
//                    if (habitList.get(titleIndex).getType().equals(apsAdapter.getItem(i).toString())) {
//                        typeSp.setSelection(i);
//                        break;
//                    }
//                }
//                sunB.setChecked(habitList.get(titleIndex).getSun());
//                monB.setChecked(habitList.get(titleIndex).getMon());
//                tueB.setChecked(habitList.get(titleIndex).getTue());
//                wenB.setChecked(habitList.get(titleIndex).getWen());
//                thuB.setChecked(habitList.get(titleIndex).getThu());
//                friB.setChecked(habitList.get(titleIndex).getFri());
//                satB.setChecked(habitList.get(titleIndex).getSat());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
//
//



        // ****setup calendar picker dialog
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
//                //   Toast.makeText(getApplicationContext(), year + "-" + (month + 1) + "-" + dayOfMonth, Toast.LENGTH_LONG).show();
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
   }
    public void addHabit(View view){
        Intent intent = new Intent(this, AddHabitActivity.class);
        intent.putExtra(EXTRA_MESSAGE,user_data);
        startActivityForResult(intent,RESULT_OK);
    }
    public void showList(){}
    public void dateGet(){}

}