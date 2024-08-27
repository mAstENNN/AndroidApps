package com.example.audioclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.clipserver.AudioAIDL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity"; // for log tag
    private AudioAIDL clipserver; //aidl interface for binding the service
    private boolean mIsBound = false; //flag to track service binding
    private String currentClip; //holds the currently selected audio clip
    private Button start_service, stop_service, play, pause, resume, stop_playback;
    private ListView listViewClips; //list view for displaying available clips
    private TextView selectedItem, status;  //text views for displaying selected item and connection status
    private static final String CHANNEL_ID = "Music player style" ; //notification channel id
    private static final int NOTIFICATION_ID = 1; //notification id



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewClips = findViewById(R.id.listViewClips);
        selectedItem = findViewById(R.id.selectedItem);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                new String[]{"jungle", "backwards", "mysterious", "reggaeton", "stranger"});
        listViewClips.setAdapter(adapter);

        listViewClips.setOnItemClickListener((parent, view, position, id) -> {
            currentClip = (String) parent.getItemAtPosition(position);
            selectedItem.setText("Selected: " + currentClip);
        });

        start_service = findViewById(R.id.StartService);
        stop_service = findViewById(R.id.StopService);
        play = findViewById(R.id.Play);
        pause = findViewById(R.id.Pause);
        resume = findViewById(R.id.Resume);
        stop_playback = findViewById(R.id.Stop);

        //set onClick listener for the start service button
        start_service.setOnClickListener(v -> {
            if (!mIsBound) {
                Intent intent = new Intent("musicservice");
                intent.setPackage("com.example.clipserver");
                bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
            }
            toggleButtons(true);

        });

        //set onClick listener for the stop service button
        stop_service.setOnClickListener(v -> {
            status = findViewById(R.id.status);
            try {
                clipserver.stop(); //stop the playback
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            if (mIsBound) {
                unbindService(mConnection); //unbind the service
                mIsBound = false; //update binding flag
            }
            Intent serviceIntent = new Intent(this, AudioAIDL.class); //stop the service
            stopService(serviceIntent); // update button states
            toggleButtons(false);
            play.setEnabled(false);
            pause.setEnabled(false);
            resume.setEnabled(false);
            stop_playback.setEnabled(false);
            start_service.setEnabled(true);
            status.setText("Status: DISCONNECTED"); // update status
        });

        play.setOnClickListener(v -> {

            if (mIsBound && clipserver != null && currentClip != null) {
                try {
                    clipserver.play(currentClip); //command the service to play the selected clip
                    Log.d(TAG, "Playing clip" + currentClip);
                    pause.setEnabled(true);
                    resume.setEnabled(false);
                    stop_playback.setEnabled(true);
                    start_service.setEnabled(false);
                    stop_service.setEnabled(true);
                } catch (Exception e) {
                    Log.e(TAG, "Error playing clip: ", e);
                }
            }
        });

        // set onClick listener for the pause button
        pause.setOnClickListener(v -> {
            if (mIsBound && clipserver != null) {
                try {
                    clipserver.pause();
                    Log.e(TAG, "pausing clip");
                } catch (Exception e) {
                    Log.e(TAG, "Error pausing clip: ", e);
                }
            }
            resume.setEnabled(true);

        });

        // set onClick listener for the pause button
        resume.setOnClickListener(v -> {
            if (mIsBound && clipserver != null) {
                try {
                    clipserver.resume(); // command the service to resume playback
                    Log.e(TAG,"resuming clip");
                } catch (Exception e) {
                    Log.e(TAG, "Error resuming clip: ", e);
                }
            }
            resume.setEnabled(false); // disable the resume button after use

        });

        // set onClick listener for the stop playback button
        stop_playback.setOnClickListener(v -> {
            if (mIsBound && clipserver != null) {
                try {
                    clipserver.stop(); // command the service to stop playback
                    Log.e(TAG, "stopping clip");
                } catch (Exception e) {
                    Log.e(TAG, "Error stopping clip: ", e);
                }
            }
            pause.setEnabled(false); // disable the resume button after use
        });
    }

    // defines callbacks for service binding and passed to bindService()
    private final ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            status = findViewById(R.id.status);
            clipserver = AudioAIDL.Stub.asInterface(service); //cast the IBinder to AudioAIDL type
            mIsBound = true; //update the binding flag
            Log.d(TAG, "Service connected.");
            status.setText("Status: CONNECTED");  //update the status text view
//            toggleButtons(false);
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            clipserver = null;
            mIsBound = false;
            Log.d(TAG, "Service disconnected.");
            toggleButtons(false);
        }
    };

    // method to enable/disable buttons based on service state
    private void toggleButtons(boolean enable) {
        stop_service.setEnabled(!enable);
        play.setEnabled(enable);
        pause.setEnabled(!enable);
        resume.setEnabled(!enable);
        stop_playback.setEnabled(!enable);
        start_service.setEnabled(!enable);
    }

    // called when the activity is stopping
    @Override
    protected void onStop() {
        super.onStop();
        status = findViewById(R.id.status);
        if (mIsBound) {
            unbindService(mConnection); //unbind the service on stop
            mIsBound = false;
            toggleButtons(false); //update button states
            Log.d(TAG, "Service unbound.");
        }
    }
}