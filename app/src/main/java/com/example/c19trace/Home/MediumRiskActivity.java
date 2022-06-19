package com.example.c19trace.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.c19trace.Others.NavigationActivity;
import com.example.c19trace.R;

public class MediumRiskActivity extends AppCompatActivity {

    Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medium_risk);

        done = findViewById(R.id.btn_mediumResultDone);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediumRiskActivity.this, NavigationActivity.class);
                startActivity(intent);
            }
        });
    }
}