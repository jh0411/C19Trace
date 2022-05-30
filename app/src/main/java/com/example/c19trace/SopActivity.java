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

        String pdf = "https://asset.mkn.gov.my/web/wp-content/uploads/sites/3/2019/08/MKN-SOP_ReopeningSafely_vF-ENG_Clean-1.pdf";
        sopView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);

        WebSettings webSettings = sopView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        sopView.clearCache(true);
        sopView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
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