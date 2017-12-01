package com.example.spei.makemyhabitpro;



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
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
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

        Gson gson2 = new Gson();
        local_user = gson2.fromJson(user_data, User.class);
        UID = local_user.getUid();

        jsonString = readFile().toString();

        ListView habitLt = (ListView) findViewById(R.id.habitLt);

        ArrayList<String> titles = null;
        if (jsonString.length() > 0) {
            Gson gson1 = new Gson();
            Type habitListType = new TypeToken<ArrayList<Habit>>() {
            }.getType();
            final ArrayList<Habit> habitList = gson1.fromJson(jsonString, habitListType);
            titles = new ArrayList<String>();
            for (i = 0; i < habitList.size(); i++) {
                if (UID.equals(habitList.get(i).getUserId())) {
                    titles.add(habitList.get(i).getTitle());
                    list.add(i);
                }
            }
        }

        habitLt.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,titles));
        final ArrayList<String> finalTitles = titles;
        habitLt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle=new Bundle();
                bundle.putString("user_data",user_data);
                bundle.putString("title", finalTitles.get(i));
                Intent intent=new Intent(HabitActivity.this,DetailHabitActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                HabitActivity.this.finish();
            }
        });
    }
    //button
    public void addHabit(View view){
        Intent intent = new Intent(this, AddHabitActivity.class);
        intent.putExtra(EXTRA_MESSAGE,user_data);
        startActivityForResult(intent,RESULT_OK);
        HabitActivity.this.finish();
    }
    public void habitB(View view){
        HabitActivity.this.finish();
    }
    //       /button


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








}