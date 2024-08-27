package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient());
        setContentView(webView);

        String url = getIntent().getStringExtra("url");
        webView.loadUrl(url);
    }

}
