package com.example.c19trace;

import androidx.appcompat.app.AppCompatActivity;

import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class News5Activity extends AppCompatActivity {

    private WebView news5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news5);
        news5 = findViewById(R.id.webView_news5);

        news5.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
                handler.proceed();
            }
        });

        news5.loadUrl("https://www.theborneopost.com/2022/03/25/airasia-reminds-guests-to-adhere-to-sop-for-international-travellers-when-malaysia-reopens/");

        WebSettings webSettings = news5.getSettings();
        webSettings.setJavaScriptEnabled(true);

        news5.clearCache(true);
        news5.loadUrl("https://www.theborneopost.com/2022/03/25/airasia-reminds-guests-to-adhere-to-sop-for-international-travellers-when-malaysia-reopens/");
    }

    @Override
    public void onBackPressed()
    {
        if(news5.canGoBack())
        {
            news5.goBack();
        } else
        {
            super.onBackPressed();
        }
    }
}