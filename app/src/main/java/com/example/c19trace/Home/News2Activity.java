package com.example.c19trace.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.c19trace.R;

public class News2Activity extends AppCompatActivity {

    private WebView news2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news2);

        news2 = findViewById(R.id.webView_news2);

        news2.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
                handler.proceed();
            }
        });

        news2.loadUrl("https://www.thestar.com.my/news/nation/2022/03/24/covid-19-malaysia-to-seek-clarification-from-eu-over-non-recognition-of-sinovac-vaccine-says-kj");

        WebSettings webSettings = news2.getSettings();
        webSettings.setJavaScriptEnabled(true);

        news2.clearCache(true);
        news2.loadUrl("https://www.thestar.com.my/news/nation/2022/03/24/covid-19-malaysia-to-seek-clarification-from-eu-over-non-recognition-of-sinovac-vaccine-says-kj");
    }

    @Override
    public void onBackPressed()
    {
        if(news2.canGoBack())
        {
            news2.goBack();
        } else
        {
            super.onBackPressed();
        }
    }
}