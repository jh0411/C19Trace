package com.example.c19trace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class SymptomsTestActivity extends AppCompatActivity {

    TextView question;
    Button option1, option2;

    ArrayList<SymptomsTestClass> symptomsTestClassArrayList;

    int count = 0, score = 0;

    SymptomsTestClass q0 = new SymptomsTestClass(
            "Are you exhibiting 2 or more symptoms such as Fever, Shivering, Body ache, Headache etc? ",
            "Yes",
            "No"
    );

    SymptomsTestClass q1 = new SymptomsTestClass(
            "Beside the above, are you exhibiting any of the symptoms such as Cough, Difficulty breathing, Loss of smell, Loss of taste",
            "Yes",
            "No"
    );

    SymptomsTestClass q2 = new SymptomsTestClass(
            "Have you attended any event/areas associated with known COVID-19 cluster?",
            "Yes",
            "No"
    );

    SymptomsTestClass q3 = new SymptomsTestClass(
            "Have you travelled abroad within the last 14 days?",
            "Yes",
            "No"
    );

    SymptomsTestClass q4 = new SymptomsTestClass(
            "Have you had close contact with any confirmed or suspected COVID-19 cases within the last 14 days? ",
            "Yes",
            "No"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms_test);

        question = findViewById(R.id.tv_testQuestionTitle);
        option1 = findViewById(R.id.btn_testAnsYes);
        option2 = findViewById(R.id.btn_testAnsNo);

        symptomsTestClassArrayList = new ArrayList<>();

        getTestQuestions(symptomsTestClassArrayList);
        
        setDataToView();

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerYes();
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerNo();
            }
        });
    }

    private void answerYes() {
        score++;
        count++;

        if(count == symptomsTestClassArrayList.size()){
            Intent intent = new Intent(SymptomsTestActivity.this, SymptomsResultActivity.class);
            startActivity(intent);
            finish();
        } else{
            setDataToView();
        }
    }

    private void answerNo() {
        score--;
        count++;

        if(count == symptomsTestClassArrayList.size()){
            Intent intent = new Intent(SymptomsTestActivity.this, SymptomsResultActivity.class);

            Bundle b = new Bundle();
            b.putInt("score", score);
            intent.putExtras(b);

            startActivity(intent);
            finish();
        } else{
            setDataToView();
        }
    }

    private void setDataToView() {
        question.setText(symptomsTestClassArrayList.get(count).getQuestion());
        option1.setText(symptomsTestClassArrayList.get(count).getOption1());
        option2.setText(symptomsTestClassArrayList.get(count).getOption2());
    }

    private void getTestQuestions(ArrayList<SymptomsTestClass> symptomsTestClassArrayList) {
        symptomsTestClassArrayList.add(q0);
        symptomsTestClassArrayList.add(q1);
        symptomsTestClassArrayList.add(q2);
        symptomsTestClassArrayList.add(q3);
        symptomsTestClassArrayList.add(q4);
    }
}