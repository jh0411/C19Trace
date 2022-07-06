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


public class GlobalFragment extends Fragment {

    TextView globalActive, globalRecovered, globalDeath, globalAffected, globalTotal;

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

        globalActive = view.findViewById(R.id.tv_globalNewNum);
        globalRecovered = view.findViewById(R.id.tv_globalRecNum);
        globalDeath = view.findViewById(R.id.tv_globalDedNum);
        globalAffected = view.findViewById(R.id.tv_globalAffNum);
        globalTotal = view.findViewById(R.id.tv_globalAllNum);

        fetchAPIUsingVolley();
    }

    private void fetchAPIUsingVolley() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        String url = "https://disease.sh/v3/covid-19/all";

        StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());

                    globalActive.setText(jsonObject.getString("active"));
                    globalRecovered.setText(jsonObject.getString("recovered"));
                    globalDeath.setText(jsonObject.getString("deaths"));
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

        requestQueue.add(request);
    }
}