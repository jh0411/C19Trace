package com.example.c19trace.Home.SymptomTest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.c19trace.CheckIn.CheckInFragment;
import com.example.c19trace.Home.VaccinationForm.VaccinationActivity;
import com.example.c19trace.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SymptomsTestActivity extends AppCompatActivity {

    private final String[] questions = {
            "Are you exhibiting 2 or more symptoms such as Fever, Shivering, Body ache, Headache etc?",
            "Besides the previous symptoms, are you exhibiting any of the symptoms such as Cough, Difficulty breathing, Loss of smell, Loss of taste",
            "Have you attended any event/areas associated with known COVID-19 cluster?",
            "Have you travelled abroad within the last 14 days?",
            "Have you had close contact with any confirmed or suspected COVID-19 cases within the last 14 days? "
    };

    private int symptoms = 0, count = 0;

    TextView question;
    Button yes;
    Button no;

    ImageView back;

    FirebaseUser currentUser;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms_test);

        question = findViewById(R.id.tv_testQuestionTitle);
        yes = findViewById(R.id.btn_testAnsYes);
        no = findViewById(R.id.btn_testAnsNo);

        question.setText(questions[count]);

        databaseReference = FirebaseDatabase.getInstance("https://c19trace-12be0-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("user");

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count < questions.length - 1){
                    symptoms++;
                    count++;

                    question.setText(questions[count]);
                } else{
                    status(symptoms);
                }
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count < questions.length - 1){
                    count++;

                    question.setText(questions[count]);
                }else{
                    status(symptoms);
                }
            }
        });

        back = findViewById(R.id.symptomsBack);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SymptomsTestActivity.this, CheckInFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
            }
        });
    }

    private void status(int symptoms) {

        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if(symptoms >= 4){
            Intent intent = new Intent(SymptomsTestActivity.this, HighRiskActivity.class);
            startActivity(intent);
            databaseReference.child(user).child("Risk Status").setValue("High Risk");

        } else if(symptoms == 0){
            Intent intent = new Intent(SymptomsTestActivity.this, LowRiskActivity.class);
            startActivity(intent);
            databaseReference.child(user).child("Risk Status").setValue("Low Risk");

        } else{
            Intent intent = new Intent(SymptomsTestActivity.this, MediumRiskActivity.class);
            startActivity(intent);
            databaseReference.child(user).child("Risk Status").setValue("Medium Risk");

        }
    }
}