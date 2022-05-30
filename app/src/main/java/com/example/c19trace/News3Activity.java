package com.example.c19trace;

import androidx.appcompat.app.AppCompatActivity;

import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class News3Activity extends AppCompatActivity {

    private WebView news3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news3);

        news3 = findViewById(R.id.webView_news3);

        news3.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
                handler.proceed();
            }
        });

        news3.loadUrl("https://www.freemalaysiatoday.com/category/nation/2022/03/25/64-covid-19-deaths-new-cases-rise-to-24316/");

        WebSettings webSettings = news3.getSettings();
        webSettings.setJavaScriptEnabled(true);

        news3.clearCache(true);
        news3.loadUrl("https://www.freemalaysiatoday.com/category/nation/2022/03/25/64-covid-19-deaths-new-cases-rise-to-24316/");
    }

    @Override
    public void onBackPressed()
    {
        if(news3.canGoBack())
        {
            news3.goBack();
        } else
        {
            super.onBackPressed();
        }
    }
}