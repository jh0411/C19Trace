package com.example.c19trace.Home.Statistics;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.c19trace.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class GlobalFragment extends Fragment {

    TextView globalNew, globalRecovered, globalDeath, globalAffected, globalTotal;

    Spinner spinner;

    ArrayAdapter<String> adapter;

    public GlobalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_global, container, false);
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        spinner = view.findViewById(R.id.sp_globalCountry);

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.global_array));
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

        globalNew = view.findViewById(R.id.tv_globalNewNum);
        globalRecovered = view.findViewById(R.id.tv_globalRecNum);
        globalDeath = view.findViewById(R.id.tv_globalDedNum);
        globalAffected = view.findViewById(R.id.tv_globalAffNum);
        globalTotal = view.findViewById(R.id.tv_globalAllNum);

        fetchAPIUsingVolley();
    }

    private void fetchAPIUsingVolley() {
        // Instantiate the Volley RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        String url = "https://disease.sh/v3/covid-19/all";

        // https://developer.android.com/training/volley/simple
        // Request a string response from the provided URL.

        StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // Creating object of JSONObject
                    // https://developer.android.com/reference/org/json/JSONObject
                    // https://www.tutorialspoint.com/android/android_json_parser.htm
                    // https://www.journaldev.com/10642/android-jsonobject-json-parsing
                    JSONObject jsonObject = new JSONObject(response.toString());

                    globalNew.setText(jsonObject.getString("todayCases"));
                    globalRecovered.setText(jsonObject.getString("todayRecovered"));
                    globalDeath.setText(jsonObject.getString("todayDeaths"));
                    globalAffected.setText(jsonObject.getString("affectedCountries"));
                    globalTotal.setText(jsonObject.getString("cases"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        requestQueue.add(request);
    }
}