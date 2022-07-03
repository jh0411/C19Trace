package com.example.c19trace.Hotspot;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.c19trace.R;

public class HotspotFragment extends Fragment {

    Button hotspot;

    public HotspotFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotspot, container, false);

        hotspot = view.findViewById(R.id.btn_hotspotBtn);

        hotspot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),CovidHotspotActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}