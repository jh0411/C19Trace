package com.example.c19trace.Home.VaccinationForm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.c19trace.Home.HomeFragment;
import com.example.c19trace.Others.SignUpActivity;
import com.example.c19trace.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VaccinationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button readInfo, submit;
    Spinner question1, question2, question3, question4, question5;
    ArrayAdapter<String> vaccAnsAdapter, vaccTypeAdapter;
    CheckBox checkBox1, checkBox2, checkBox3;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance("https://c19trace-12be0-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("vaccineForm");

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        readInfo = findViewById(R.id.btn_readVaccine);

        readInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VaccinationActivity.this, ReadVaccineInfoActivity.class);
                startActivity(intent);
            }
        });

        vaccAnsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.vacc_ans));
        vaccAnsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        vaccTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.vacc_type));
        vaccTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        setSpinnerAdapter();

        checkBox1 = findViewById(R.id.vaccCheckBox1);
        checkBox2 = findViewById(R.id.vaccCheckBox2);
        checkBox3 = findViewById(R.id.vaccCheckBox3);

        submit = findViewById(R.id.btn_submitVacc);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String q1 = question1.getSelectedItem().toString();
                String q2 = question2.getSelectedItem().toString();
                String q3 = question3.getSelectedItem().toString();
                String q4 = question4.getSelectedItem().toString();
                String q5 = question5.getSelectedItem().toString();

                String noAns = "Please select your answer";

                userName = currentUser.getUid();

                if (!checkBox1.isChecked() || !checkBox2.isChecked() || !checkBox3.isChecked()) {
                    Toast.makeText(VaccinationActivity.this, "Please tick all the statements to proceed.", Toast.LENGTH_SHORT).show();
                } else {
                    if (q1 == noAns || q2 == noAns || q3 == noAns || q4 == noAns || q5 == noAns) {
                        Toast.makeText(VaccinationActivity.this, "Please select an answer for all the questions.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(VaccinationActivity.this, "Form submitted successfully. Please wait patiently until we schedule a vaccination appointment for you", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(VaccinationActivity.this, HomeFragment.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                    };
                }

                VaccineFormClass vaccineFormClass = new VaccineFormClass(q1, q2, q3, q4, q5);
                databaseReference.child(currentUser.getUid()).setValue(vaccineFormClass);

            }
        });
    }

    private void setSpinnerAdapter() {
        question1 = findViewById(R.id.sp_vaccAns1);
        question1.setAdapter(vaccAnsAdapter);
        question1.setOnItemSelectedListener(this);

        question2 = findViewById(R.id.sp_vaccAns2);
        question2.setAdapter(vaccAnsAdapter);
        question2.setOnItemSelectedListener(this);

        question3 = findViewById(R.id.sp_vaccAns3);
        question3.setAdapter(vaccAnsAdapter);
        question3.setOnItemSelectedListener(this);

        question4 = findViewById(R.id.sp_vaccAns4);
        question4.setAdapter(vaccAnsAdapter);
        question4.setOnItemSelectedListener(this);

        question5 = findViewById(R.id.sp_vaccAns5);
        question5.setAdapter(vaccTypeAdapter);
        question5.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}