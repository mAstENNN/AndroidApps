package com.example.project2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class FullPosterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView imageView = new ImageView(this);
        setContentView(imageView);

        int imageResId = getIntent().getIntExtra("ImageResourceId", 0);
        imageView.setImageResource(imageResId);

        imageView.setOnClickListener(v -> {
            String url = getIntent().getStringExtra("url");
            Intent intent = new Intent(v.getContext(), WebViewActivity.class);
            intent.putExtra("url", url);
            startActivity(intent);
        });
    }
}
