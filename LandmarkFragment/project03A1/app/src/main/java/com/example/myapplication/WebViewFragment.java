package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        retain this fragment across configuration changes
//        setRetainInstance(true);
    }
    // constant to identify the argument key for the URL
    public static final String ARG_URL = "url";
    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // finds the webView by id and initializes it
        View view = inflater.inflate(R.layout.fragment_web_view, container, false);
        webView = view.findViewById(R.id.webview_fragment);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // allows for the loading of URLs within this webView
                view.loadUrl(url);
                // tell the host application handles the URL
                return false;
            }
        });
        //returns the view for this fragment
        return view;
    }

    public void updateWebView(String url) {
        //checks if the webView is not null and loads the given URL
        if (webView != null) {
            webView.loadUrl(url);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // retrieve the URL from the fragment arguments and loads it into the webView
        String url = getArguments().getString(ARG_URL, "");
        updateWebView(url);
    }
}
