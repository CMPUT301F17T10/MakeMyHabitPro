package com.example.mmhp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Intent intent = getIntent();
        Button button1=(Button)findViewById(R.id.fBack);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Intent intent=new Intent();
                intent.putExtra("MESSAGE",1);
                setResult(2,intent);
                finish();//finishing activity
            }
        });
    }
}
