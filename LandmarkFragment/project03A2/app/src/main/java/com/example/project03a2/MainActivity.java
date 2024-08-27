package com.example.project03a2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Toolbar toolbar = findViewById(R.id.toolbar);
            // set the Toolbar to act as the actionBar for this activity window
            setSupportActionBar(toolbar);

        String data = getIntent().getStringExtra("extra_data");
        int[] images = {
                R.drawable.museum_of_science_and_industry,
                R.drawable.wrigley_field,
                R.drawable.lincoln_park_zoo,
                R.drawable.sears_tower,
                R.drawable.the_art_institute_of_chicago,
                R.drawable.navy_pier};

        LinearLayout container = findViewById(R.id.landmarksContainer);

        for (int resId : images) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
            imageView.setLayoutParams(layoutParams);
            imageView.setImageResource(resId);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            container.addView(imageView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.exitapp) {
            finish();
            return true;
        } else if (id == R.id.launchApp) {
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
}
