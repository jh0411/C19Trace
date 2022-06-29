package com.example.c19trace.Home.SymptomTest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.c19trace.Others.NavigationActivity;
import com.example.c19trace.R;

public class LowRiskActivity extends AppCompatActivity {

    Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_low_risk);

        done = findViewById(R.id.btn_lowResultDone);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LowRiskActivity.this, NavigationActivity.class);
                startActivity(intent);
            }
        });
    }
}