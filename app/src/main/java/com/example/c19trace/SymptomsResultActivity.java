package com.example.c19trace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SymptomsResultActivity extends AppCompatActivity {

    TextView riskStatus, riskDescp;
    Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms_result);

        riskStatus = findViewById(R.id.tv_riskStatusText);
        riskDescp = findViewById(R.id.tv_riskDescription);

        done = findViewById(R.id.btn_resultDone);

//        Bundle b = getIntent().getExtras();
//        int result = b.getInt("score", 0);
//
//        if(result > 3){
//            riskStatus.setText("High Risk");
//            riskDescp.setText("Please stay at home at the moment and make sure you take good care of yourself until you have no symptoms at all");
//        } else {
//            riskStatus.setText("Low Risk");
//            riskDescp.setText("Continue to stay safe and practice the SOPs to keep yourself safe from COVID-19!");
//        }

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SymptomsResultActivity.this, HomeFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
            }
        });
    }
}