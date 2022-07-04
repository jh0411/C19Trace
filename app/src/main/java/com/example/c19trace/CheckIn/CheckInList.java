package com.example.c19trace.CheckIn;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.c19trace.R;

public class CheckInList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_check_in_history);
    }

    public void back(View view){
        finish();
    }
}
