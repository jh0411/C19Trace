package com.example.c19trace.Home.Statistics;

import static java.util.Locale.getDefault;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CountryFragment extends Fragment {

    TextView countryActive, countryRecovered, countryDeath, countryVaccine, countryTotal;

    public CountryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country, container, false);
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        countryActive = view.findViewById(R.id.tv_malaysiaNewNum);
        countryRecovered = view.findViewById(R.id.tv_malaysiaRecNum);
        countryDeath = view.findViewById(R.id.tv_malaysiaDedNum);
        countryVaccine = view.findViewById(R.id.tv_malaysiaVacNum);
        countryTotal = view.findViewById(R.id.tv_malaysia_allNum);

        fetchApiUsingVolley();
    }

    private void fetchApiUsingVolley() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        String malaysiaUrl = "https://disease.sh/v3/covid-19/countries/Malaysia?strict=true";
        String malaysiaVaccine = "https://disease.sh/v3/covid-19/vaccine/coverage/countries/Malaysia?lastdays=1&fullData=false";

        StringRequest malaysiaRequest = new StringRequest(Request.Method.GET, malaysiaUrl, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());

                    countryActive.setText(jsonObject.getString("active"));
                    countryRecovered.setText(jsonObject.getString("recovered"));
                    countryDeath.setText(jsonObject.getString("deaths"));
                    countryTotal.setText(jsonObject.getString("cases"));
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

        StringRequest malaysiaVaccineReq = new StringRequest(Request.Method.GET, malaysiaVaccine, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONObject jsonObjectTimeLine = jsonObject.getJSONObject("timeline");
                    String k = jsonObjectTimeLine.keys().next();

                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put(jsonObjectTimeLine);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        String a = jsonArray.getString(i);
                        String jsonFormattedString = a.replaceAll("\\\\", "");
                        countryVaccine.setText(jsonObjectTimeLine.getString(k));
                    }
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

        requestQueue.add(malaysiaRequest);
        requestQueue.add(malaysiaVaccineReq);
    }
}

