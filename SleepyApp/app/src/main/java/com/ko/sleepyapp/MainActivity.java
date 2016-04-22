package com.ko.sleepyapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    public MediaPlayer mp;
    private boolean playbutton = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LinearLayout settings = (LinearLayout) findViewById(R.id.linearsettings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), settings.class);
                startActivity(intent);
            }
        });


        final ImageView play = (ImageView) findViewById(R.id.imageButton);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //play click
                if(playbutton)
                {
                    play.setBackgroundResource(R.drawable.pausebutton);

                    stopPlaying();
                    mp = MediaPlayer.create(MainActivity.this,R.raw.colorofmysoul);
                    mp.start();

                    playbutton = false;
                }
                //pause click
                else{
                    stopPlaying();
                    play.setBackgroundResource(R.drawable.playbutton);
                    playbutton = true;
                }
            }
        });
    }

    private void stopPlaying() {
        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
        }
    }
}
