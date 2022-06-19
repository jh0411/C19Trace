package com.example.c19trace.Others;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c19trace.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText email;
    Button resetPass;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = findViewById(R.id.et_forgotPassInputEmail);
        resetPass = findViewById(R.id.btn_resetPass);
        mAuth = FirebaseAuth.getInstance();

        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_mail = email.getText().toString();

                if (user_mail.equals("")){
                    Toast.makeText(ForgotPasswordActivity.this, "Please fill in your email address!", Toast.LENGTH_SHORT).show();
                }
                else{
                        mAuth.sendPasswordResetEmail(user_mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ForgotPasswordActivity.this, "Check your email to access the reset link", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(ForgotPasswordActivity.this, SignInActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else{
                                    Toast.makeText(ForgotPasswordActivity.this, "Email is not registered.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                }
            }
        });
    }
}