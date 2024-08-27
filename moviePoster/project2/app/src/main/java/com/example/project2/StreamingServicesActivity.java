package com.example.project2;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class StreamingServicesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient());
        setContentView(webView);

        String wikiUrl = getIntent().getStringExtra("streamUrl");
        webView.loadUrl(wikiUrl);
    }

}