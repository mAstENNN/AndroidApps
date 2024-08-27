package com.example.project4;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button startGameButton, exitAppButton, stopGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startGameButton = findViewById(R.id.startGameButton); //start game button
        exitAppButton = findViewById(R.id.exitAppButton); // exit app button
        stopGameButton = findViewById(R.id.stopGameButton); //stop game button

        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        exitAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {finishAffinity();}
        });

        stopGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this will send a broadcast to stop the game which GameActivity will handel
                Intent intent = new Intent("com.project4.ACTION_STOP_GAME");
                sendBroadcast(intent);
            }
        });
    }
}
