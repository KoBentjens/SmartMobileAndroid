package csi.fhict.org.csi_week_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.HashMap;
import java.util.List;

public class Criminalist extends AppCompatActivity {

    ListView lvCriminals;
    String[] criminals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criminalist);

        CriminalProvider cp = new CriminalProvider(this);
        CriminalListAdapter cla = new CriminalListAdapter(this, cp.GetCriminals());

        lvCriminals = (ListView) findViewById(R.id.listView);

        criminals = getResources().getStringArray(R.array.names);
        lvCriminals.setAdapter(cla);

        lvCriminals.setOnItemClickListener(new checkClick());
    }


    private class checkClick implements android.widget.AdapterView.OnItemClickListener {


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent i = new Intent(getBaseContext(), MainActivity.class);
            i.putExtra("chosenCriminalPosition", position);
            startActivity(i);
        }
    }
}
