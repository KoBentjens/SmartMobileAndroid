package csi.fhict.org.csi_week_1;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import csi.fhict.org.csi_week_1.R;

@SuppressLint("InflateParams")  // See: https://code.google.com/p/android-developer-preview/issues/detail?id=1203
public class CriminalListAdapter extends ArrayAdapter<Criminal> {

	private Context context;
	private List<Criminal> criminals;

	public CriminalListAdapter(Context context, List<Criminal> criminals) {
		super(context, R.layout.criminallistitem, criminals);
		
		this.context = context;
		this.criminals = criminals;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Criminal requestedCriminal = criminals.get(position);
		View criminalView;

		//TOOD: replace this simple view by the layout as defined in criminallistitem.xml"
		if (convertView != null) {
			criminalView = convertView;
		} else {
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			criminalView = inflater.inflate(R.layout.criminallistitem, null);
		}

		ImageView criminalImage = (ImageView) criminalView.findViewById(R.id.mugshotImage);
		criminalImage.setImageDrawable(requestedCriminal.mugshot);

		TextView criminalName = (TextView) criminalView.findViewById(R.id.criminalName);
		criminalName.setText(requestedCriminal.name);

		TextView criminalBounty = (TextView) criminalView.findViewById(R.id.criminalBounty);
		criminalBounty.setText(("$" + requestedCriminal.getBountyInDollars()));

		return criminalView;
	}

}
