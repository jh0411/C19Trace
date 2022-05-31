package com.example.c19trace;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class StatisticsFragmentAdapter extends FragmentStateAdapter {

    public StatisticsFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 1){
            return new CountryFragment();
        }

        return new GlobalFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
