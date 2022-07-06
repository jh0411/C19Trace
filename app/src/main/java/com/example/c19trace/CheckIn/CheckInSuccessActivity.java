package com.example.c19trace.CheckIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.c19trace.Home.HomeFragment;
import com.example.c19trace.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CheckInSuccessActivity extends AppCompatActivity {

     TextView Date, Time, Location, risk_status, vacc_status;
     Button button;

     public static String LOCATION, DATE, TIME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_success);

        Date = findViewById(R.id.tv_checkInSuccessDate);
        Time = findViewById(R.id.tv_checkInSuccessTime);
        Location = findViewById(R.id.tv_checkInSuccessLocation);
        risk_status = findViewById(R.id.tv_riskStatusText);
        vacc_status = findViewById(R.id.tv_vaccStatusText);
        button = findViewById(R.id.btn_checkInSuccessDone);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance("https://c19trace-12be0-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("user");

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String location = extras.getString("Location");
            String date = extras.getString("Date");
            String time = extras.getString("Time");

            Location.setText(location);
            Date.setText(date);
            Time.setText(time);

            LOCATION = location;
            DATE = date;
            TIME = time;
        }

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String currentUserID = currentUser.getUid();
                    String user_risk = snapshot.child(currentUserID).child("riskStatus").getValue(String.class);
                    String user_vacc = snapshot.child(currentUserID).child("vaccinationStatus").getValue(String.class);

                    risk_status.setText(user_risk);
                    vacc_status.setText(user_vacc);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckInSuccessActivity.this, CheckInFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
            }
        });
    }
}