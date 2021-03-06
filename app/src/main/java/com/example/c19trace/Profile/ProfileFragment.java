package com.example.c19trace.Profile;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c19trace.R;
import com.example.c19trace.Others.SignInActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    TextView profileName, profileNumber, profileEmail, riskStatus, vaccStatus;
    CircleImageView profileImage;

    private FirebaseAuth mAuth;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        profileName = (TextView) view.findViewById(R.id.tv_name);
        profileEmail = (TextView) view.findViewById(R.id.tv_email);
        profileNumber = (TextView) view.findViewById(R.id.tv_mobile);
        profileImage = (CircleImageView) view.findViewById(R.id.iv_profilePic);

        riskStatus = (TextView) view.findViewById(R.id.tv_riskStatus);
        vaccStatus = (TextView) view.findViewById(R.id.tv_vaccineStatus);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance("https://c19trace-12be0-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("user");

        profileName.setText(currentUser.getDisplayName());
        profileEmail.setText(currentUser.getEmail());
        profileNumber.setText(currentUser.getPhoneNumber());

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("picture/" + currentUser.getUid() + ".png");

        final long ONE_MEGABYTE = 1024 * 1024;

        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                profileImage.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapShot) {

                if (snapShot.exists()){
                    String currentUserID = currentUser.getUid();
                    String user_name = snapShot.child(currentUserID).child("name").getValue(String.class);
                    String user_mail = snapShot.child(currentUserID).child("email").getValue(String.class);
                    String user_number = snapShot.child(currentUserID).child("phoneNumber").getValue(String.class);
                    String user_risk = snapShot.child(currentUserID).child("riskStatus").getValue(String.class);
                    String user_vacc = snapShot.child(currentUserID).child("vaccinationStatus").getValue(String.class);

                    profileName.setText(user_name);
                    profileEmail.setText(user_mail);
                    profileNumber.setText(user_number);

                    riskStatus.setText(user_risk);
                    vaccStatus.setText(user_vacc);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        view.findViewById(R.id.btn_editProfileBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment next_fragment = new EditProfile();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, next_fragment).commit();
            }
        });

        view.findViewById(R.id.btn_changePassBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment next_fragment = new ChangePassword();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, next_fragment).commit();
            }
        });

        view.findViewById(R.id.btn_faqBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment next_fragment = new FaqFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, next_fragment).commit();
            }
        });

        Dialog dialog = new Dialog(getActivity());

        Button logout = view.findViewById(R.id.btn_logoutBtn);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.setContentView(R.layout.logout_prompt);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Button yes = dialog.findViewById(R.id.btn_logoutYes);
                Button cancel = dialog.findViewById(R.id.btn_logoutCancel);

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(getActivity(), "Sign out successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), SignInActivity.class);
                        startActivity(intent);
                        getActivity().onBackPressed();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }
}