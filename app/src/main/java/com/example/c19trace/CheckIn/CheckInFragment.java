package com.example.c19trace.CheckIn;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.c19trace.R;
import com.example.c19trace.databinding.ActivityMainBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckInFragment extends Fragment {

    Button button;
    TextView link;
    ArrayList<HistoryClass> checkInArrayList;

    FirebaseDatabase mDatabase;
    DatabaseReference databaseReference;

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

        button = view.findViewById(R.id.btn_checkIn);
        link = view.findViewById(R.id.tv_checkHistoryLink);
        checkInArrayList = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance();
        databaseReference = mDatabase.getReference();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ScanOptions options = new ScanOptions();

                options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
                options.setPrompt("Scan");
                options.setCameraId(0);  // Use a specific camera of the device
                options.setBeepEnabled(true);
                options.setBarcodeImageEnabled(true);
                options.setOrientationLocked(true);

                qrLauncher.launch(options);
            }
        });

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CheckInHistoryActivity.class);
                startActivity(intent);
            }
        });
    }



    private final ActivityResultLauncher<ScanOptions> qrLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null){
                    Log.e("Scan*******", "Cancelled scan");
                    Toast.makeText(getActivity(), "Cancelled scan", Toast.LENGTH_SHORT).show();
                } else{
                    Log.e("Scan", "Scanned");
                    Toast.makeText(getActivity(), "Scanned!", Toast.LENGTH_SHORT).show();

                    String scan_location = result.getContents();
                    String current_date = java.time.LocalDate.now().toString();
                    String current_time = java.time.LocalTime.now().truncatedTo(ChronoUnit.SECONDS).toString();

                    HistoryClass history = new HistoryClass(scan_location, current_date, current_time);
                    saveHistory(scan_location, current_date, current_time);

                    checkInArrayList.add(history);

                    Intent intent = new Intent(getActivity(), CheckInSuccessActivity.class);
                    intent.putExtra("Location", scan_location);
                    intent.putExtra("Date", current_date);
                    intent.putExtra("Time", current_time);
                    startActivity(intent);
                }
            });

    private void saveHistory(String scan_location, String current_date, String current_time){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        String url = "https://62bb32877bdbe01d52998dd7.mockapi.io/History/checkIn";

        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //method to handle error
                        Toast.makeText(getActivity(), "failed to get response" + error, Toast.LENGTH_SHORT).show();
                    }
                }){

            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();

                params.put("location", scan_location);
                params.put("date", current_date);
                params.put("time", current_time);

                return params;
            }
        };

        requestQueue.add(request);
    }
}