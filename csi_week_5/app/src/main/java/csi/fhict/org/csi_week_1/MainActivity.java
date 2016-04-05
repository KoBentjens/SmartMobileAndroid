package csi.fhict.org.csi_week_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnReport;
    private Criminal criminal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CriminalProvider cp = new CriminalProvider(this);
        Intent intent = getIntent();
        int chosenCriminalPosition = intent.getIntExtra("chosenCriminalPosition", 0);

        criminal = cp.GetCriminal(chosenCriminalPosition);

        ImageView ivCriminal = (ImageView) findViewById(R.id.criminalImage);
        ivCriminal.setImageDrawable(criminal.mugshot);

        CrimeListAdapter cla = new CrimeListAdapter(this, cp.GetCriminal(chosenCriminalPosition).crimes);

        ListView lvCrimes = (ListView) findViewById(R.id.lvCrimes);
        lvCrimes.setAdapter(cla);

        TextView tvName = (TextView) findViewById(R.id.tvBgName);
        tvName.setText(criminal.name);

        TextView tvGender = (TextView) findViewById(R.id.tvBgGender);
        tvGender.setText(criminal.gender);

        TextView tvAge = (TextView) findViewById(R.id.tvBgAge);
        tvAge.setText(String.valueOf(criminal.age));

        TextView tvBounty = (TextView) findViewById(R.id.tvBgBounty);
        tvBounty.setText("$" + String.valueOf(criminal.getBountyInDollars()));

        TextView tvDetails = (TextView) findViewById(R.id.tvDetails);
        tvDetails.setText(criminal.description);

        btnReport = (Button)findViewById(R.id.btnReport);
        btnReport.setOnClickListener(new ReportAction());
    }

    private class ReportAction implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getBaseContext(), ReportActivity.class);
            intent.putExtra("latitude",criminal.lastKnownLocation.getLatitude());
            intent.putExtra("longitude",criminal.lastKnownLocation.getLongitude());
            startActivity(intent);
        }
    }
}
