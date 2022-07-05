package com.example.c19trace.CheckIn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.c19trace.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CheckInHistoryActivity extends AppCompatActivity {

    ImageView back;
    RecyclerView recyclerView;
    ArrayList<HistoryClass> checkInArrayList;
    HistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_history);
        checkInArrayList = new ArrayList<>();

        back = findViewById(R.id.checkInHistoryBack);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckInHistoryActivity.this, CheckInFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
            }
        });

        //Load check in history from API
        retrieveCheckInHistoryFromApi();

        //Set up recycler view to show check in history
        recyclerView = findViewById(R.id.recyclerview_checkInHistory);
        historyAdapter = new HistoryAdapter(new HistoryAdapter.HistoryDiff());
        recyclerView.setAdapter(historyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(CheckInHistoryActivity.this));
        historyAdapter.submitList(checkInArrayList);
    }

    //Get check in history from API
    private void retrieveCheckInHistoryFromApi(){
        String url = "https://62bb32877bdbe01d52998dd7.mockapi.io/History/checkIn";

        //declare a new variable for the request queue
        RequestQueue requestQueue = Volley.newRequestQueue(CheckInHistoryActivity.this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        recyclerView.setVisibility(View.VISIBLE);

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);

                                String location = jsonObject.getString("location");
                                String date = jsonObject.getString("date");
                                String time = jsonObject.getString("time");

                                checkInArrayList.add(new HistoryClass(location, date, time));

                                int historyListSize = checkInArrayList.size();
                                recyclerView.getAdapter().notifyItemInserted(historyListSize);
                                recyclerView.getAdapter().notifyDataSetChanged();
                                recyclerView.smoothScrollToPosition(historyListSize);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CheckInHistoryActivity.this,"Failed to retrieve data!", Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}