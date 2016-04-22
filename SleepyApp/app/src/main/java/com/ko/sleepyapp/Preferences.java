package com.ko.sleepyapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;


import com.microsoft.band.BandClient;
import com.microsoft.band.BandClientManager;
import com.microsoft.band.BandException;
import com.microsoft.band.BandInfo;
import com.microsoft.band.BandIOException;
import com.microsoft.band.ConnectionState;
import com.microsoft.band.UserConsent;
import com.microsoft.band.sensors.BandHeartRateEvent;
import com.microsoft.band.sensors.BandHeartRateEventListener;
import com.microsoft.band.sensors.HeartRateConsentListener;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class Preferences extends AppCompatActivity {

    private double percentage;
    private BandClient client = null;


    private BandHeartRateEventListener mHeartRateEventListener = new BandHeartRateEventListener() {
        @Override
        public void onBandHeartRateChanged(final BandHeartRateEvent event) {
            Log.i("tag", String.valueOf(event.getHeartRate()));


            // 65 is de proef hearth rate om de app te sluiten
            if (event.getHeartRate() > 70)
            {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        final SoundMeter soundMeter = new SoundMeter();
        final AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //Volume
        final SeekBar barVolume = (SeekBar) findViewById(R.id.barVolume);
        final CheckBox checkVolume = (CheckBox) findViewById(R.id.checkVolume);

        final double[] backgroundNoise = new double[1];
        //Task volumedetection
        final Runnable volumeTask = new Runnable() {
            @Override
            public void run() {
                backgroundNoise[0] = soundMeter.getAmplitude();
                Log.i("Background Noise", String.valueOf(backgroundNoise[0]));

                Preferences.this.percentage = backgroundNoise[0] / 32768 * 100;


                barVolume.setProgress((int) percentage);
                soundMeter.stop();
            }
        };

        //checkbox volume
        checkVolume.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    barVolume.setEnabled(false);

                    try {
                        soundMeter.start();

                        //timer.schedule(volumeTask, 3000);
                        new Handler().postDelayed(volumeTask, 500);

                        //soundMeter.stop();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //barVolume.setProgress(75);
                } else {
                    barVolume.setEnabled(true);
                }
            }
        });

        barVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Max Volume", String.valueOf(audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC)));
                audio.setStreamVolume(AudioManager.STREAM_MUSIC, (int) (progress / 6.6f), 1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        final WeakReference<Activity> reference = new WeakReference<Activity>(this);

        //Duration
        final SeekBar barDuration = (SeekBar) findViewById(R.id.barDuration);
        final CheckBox checkDuration = (CheckBox) findViewById(R.id.checkDuration);
        checkDuration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    new HeartRateConsentTask().execute(reference);
                    new HeartRateSubscriptionTask().execute();
                    barDuration.setEnabled(false);
                } else {
                    barDuration.setEnabled(true);
                }
            }
        });

        //back button
        TextView textBack = (TextView) findViewById(R.id.back);
        textBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), settings.class);
                startActivity(intent);
            }
        });




        //band

    }




    private class HeartRateConsentTask extends AsyncTask<WeakReference<Activity>, Void, Void> {
        @Override
        protected Void doInBackground(WeakReference<Activity>... params) {
            try {
                if (getConnectedBandClient()) {

                    if (params[0].get() != null) {
                        client.getSensorManager().requestHeartRateConsent(params[0].get(), new HeartRateConsentListener() {
                            @Override
                            public void userAccepted(boolean consentGiven) {

                                Log.i("tag","userAccepted");

                            }
                        });
                    }
                } else {
                 Log.i("tag","Band isn't connected. Please make sure bluetooth is on and the band is in range.\n");
                }
            } catch (BandException e) {
                String exceptionMessage="";
                switch (e.getErrorType()) {
                    case UNSUPPORTED_SDK_VERSION_ERROR:
                        exceptionMessage = "Microsoft Health BandService doesn't support your SDK Version. Please update to latest SDK.\n";
                        break;
                    case SERVICE_ERROR:
                        exceptionMessage = "Microsoft Health BandService is not available. Please make sure Microsoft Health is installed and that you have the correct permissions.\n";
                        break;
                    default:
                        exceptionMessage = "Unknown error occured: " + e.getMessage() + "\n";
                        break;
                }
                Log.i("tag",exceptionMessage);

            } catch (Exception e) {
                Log.i("tag",e.getMessage());
            }
            return null;
        }
    }

    private boolean getConnectedBandClient() throws InterruptedException, BandException {
        if (client == null) {
            BandInfo[] devices = BandClientManager.getInstance().getPairedBands();
            if (devices.length == 0) {
                Log.i("","Band isn't paired with your phone.\n");
                return false;
            }
            client = BandClientManager.getInstance().create(getBaseContext(), devices[0]);
        } else if (ConnectionState.CONNECTED == client.getConnectionState()) {
            Log.i("tag","Connected");
            return true;
        }

       Log.i("tag","Band is connecting...\n");
        return ConnectionState.CONNECTED == client.connect().await();
    }




    @Override
    protected void onResume() {
        super.onResume();
        //txtStatus.setText("");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (client != null) {
            try {
                client.getSensorManager().unregisterHeartRateEventListener(mHeartRateEventListener);
            } catch (BandIOException e) {
                //appendToUI(e.getMessage());
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (client != null) {
            try {
                client.disconnect().await();
            } catch (InterruptedException e) {
                // Do nothing as this is happening during destroy
            } catch (BandException e) {
                // Do nothing as this is happening during destroy
            }
        }
        super.onDestroy();
    }


    private class HeartRateSubscriptionTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                if (getConnectedBandClient()) {
                    if (client.getSensorManager().getCurrentHeartRateConsent() == UserConsent.GRANTED) {
                        client.getSensorManager().registerHeartRateEventListener(mHeartRateEventListener);
                    } else {
                        Log.i("tag","You have not given this application consent to access heart rate data yet."
                                + " Please press the Heart Rate Consent button.\n");
                    }
                } else {
                    Log.i("tag","Band isn't connected. Please make sure bluetooth is on and the band is in range.\n");
                }
            } catch (BandException e) {
                String exceptionMessage="";
                switch (e.getErrorType()) {
                    case UNSUPPORTED_SDK_VERSION_ERROR:
                        exceptionMessage = "Microsoft Health BandService doesn't support your SDK Version. Please update to latest SDK.\n";
                        break;
                    case SERVICE_ERROR:
                        exceptionMessage = "Microsoft Health BandService is not available. Please make sure Microsoft Health is installed and that you have the correct permissions.\n";
                        break;
                    default:
                        exceptionMessage = "Unknown error occured: " + e.getMessage() + "\n";
                        break;
                }
                Log.i("tag",exceptionMessage);

            } catch (Exception e) {
                Log.i("tag",e.getMessage());
            }
            return null;
        }
    }


}
