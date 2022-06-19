package com.example.c19trace.CheckIn;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.c19trace.Others.NotificationFragment;
import com.example.c19trace.R;
import com.example.c19trace.databinding.ActivityMainBinding;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.LinkedList;
import java.util.List;

public class CheckInFragment extends Fragment {

    ActivityMainBinding binding;

    private MainViewModel mainViewModel;
    private RecyclerView recyclerView;

    private final LinkedList<HistoryClass> historyList = new LinkedList<>();

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
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, next_fragment).commit();
            }
        });

        recyclerView = view.findViewById(R.id.recyclerview_checkInHistory);
        final HistoryAdapter adapter = new HistoryAdapter(new HistoryAdapter.HistoryDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        mainViewModel.getAllHistory().observe(getActivity(), new Observer<List<HistoryClass>>() {
            @Override
            public void onChanged(List<HistoryClass> historyClasses) {
                adapter.submitList(historyList);
            }
        });

        Button button = view.findViewById(R.id.btn_checkIn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                IntentIntegrator integrator = IntentIntegrator.forSupportFragment(CheckInFragment.this);
//
//                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
//                integrator.setPrompt("Scan");
//                integrator.setCameraId(0);
//                integrator.setBeepEnabled(true);
//                integrator.setBarcodeImageEnabled(true);
//                integrator.setOrientationLocked(true);
//
//                integrator.initiateScan();

                ScanOptions options = new ScanOptions();

                options.setDesiredBarcodeFormats(ScanOptions.ONE_D_CODE_TYPES);
                options.setPrompt("Scan");
                options.setCameraId(0);  // Use a specific camera of the device
                options.setBeepEnabled(true);
                options.setBarcodeImageEnabled(true);
                options.setOrientationLocked(true);

                qrLauncher.launch(options);
            }
        });
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
//
//        if(result != null) {
//            if(result.getContents() == null) { //not successful
//                Log.e("Scan*******", "Cancelled scan");
//                Toast.makeText(getActivity(), "Cancelled scan", Toast.LENGTH_LONG).show();
//
//            } else { //successful get the string from the QR code
//                Log.e("Scan", "Scanned");
//
//                String scan_location = result.getContents();
//                String current_date = java.time.LocalDate.now().toString();
//
//                HistoryClass history = new HistoryClass(scan_location, current_date);
//                mainViewModel.insert(history);
//
//                int historyListSize = historyList.size();
//                historyList.addLast(history);
//                recyclerView.getAdapter().notifyDataSetChanged();
//                recyclerView.smoothScrollToPosition(historyListSize);
//            }
//        } else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//    }

    private final ActivityResultLauncher<ScanOptions> qrLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null){
                    Log.e("Scan*******", "Cancelled scan");
                    Toast.makeText(getActivity(), "Cancelled scan", Toast.LENGTH_LONG).show();
                } else{
                    Log.e("Scan", "Scanned");

                    String scan_location = result.getContents();
                    String current_date = java.time.LocalDate.now().toString();

                    HistoryClass history = new HistoryClass(scan_location, current_date);
                    mainViewModel.insert(history);

                    int historyListSize = historyList.size();
                    historyList.addLast(history);
                    recyclerView.getAdapter().notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(historyListSize);
                }
            });
}