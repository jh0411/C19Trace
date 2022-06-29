package com.example.c19trace.Home.VaccinationForm;

import androidx.appcompat.app.AppCompatActivity;

import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.c19trace.R;

public class ReadVaccineInfoActivity extends AppCompatActivity {

    private WebView vaccInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_vaccine_info);

        vaccInfo = findViewById(R.id.webView_vaccineInfo);

        vaccInfo.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
                handler.proceed();
            }
        });

        vaccInfo.loadUrl("https://refugeemalaysia.org/support/covid-19/vaccination/");

        WebSettings webSettings = vaccInfo.getSettings();
        webSettings.setJavaScriptEnabled(true);

        vaccInfo.clearCache(true);
        vaccInfo.loadUrl("https://refugeemalaysia.org/support/covid-19/vaccination/");
    }
}