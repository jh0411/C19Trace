package com.example.c19trace.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.c19trace.R;

public class News4Activity extends AppCompatActivity {

    private WebView news4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news4);
        news4 = findViewById(R.id.webView_news4);

        news4.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
                handler.proceed();
            }
        });

        news4.loadUrl("https://www.theedgemarkets.com/article/covid19-vaccination-36-malaysian-children-given-first-jab-march-23");

        WebSettings webSettings = news4.getSettings();
        webSettings.setJavaScriptEnabled(true);

        news4.clearCache(true);
        news4.loadUrl("https://www.theedgemarkets.com/article/covid19-vaccination-36-malaysian-children-given-first-jab-march-23");
    }

    @Override
    public void onBackPressed()
    {
        if(news4.canGoBack())
        {
            news4.goBack();
        } else
        {
            super.onBackPressed();
        }
    }
}