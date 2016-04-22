package com.ko.sleepyapp;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.browse.MediaBrowser;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class MusicSource extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_source);

        final SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);

        final CheckBox checkBoxGM = (CheckBox) findViewById(R.id.googlemusic);
        checkBoxGM.setChecked(settings.getBoolean(getString(R.string.sharedPreferencesGoogleMusic), false));
        checkBoxGM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showDialog("Log in screen van Google Music", "Google Music", checkBoxGM, getString(R.string.sharedPreferencesGoogleMusic));
                }
                else
                {
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putBoolean(getString(R.string.sharedPreferencesGoogleMusic), false);
                    editor.commit();
                }
            }
        });

        final CheckBox checkBoxS = (CheckBox) findViewById(R.id.spotify);
        checkBoxS.setChecked(settings.getBoolean(getString(R.string.SharedPreferencesSpotify), false));
        checkBoxS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    showDialog("Log in screen van Spotify", "Spotify", checkBoxS, getString(R.string.SharedPreferencesSpotify));
                }
                else
                {
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putBoolean(getString(R.string.SharedPreferencesSpotify), false);
                    editor.commit();
                }
            }
        });

        final CheckBox checkBoxSH = (CheckBox) findViewById(R.id.soundhoud);
        checkBoxSH.setChecked(settings.getBoolean(getString(R.string.SharedPreferencesSoundHound), false));
        checkBoxSH.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    showDialog("Log in screen van SoundHound", "SoundHound", checkBoxSH, getString(R.string.SharedPreferencesSoundHound));
                }
                else
                {
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putBoolean(getString(R.string.SharedPreferencesSoundHound), false);
                    editor.commit();
                }
            }
        });

        final CheckBox checkBoxLD = (CheckBox) findViewById(R.id.local);
        checkBoxLD.setChecked(settings.getBoolean(getString(R.string.SharedPreferencesLocalData), false));
        checkBoxLD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    showDialog("Select path for Local Data", "Local Data", checkBoxLD, getString(R.string.SharedPreferencesLocalData));
                }
                else
                {
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putBoolean(getString(R.string.SharedPreferencesLocalData), false);
                    editor.commit();
                }
            }
        });

        TextView textBack = (TextView) findViewById(R.id.back);
        textBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), settings.class);
                startActivity(intent);
            }
        });
    }

    public void showDialog(String message, String title, final CheckBox checkBox, final String sharedPref)
    {
       AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(message);
        dialog.setPositiveButton("Log in", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                checkBox.setChecked(true);

                SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);
                SharedPreferences.Editor editor = settings.edit();

                editor.putBoolean(sharedPref, true);
                editor.commit();
                Log.i(sharedPref, "true");

            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                checkBox.setChecked(false);

                SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);
                SharedPreferences.Editor editor = settings.edit();

                editor.putBoolean(sharedPref, false);
                editor.commit();
                Log.i(sharedPref,"false");

            }
        });
        dialog.setTitle(title);
        dialog.create().show();

    }
}
