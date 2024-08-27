// AudioAIDL.aidl
package com.example.clipserver;

// Declare any non-default types here with import statements

interface AudioAIDL {
    void play(String songName);
    void pause();
    void stop();
    void resume();
    void stopService();
}