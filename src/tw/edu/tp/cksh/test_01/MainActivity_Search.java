package tw.edu.tp.cksh.test_01;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.Service;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity_Search extends Activity {

	
	TextView colorRGB;
	ImageView imgSource1;
	Vibrator vVi;
	
	
	private LocationManager manager;
	private Map_Sea listener;
	private int zoom = 16;
	private double lat = 25.1105474, lon = 121.526135;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_activity__search);manager = (LocationManager)this.getSystemService(LOCATION_SERVICE);
		Location coordinate = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		vVi = (Vibrator)this.getApplication().getSystemService(Service.VIBRATOR_SERVICE);
		   
		
		colorRGB = (TextView)findViewById(R.id.textView1);
		
		imgSource1 = (ImageView)findViewById(R.id.imageView1);
		imgSource1.setOnTouchListener(imgSourceOnTouchListener);
		
		
		
		listener = new Map_Sea();
		listener.refsea= this;
		if(manager.getProvider(LocationManager.GPS_PROVIDER)!=null){
			manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10*1000, 30, listener);
		}
		if(manager.getProvider(LocationManager.NETWORK_PROVIDER)!=null){
			manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5*1000, 10, listener);
		}
	}
	public void onZoomin(View v) {
		zoom++;
		updateMapImage();
	}
	public void onZoomout(View v) {
		zoom--;
		updateMapImage();
	}
	
	public void updateMapCroodinate(double new_lat, double new_lon) {
		this.lat = new_lat;
		this.lon = new_lon;
	}
	

    public void updateMapImage() {
	    	try {
				URL url = new URL("http://maps.googleapis.com/maps/api/staticmap?" + 
							      "zoom=" + zoom + "&size=1000x1000" +
							      "&sensor=false&center=" + lat + "," + lon);

				DownloadAsyncTask_Sea task = new DownloadAsyncTask_Sea();
				task.refsea = this;
				task.execute(url);
			} catch (MalformedURLException e) {
			}
	    }
	@Override
	protected void onDestroy() {
		super.onDestroy();
		manager.removeUpdates(listener);
	}
	
	
	OnTouchListener imgSourceOnTouchListener
    = new OnTouchListener(){

  @Override
  public boolean onTouch(View view, MotionEvent event) {
   
   float eventX = event.getX();
   float eventY = event.getY();
   float[] eventXY = new float[] {eventX, eventY};
   
   Matrix invertMatrix = new Matrix();
   ((ImageView)view).getImageMatrix().invert(invertMatrix);
   
   invertMatrix.mapPoints(eventXY);
   int x = Integer.valueOf((int)eventXY[0]);
   int y = Integer.valueOf((int)eventXY[1]);
   
   
   Drawable imgDrawable = ((ImageView)view).getDrawable();
   Bitmap bitmap = ((BitmapDrawable)imgDrawable).getBitmap();
   
   
   
   //Limit x, y range within bitmap
   if(x < 0){
    x = 0;
   }else if(x > bitmap.getWidth()-1){
    x = bitmap.getWidth()-1;
   }
   
   if(y < 0){
    y = 0;
   }else if(y > bitmap.getHeight()-1){
    y = bitmap.getHeight()-1;
   }

   int touchedRGB = bitmap.getPixel(x, y);
   
   colorRGB.setText("touched color: " + touchedRGB);
   colorRGB.setTextColor(touchedRGB);
   
   
   if(	touchedRGB == -65794 || 
		touchedRGB ==-1 || 
		touchedRGB ==-3948875|| 
		touchedRGB ==-3357767||
		touchedRGB ==-6843505){
	   
	   vVi.vibrate( 50 );
   }
   
   return true;
  }};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity__search, menu);
		return true;
	}

}
