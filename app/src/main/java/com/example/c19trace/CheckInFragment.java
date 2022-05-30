package com.example.c19trace;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static android.Manifest.permission.CAMERA;

public class CheckInFragment extends Fragment {

    public CheckInFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_in, container, false);
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.iv_checkInNotifications).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment next_fragment = new NotificationFragment();
                Bundle bundle = new Bundle();
                bundle.putString("checkInFeatures", "notifications");
                next_fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, next_fragment).commit();
            }
        });

        Button button = view.findViewById(R.id.btn_checkIn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_CALL);
//                if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED){
//                    startActivity(intent);
//                }
//                else{
//                    requestPermissions(new String[]{CAMERA},1);
//                }

                Intent intent = new Intent(getActivity(), QrScannerActivity.class);
                startActivity(intent);

            }
        });
    }
}