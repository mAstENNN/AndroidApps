package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements LandmarkListFragment.OnLandmarkSelectedListener {

    //for tracking the screen orientation
    private boolean screen = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sets the layout for the main activity
        setContentView(R.layout.activity_main);

        //finds the toolbar view by id
        Toolbar toolbar = findViewById(R.id.toolbar);
        //sets the toolbar as the app's action bar
        setSupportActionBar(toolbar);
        toolbar.setSubtitleTextColor(ContextCompat.getColor(this, android.R.color.black));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("A1");

        if (findViewById(R.id.webview_fragment_container) != null) {
            //landscape mode is true
            screen = true;
            if (savedInstanceState != null) {
                //if there is a saved instance state do not replace fragments
                return;
            }
            LandmarkListFragment firstFragment = new LandmarkListFragment();
            //adds the first fragment to the container
            getSupportFragmentManager().beginTransaction().add(R.id.landmark_list_container, firstFragment).commit();
        } else {
            //portrait mode setup
            if (savedInstanceState == null) {
                //replaces the container content with a new landmark list fragment
                getSupportFragmentManager().beginTransaction().replace(R.id.landmark_list_container, new LandmarkListFragment()).commit();
            }
        }
    }

    @Override
    public void onLandmarkSelected(String url) {
        if (screen) {
            //update the layout parameters to adjust the width of the first fragment container
            View listContainer = findViewById(R.id.landmark_list_container);
            if (listContainer != null) {
                // 1/3 of the screen
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
                //applies the new layout parameter
                listContainer.setLayoutParams(params);
            }

            WebViewFragment webViewFragment = (WebViewFragment) getSupportFragmentManager().findFragmentById(R.id.webview_fragment);
            if (webViewFragment != null) {
                //updates the existing web view fragment with the new url
                webViewFragment.updateWebView(url);
            } else {
                WebViewFragment newFragment = new WebViewFragment();
                Bundle args = new Bundle();
                //passes the url to the new fragment
                args.putString(WebViewFragment.ARG_URL, url);
                newFragment.setArguments(args);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //replaces the current fragment with the new one
                transaction.replace(R.id.webview_fragment_container, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        } else {
            // launch webViewActivity in portrait mode
            Intent intent = new Intent(this, WebViewActivity.class);
            //passes the url to the new activity
            intent.putExtra("url", url);
            startActivity(intent);
        }
    }

//    //sets the title of the action bar
//    public void setActionBarTitle(String title) {
//        getSupportActionBar().setTitle(title);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.exitapp) {
            //closes the application
            finish();
            return true;
        } else if (itemId == R.id.launchApp) {
            //launches A2
            launchA2();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void launchA2() {
        Intent intent = new Intent("com.example.project03a2.ACTION_VIEW");
        intent.setPackage("com.example.project03a2");
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // check if the webViewFragment is visible in landscape mode
        FragmentManager fragmentManager = getSupportFragmentManager();
        WebViewFragment webViewFragment = (WebViewFragment) fragmentManager.findFragmentById(R.id.webview_fragment_container);
        if (screen && webViewFragment != null && webViewFragment.isVisible()) {
            // adjust layout parameters of the LandmarkListFragment container to  full screen
            View listContainer = findViewById(R.id.landmark_list_container);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            listContainer.setLayoutParams(params);
            fragmentManager.popBackStackImmediate();
        } else {
            //calls the superclass method
            super.onBackPressed();
        }
    }
}

