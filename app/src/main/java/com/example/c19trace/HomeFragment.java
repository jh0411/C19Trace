package com.example.c19trace;

import static android.Manifest.permission.CALL_PHONE;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class HomeFragment extends Fragment {

    public HomeFragment(){
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.iv_homeInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment next_fragment = new InfoHubFragment();
                Bundle bundle = new Bundle();
                bundle.putString("homeFeatures", "infoHub");
                next_fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, next_fragment).commit();
            }
        });

        ImageView sop = view.findViewById(R.id.iv_homeSop);

        sop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SopActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.iv_homePhone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);

                String covidHotline = "0377239300";
                intent.setData(Uri.parse("tel:" + covidHotline));

                if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
                {
                    startActivity(intent);
                } else
                {
                    requestPermissions(new String[]{CALL_PHONE}, 1);
                }
            }
        });

        view.findViewById(R.id.iv_homeNotifications).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment next_fragment = new NotificationFragment();
                Bundle bundle = new Bundle();
                bundle.putString("homeFeatures", "notifications");
                next_fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, next_fragment).commit();
            }
        });

        view.findViewById(R.id.iv_homeVaccine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment next_fragment = new VaccinationFragment();
                Bundle bundle = new Bundle();
                bundle.putString("homeFeatures", "vaccination");
                next_fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, next_fragment).commit();
            }
        });
    }
}