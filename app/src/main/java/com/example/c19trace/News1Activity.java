package com.example.c19trace;

import androidx.appcompat.app.AppCompatActivity;

import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class News1Activity extends AppCompatActivity {

    private WebView news1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news1);

        news1 = findViewById(R.id.webView_news1);

        news1.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
                handler.proceed();
            }
        });

        news1.loadUrl("https://www.nst.com.my/news/nation/2022/03/782854/kj-fully-vaccinated-malaysia-singapore-land-travellers-exempted-covid-19");

        WebSettings webSettings = news1.getSettings();
        webSettings.setJavaScriptEnabled(true);

        news1.clearCache(true);
        news1.loadUrl("https://www.nst.com.my/news/nation/2022/03/782854/kj-fully-vaccinated-malaysia-singapore-land-travellers-exempted-covid-19");
    }

    @Override
    public void onBackPressed()
    {
        if(news1.canGoBack())
        {
            news1.goBack();
        } else
        {
            super.onBackPressed();
        }
    }
}