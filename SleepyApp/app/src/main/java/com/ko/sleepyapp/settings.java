package com.ko.sleepyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        TextView textBack = (TextView) findViewById(R.id.back);
        textBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        TextView textMusicPreferences = (TextView) findViewById(R.id.musicpreference);
        textMusicPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        TextView textMusicSource = (TextView) findViewById(R.id.musicsource);
        textMusicSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MusicSource.class);
                startActivity(intent);
            }
        });

        TextView textDeviceConnection = (TextView) findViewById(R.id.deviceconnection);
        textDeviceConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Devices.class);
                startActivity(intent);
            }
        });

        TextView textPreferences = (TextView) findViewById(R.id.preferences);
        textPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Preferences.class);
                startActivity(intent);
            }
        });
        }
    }


