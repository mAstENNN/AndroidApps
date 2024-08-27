package com.example.myapplication;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // sets the content view to the web view activity layout
        setContentView(R.layout.activity_web_view);
        //retrieves the url passed through the intent
        String url = getIntent().getStringExtra("url");
        //creates a new instance of the WebViewFragment
        WebViewFragment webViewFragment = new WebViewFragment();
        //creates a new bundle for fragment arguments
        Bundle args = new Bundle();
        // puts the url into the bundle
        args.putString(WebViewFragment.ARG_URL, url);
        //sets the fragment's arguments bundle
        webViewFragment.setArguments(args);
        //  begins a transaction to add the webview fragment to the container
        //replaces the current fragment in the container
        //commits the transaction to display the webview fragment
        getSupportFragmentManager().beginTransaction() .replace(R.id.fragment_container, webViewFragment).commit();
    }
}
