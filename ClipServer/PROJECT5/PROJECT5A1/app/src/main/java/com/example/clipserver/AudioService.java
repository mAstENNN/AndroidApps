package com.example.clipserver;

import android.app.Service;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.clipserver.AudioAIDL;

public class AudioService extends Service {
    private MediaPlayer mediaPlayer;
    private static final String CHANNEL_ID = "Music player style" ;
    private static final int NOTIFICATION_ID = 1;


    @Override
    public IBinder onBind(Intent intent) {
        //return an instance of the implementation of AudioAIDL
        return new AudioServiceImpl();
    }

    private final class AudioServiceImpl extends AudioAIDL.Stub {
        @Override
        public void play(String songName) throws RemoteException {

            if (mediaPlayer != null) {
                mediaPlayer.stop(); //stop current playback
                mediaPlayer.release(); //release resources
            }

            int songResource = getSongResource(songName); //get song resourceid
            mediaPlayer = MediaPlayer.create(AudioService.this, songResource); //create and setup mediaplayer
            mediaPlayer.setOnCompletionListener(mp -> {
                try {
                    stop(); //stop playback on completion
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            });
            mediaPlayer.start();
        }

        @Override
        public void pause() throws RemoteException {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause(); //pause playback
            }
        }

        @Override
        public void stop() throws RemoteException {
            if (mediaPlayer != null) {
                mediaPlayer.stop(); //stop playback
                mediaPlayer.release(); //release resources
                mediaPlayer = null; //clear mediaplayer instance
            }
//            stopForeground(true);
//            stopSelf();
        }

        @Override
        public void resume() throws RemoteException {
            if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                mediaPlayer.start(); //resume playback
            }
        }

        @Override
        public void stopService() throws RemoteException {
            stopSelf();
        }


        //return songids
        private int getSongResource(String songName) {

            switch (songName) {
                case "jungle":
                    return R.raw.jungle;
                case "mysterious":
                    return R.raw.mysterious;
                case "stranger":
                    return R.raw.stranger;
                case "reggaeton":
                    return R.raw.reggaeton;
                case "backwards":
                    return R.raw.backwards;
                default:
                    throw new IllegalArgumentException("Unknown song: " + songName);
            }
        }

    }
}
