package com.example.c19trace;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends Fragment {

    Spinner spinner;
    CircleImageView profilePicture;
    Uri imageUri;
    StorageReference storageProfilePicRef;
    DatabaseReference databaseReference;
    FirebaseUser currentUser;
    EditText profileName, profilePhone, profileEmail, profileDob;
    ImageView editPen;
    Button saveChanges;
    ActivityResultLauncher<Intent> intentActivityResultLauncher;

    ArrayAdapter<String> adapter;

    public void openGalleryForResult(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT, null);
        galleryIntent.setType("image/*");

        Intent chooser = new Intent(Intent.ACTION_CHOOSER);
        chooser.putExtra(Intent.EXTRA_INTENT, galleryIntent);

        Intent[] intentArray = {galleryIntent};
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
        intentActivityResultLauncher.launch(chooser);
    }

    public EditProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("picture/" + currentUser.getUid() + ".png");

        final long ONE_MEGABYTE = 1024 * 1024;
        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                profilePicture.setImageBitmap(bmp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });

        intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                            imageUri = data.getData();
                            profilePicture.setImageURI(imageUri);
                        }
                    }
                });

        profileName = view.findViewById(R.id.et_editProfileName);
        profilePhone = view.findViewById(R.id.et_editProfilePhoneNum);
        profileEmail = view.findViewById(R.id.et_editProfileEmail);
        profileDob = view.findViewById(R.id.et_editProfileDOB);
        profilePicture = view.findViewById(R.id.iv_editProfilePicture);

        editPen = view.findViewById(R.id.iv_editEllipse);

        saveChanges = view.findViewById(R.id.btn_saveChangesEdit);

        spinner = view.findViewById(R.id.sp_editProfileGender);

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.gender_array));
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

        databaseReference = FirebaseDatabase.getInstance("https://c19trace-12be0-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("user");

        if (currentUser != null) {
            profileName.setText(currentUser.getDisplayName());
            profileEmail.setText(currentUser.getEmail());
            profilePhone.setText(currentUser.getPhoneNumber());

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String currentUserID = currentUser.getUid();
                        String user_mail = dataSnapshot.child(currentUserID).child("email").getValue(String.class);
                        String user_name = dataSnapshot.child(currentUserID).child("name").getValue(String.class);
                        String user_phone = dataSnapshot.child(currentUserID).child("phoneNumber").getValue(String.class);
                        String user_dob = dataSnapshot.child(currentUserID).child("dob").getValue(String.class);

                        profileEmail.setText(user_mail);
                        profileName.setText(user_name);
                        profilePhone.setText(user_phone);
                        profileDob.setText(user_dob);

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                dataSnapshot.child(currentUserID).child("gender").getValue(String.class);
                                spinner.setAdapter(adapter);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            editPen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openGalleryForResult(v);
                }
            });

            saveChanges.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String user_name = profileName.getText().toString();
                    String user_mail = profileEmail.getText().toString();
                    String user_phone = profilePhone.getText().toString();
                    String user_dob = profileDob.getText().toString();

                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(user_name).setPhotoUri(Uri.parse("picture/profile_pic.png")).build();

                    uploadImageToFirebase(imageUri);

                    currentUser.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            AuthCredential credential = EmailAuthProvider.getCredential(user_mail, currentUser.getUid()); // Current Login Credentials \\
                            // Prompt the user to re-provide their sign-in credentials
                            currentUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.d(TAG, "User re-authenticated.");
                                    //Now change your email address \\
                                    //----------------Code for Changing Email Address----------\\
                                    currentUser.updateEmail(user_mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User email address updated.");
                                            }
                                        }
                                    });
                                }
                            });

                            databaseReference.child(currentUser.getUid()).child("name").setValue(user_name);
                            databaseReference.child(currentUser.getUid()).child("email").setValue(user_mail);
                            databaseReference.child(currentUser.getUid()).child("phoneNumber").setValue(user_phone);
                            databaseReference.child(currentUser.getUid()).child("dob").setValue(user_dob);
                            databaseReference.child(currentUser.getUid()).child("profileImage");

                            databaseReference.child(currentUser.getUid()).child("gender").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                            Toast.makeText(getActivity(), "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                            Fragment next_fragment = new ProfileFragment();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, next_fragment).commit();
                        }
                    });
                }
            });
        }
    }

    private void uploadImageToFirebase(Uri uri) {
        storageProfilePicRef = FirebaseStorage.getInstance().getReference().child("picture/" + currentUser.getUid() + ".png");

        if (uri != null) {
            try {
                final File localFile = File.createTempFile(currentUser.getUid(), "png");
                storageProfilePicRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getActivity(), "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        profilePicture.setImageBitmap(bitmap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Image failed to upload", Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
