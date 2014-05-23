package tw.edu.tp.cksh.test_01;

import java.io.IOException;
import java.util.List;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.TextView;

public class Map_Now implements LocationListener {
	
	public MainActivity_NowPosition refnow;

	@Override
	public void onLocationChanged(Location loc) {
		// TODO Auto-generated method stub
		TextView label = (TextView) refnow.findViewById(R.id.textView1);
		Geocoder geocoder = new Geocoder(refnow);
		try {
			List<Address> address = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
			if (address.size() > 0){
				label.setText(address.get(0).toString());
			}
		} catch (IOException e) {
			label.setText("無法聯網");
		}
		refnow.updateMapCroodinate(loc.getLatitude(), loc.getLongitude());
		refnow.updateMapImage();
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

}
