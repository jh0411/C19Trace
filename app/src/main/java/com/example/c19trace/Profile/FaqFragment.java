package com.example.c19trace.Profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.c19trace.R;

import java.util.ArrayList;
import java.util.List;


public class FaqFragment extends Fragment {

    RecyclerView recyclerView;
    List<FaqClass> faqList;

    public FaqFragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_faq, container, false);
        recyclerView = view.findViewById(R.id.recyclerview_faq);

        initData();
        setRecyclerView();

        return view;
    }

    private void setRecyclerView() {
        FAQAdapter faqAdapter = new FAQAdapter(faqList);
        recyclerView.setAdapter(faqAdapter);
        recyclerView.setHasFixedSize(true);
    }

    private void initData(){
        faqList = new ArrayList<>();

        faqList.add(new FaqClass("Q: Do i need to pay any subscription fees to access the features of for the app?", "No, you do not need to! The C19Trace app is free for all users in Malaysia and they can enjoy all of the features provided as long they have a device with stable Internet connectivity."));
        faqList.add(new FaqClass("Q: How can I know if my location is in hotspot area?", "The C19Trace app includes a hotspot map function located at the bottom navigation. Users can simply tap on it and search for any location to find out any hotspot areas."));
        faqList.add(new FaqClass("Q: What should I do if I’m experiencing some common symptoms of Covid-19?", "You should use the symptoms test feature located in the home page to check if you might be Covid positive or not. You can also use the emergency button if your conditions are serious."));
        faqList.add(new FaqClass("Q: Can I scan other QR codes with the C19Trace app?", "Unfortunately, no. The app is only designated to scan C19Trace app QR codes."));
        faqList.add(new FaqClass("Q: Why does my risk status is still not changed even though I’m fully recovered?", "For users who wish to update their risk status, they would need to take the Covid-19 symptoms test on the app again. It will normally take 1-2 working days for the status to be updated"));
    }


}