package csi.fhict.org.csi_week_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nick on 17-3-2016.
 */
public class CrimeListAdapter extends ArrayAdapter<Crime> {

    private Context context;
    private List<Crime> crimes;

    public CrimeListAdapter(Context context, List<Crime> crimes) {
        super(context, R.layout.crimelistitem, crimes);

        this.context = context;
        this.crimes = crimes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View crimeView;
        Crime requestedCrime = crimes.get(position);

        if (convertView != null) {
            crimeView = convertView;
        } else {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            crimeView = inflater.inflate(R.layout.crimelistitem, null);
        }

        TextView crimeName = (TextView) crimeView.findViewById(R.id.tvName);
        crimeName.setText(requestedCrime.name);

        TextView crimeBounty = (TextView) crimeView.findViewById(R.id.tvBounty);
        crimeBounty.setText((String.valueOf(requestedCrime.bountyInDollars)));

        TextView crimeDescription = (TextView) crimeView.findViewById(R.id.tvDescription);
        crimeDescription.setText(requestedCrime.description);

        return crimeView;
    }
}
