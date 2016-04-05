package com.example.bo.csi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Criminal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.criminal);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        TextView txtName = (TextView) findViewById(R.id.text2);
        txtName.setText(name);

        ImageView imgView = (ImageView) findViewById(R.id.imageView);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), CriminalActions.class);
                startActivity(intent);

            }
        });
    }
}
