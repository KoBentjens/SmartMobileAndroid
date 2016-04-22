package com.ko.sleepyapp;

import android.media.MediaRecorder;

import java.io.IOException;

/**
 * Created by Bo on 8-4-2016.
 */
public class SoundMeter {
    private MediaRecorder mRecorder = null;

    public void start() throws IOException {
        if (mRecorder == null) {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.setOutputFile("/dev/null");
            mRecorder.prepare();

            mRecorder.start();
            mRecorder.getMaxAmplitude();
        }
    }

    public void stop() {
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
           mRecorder = null;
         }
    }

    public double getAmplitude() {
        if (mRecorder != null)
            return  mRecorder.getMaxAmplitude();
        else
            return 0;

    }
}
