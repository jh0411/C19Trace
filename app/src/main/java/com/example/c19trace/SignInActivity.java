package com.example.c19trace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import static android.content.ContentValues.TAG;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    TextView signUp;
    TextView forgotPass;
    Button tempSignIn;
    private EditText email, password;

    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(SignInActivity.this, NavigationActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signUp = findViewById(R.id.tv_signUpLink);
        forgotPass = findViewById(R.id.tv_forgotPass);
        tempSignIn = findViewById(R.id.btn_signIn);
        email = (EditText) findViewById(R.id.et_userEmail);
        password = (EditText) findViewById(R.id.et_signUpPass);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        tempSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_mail = email.getText().toString();
                String user_pass = password.getText().toString();

                if (user_mail.equals("") || user_pass.equals("")){
                    Toast.makeText(SignInActivity.this, "Please enter all the fields!", Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth.signInWithEmailAndPassword(user_mail, user_pass)
                            .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Log.d(TAG, "signInWithEmail: success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user);

                                        Toast.makeText(SignInActivity.this, "Sign In Successful!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignInActivity.this, NavigationActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        Log.w(TAG, "signInWithEmail: failure", task.getException());
                                        Toast.makeText(SignInActivity.this, "Invalid Credentials, Please try again!", Toast.LENGTH_SHORT).show();
                                        updateUI(null);
                                    }
                                }

                            });
                    }
            }
        });
    }

    private void updateUI(FirebaseUser user) {
    }
}