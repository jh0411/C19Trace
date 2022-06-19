package com.example.c19trace.Others;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c19trace.Profile.UserHelper;
import com.example.c19trace.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {

    Spinner spinner;
    ArrayAdapter<String> adapter;

    TextView signIn;
    Button signUp;

    private EditText name, phoneNumber, email, DOB, password, confirmPassword;
    DatabaseReference reference;

    private FirebaseAuth mAuth;

    DatePickerDialog.OnDateSetListener setListener;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = (EditText) findViewById(R.id.et_signUpName);
        phoneNumber = (EditText) findViewById(R.id.et_signUpPhoneNum);
        email = (EditText) findViewById(R.id.et_signUpEmail);
        DOB = (EditText) findViewById(R.id.et_signUpDOB);
        spinner = findViewById(R.id.sp_signUpGender);
        password = (EditText) findViewById(R.id.et_signUpPass);
        confirmPassword = (EditText) findViewById(R.id.et_confirmPass);

        mAuth = FirebaseAuth.getInstance();

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                DOB.setText(date);
            }
        };

        DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SignUpActivity.this, R.style.CalendarTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        DOB.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });

        signUp = findViewById(R.id.btn_createAcc);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_name = name.getText().toString();
                String user_num = phoneNumber.getText().toString();
                String user_mail = email.getText().toString();
                String user_DOB = DOB.getText().toString();
                String user_gender = spinner.getSelectedItem().toString();
                String user_pass = password.getText().toString();
                String user_confirmPass = confirmPassword.getText().toString();

                if (user_name.equals("") || user_num.equals("") || user_mail.equals("")  || user_DOB.equals("") || user_gender.equals("") || user_pass.equals("") || user_confirmPass.equals("")){
                    Toast.makeText(SignUpActivity.this, "Please enter all the fields!", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (user_pass.length() < 6){
                        Toast.makeText(SignUpActivity.this, "Please make sure your password is more than 6 character!", Toast.LENGTH_SHORT).show();
                    }
                    else if (!user_pass.equals(user_confirmPass)){
                        Toast.makeText(SignUpActivity.this, "Password is not match, please try again", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        mAuth.createUserWithEmailAndPassword(user_mail, user_pass)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(SignUpActivity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d(TAG, "createUserWithEmail:success");
                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(user_name).setPhotoUri(Uri.parse("android.resource://"+ getPackageName()+"/"+R.drawable.profile_pic)).build();
                                            user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Log.d(String.valueOf(SignUpActivity.this), "User profile created.");
                                                    }
                                                }
                                            });

                                            reference = FirebaseDatabase.getInstance("https://c19trace-12be0-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("user");
                                            UserHelper userHelperClass = new UserHelper(user_name, user_mail, user_num, user_DOB, user_gender);

                                            reference.child(user.getUid()).setValue(userHelperClass);

                                            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                                            startActivity(intent);
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            }
        });

        signIn = findViewById(R.id.tv_signInLink);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.gender_array));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}