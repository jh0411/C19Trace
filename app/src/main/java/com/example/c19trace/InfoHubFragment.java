package com.example.c19trace;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class InfoHubFragment extends Fragment {

    public InfoHubFragment(){
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_hub, container, false);
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RelativeLayout rl1 = view.findViewById(R.id.relative1);
        RelativeLayout rl2 = view.findViewById(R.id.relative2);
        RelativeLayout rl3 = view.findViewById(R.id.relative3);
        RelativeLayout rl4 = view.findViewById(R.id.relative4);
        RelativeLayout rl5 = view.findViewById(R.id.relative5);

        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), News1Activity.class);
                startActivity(intent);
            }
        });

        rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), News2Activity.class);
                startActivity(intent);
            }
        });

        rl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), News3Activity.class);
                startActivity(intent);
            }
        });

        rl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), News4Activity.class);
                startActivity(intent);
            }
        });

        rl5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), News5Activity.class);
                startActivity(intent);
            }
        });
    }
}