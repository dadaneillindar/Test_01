package tw.edu.tp.cksh.test_01;

import android.app.Activity;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private LocationManager manager;
	private Map listener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		manager = (LocationManager)this.getSystemService(LOCATION_SERVICE);
		Location coordinate = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		TextView label = (TextView)findViewById(R.id.crood_text);
		if (coordinate != null) {
			label.setText(coordinate.toString());
			//處理string
		} else {
			label.setText("沒有已知座標");
		}
		
		listener = new Map();
		listener.ref= this;
		manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5*1000, 10, listener);
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10*1000, 30, listener);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		manager.removeUpdates(listener);
	}
 	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
