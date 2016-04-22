package com.ko.sleepyapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.microsoft.band.BandClient;
import com.microsoft.band.BandClientManager;
import com.microsoft.band.BandException;
import com.microsoft.band.BandInfo;
import com.microsoft.band.ConnectionState;

import java.lang.ref.WeakReference;

public class Devices extends AppCompatActivity {

    private BandClient client = null;
    private Boolean bandConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);



        //final WeakReference<Activity> reference = new WeakReference<Activity>(this);
        new getDeviceConnection().execute();

        TextView textBandConnected = (TextView) findViewById(R.id.bandConnection);
        if(bandConnected)
        {
            textBandConnected.setText("Band is connected!");
        }
        else {
            textBandConnected.setText("Band is not connected");
        }



        //back button
        TextView textBack = (TextView) findViewById(R.id.back);
        textBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), settings.class);
                startActivity(intent);
            }
        });

    }

    private class getDeviceConnection extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params) {
            try {
                bandConnected = getConnectedBandClient();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BandException e) {
                e.printStackTrace();
            }
            finally {

            }
            return null;
        }
    }

    private boolean getConnectedBandClient() throws InterruptedException, BandException {
        TextView textBandConnected = (TextView) findViewById(R.id.bandConnection);
        if (client == null) {

            BandInfo[] devices = BandClientManager.getInstance().getPairedBands();
            if (devices.length == 0) {
                Log.i("", "Band isn't paired with your phone.\n");

                textBandConnected.setText("Band is not connected");
                return false;
            }
            client = BandClientManager.getInstance().create(getBaseContext(), devices[0]);
        } else if (ConnectionState.CONNECTED == client.getConnectionState()) {
            Log.i("tag","Connected");
            textBandConnected.setText("Band is successfully connected!");
            return true;
        }

        Log.i("tag", "Band is connecting...\n");
        textBandConnected.setText("Band is successfully connected!");
        return ConnectionState.CONNECTED == client.connect().await();
    }

}
