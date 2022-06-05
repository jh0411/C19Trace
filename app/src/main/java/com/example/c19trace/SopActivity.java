package com.example.c19trace;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import android.net.http.*;

public class SopActivity extends AppCompatActivity {

    private WebView sopView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sop);

        sopView = findViewById(R.id.webView_sop);

        sopView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
                handler.proceed();
            }
        });

        sopView.loadUrl("https://covid-19.moh.gov.my/garis-panduan/garis-panduan-kkm");

        WebSettings webSettings = sopView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        sopView.clearCache(true);
        sopView.loadUrl("https://covid-19.moh.gov.my/garis-panduan/garis-panduan-kkm");
    }

    @Override
    public void onBackPressed()
    {
        if(sopView.canGoBack())
        {
            sopView.goBack();
        } else
        {
            super.onBackPressed();
        }
    }
}