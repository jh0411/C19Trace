package com.example.c19trace.Profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c19trace.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends Fragment {

    EditText oldPass, newPass, confirmNewPass;
    Button saveChanges;

    public ChangePassword() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        oldPass = view.findViewById(R.id.et_editOldPassword);
        newPass = view.findViewById(R.id.et_editNewPassword);
        confirmNewPass = view.findViewById(R.id.et_editConfirmPassword);
        saveChanges = view.findViewById(R.id.btn_saveChangesPasswordEdit);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPw = oldPass.getText().toString();
                String newPw = newPass.getText().toString();
                String confirmPw = confirmNewPass.getText().toString();

                if (oldPw.equals("") || newPw.equals("") || confirmPw.equals("")) {
                    Toast.makeText(getActivity(), "Please enter all fields!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (newPw.equals(confirmPw)){
                        if (newPw.length() < 6){
                            Toast.makeText(getActivity(), "Please ensure your password is more than 6 character", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            String user_mail = currentUser.getEmail();
                            AuthCredential credential = EmailAuthProvider.getCredential(user_mail, oldPw);

                            currentUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    currentUser.updatePassword(newPw).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(getActivity(), "Password update successfully", Toast.LENGTH_SHORT).show();
                                                Fragment next_fragment = new ProfileFragment();
                                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, next_fragment).commit();
                                            }
                                            else{
                                                Toast.makeText(getActivity(), "Password failed to update, please try again!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    } else{
                        Toast.makeText(getActivity(), "Password do not match, please try again!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}