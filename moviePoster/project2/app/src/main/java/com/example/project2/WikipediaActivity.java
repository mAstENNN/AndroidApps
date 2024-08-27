package com.example.project2;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class WikipediaActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient());
        setContentView(webView);

        String wikiUrl = getIntent().getStringExtra("wikiUrl");
        webView.loadUrl(wikiUrl);
    }

}
