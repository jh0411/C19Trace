package com.example.c19trace.Others;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.c19trace.CheckIn.CheckInFragment;
import com.example.c19trace.Home.HomeFragment;
import com.example.c19trace.Home.Statistics.StatisticsFragment;
import com.example.c19trace.Hotspot.HotspotFragment;
import com.example.c19trace.Profile.ProfileFragment;
import com.example.c19trace.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class NavigationActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    Fragment defaultFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        bottomNav = findViewById(R.id.bottomNavigation);
        bottomNav.setOnItemSelectedListener(navListener);

        defaultFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, defaultFragment).commit();
    }

    private final NavigationBarView.OnItemSelectedListener navListener = new NavigationBarView.OnItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch(item.getItemId()){
                case R.id.menu_home:
                    selectedFragment = new HomeFragment();
                    break;

                case R.id.menu_checkIn:
                    selectedFragment = new CheckInFragment();
                    break;

                case R.id.menu_profile:
                    selectedFragment = new ProfileFragment();
                    break;

                case R.id.menu_statistics:
                    selectedFragment = new StatisticsFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment).commit();
            return true;
        }
    };
}