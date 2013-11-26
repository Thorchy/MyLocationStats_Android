package mobile.mylocationstats;

import mobile.mylocationstats.domain.MyLocation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;

@SuppressLint("NewApi")
public class MyLocationActivity extends Activity {

	private LocationManager locationManager;
	private LocationListener locationListener;
	String locationProvider;
	private GoogleMap gMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_location);
		initComponents();
		getLocation();
	}

	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(locationListener);
	}

	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_location, menu);
		return true;
	}

	private void initComponents() {
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			locationProvider = LocationManager.GPS_PROVIDER;
		} else {
			locationProvider = LocationManager.NETWORK_PROVIDER;
		}

		gMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		updateLocation(locationManager.getLastKnownLocation(locationProvider));
	}

	private void getLocation() {
		locationListener = new MyLocation(this);
		locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
	}

	public void updateLocation(Location location) {
		LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
		gMap.clear();
		gMap.addMarker(new MarkerOptions().position(currentPosition).title("You"));
		gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 15));
	}
}
