package mobile.mylocationstats;

import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import mobile.mylocationstats.db.Database;
import mobile.mylocationstats.domain.Facade;
import mobile.mylocationstats.domain.Location;
import mobile.mylocationstats.domain.MyLocation;
import mobile.mylocationstats.domain.TabNames;
import mobile.mylocationstats.domain.Visit;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MyLocationActivity extends Activity {

	private LocationManager locationManager;
	private MyLocation locationListener = new MyLocation();
	private String locationProviderGPS;
	private String locationProviderNetwork;
	private Facade facade;
	private Database db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_location);
		initComponents();
		getLocation();
		facade = new Facade(this);
		db = facade.getDatabase();
	}

	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(locationListener);
	}

	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(locationProviderNetwork, 0, 0, locationListener);
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			locationManager.requestLocationUpdates(locationProviderGPS, 0, 0, locationListener);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.my_location, menu);
		return true;
	}

	private void initComponents() {
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {

			Fragment newFragment = new OverviewFragment();

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
			}

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				if (tab != null) {

					TabNames newTab = TabNames.valueOf(tab.getTag().toString());
					FragmentTransaction transaction = getFragmentManager().beginTransaction();

					switch (newTab) {
					case HOME:
						newFragment = new OverviewFragment();
						((Observable) locationListener).addObserver((Observer) newFragment);
						break;
					case MAP:
						newFragment = new MyMapFragment();
						((Observable) locationListener).addObserver((Observer) newFragment);
						break;
					case STATS:
						newFragment = new StatsFragment();
						break;
					case TARGETS:
						newFragment = new TargetOverviewFragment();
						break;
					}

					transaction.replace(R.id.fragment_container, newFragment);
					transaction.addToBackStack(null);
					transaction.commit();	
				}
			}

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				ft.remove(newFragment);
			}
		};

		actionBar.addTab(actionBar.newTab().setText("Home").setTag(TabNames.HOME).setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setText("Map").setTag(TabNames.MAP).setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setText("Stats").setTag(TabNames.STATS).setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setText("Targets").setTag(TabNames.TARGETS).setTabListener(tabListener));
	}

	private void getLocation() {
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			locationProviderGPS = LocationManager.GPS_PROVIDER;
			locationManager.requestLocationUpdates(locationProviderGPS, 0, 0, locationListener);
		}

		locationProviderNetwork = LocationManager.NETWORK_PROVIDER;
		locationManager.requestLocationUpdates(locationProviderNetwork, 0, 0, locationListener);
		
		locationListener.notifyLastLocation(locationManager.getLastKnownLocation(locationProviderNetwork));
	}
	
	public void addLocation(View v) {
		Intent locationIntent = new Intent(this, TargetAddActivity.class);
		startActivity(locationIntent);
	}
	
	public void checkIn(View v) {
		android.location.Location loc = locationListener.getLatestLocation();
		Location visited = new Location(loc.getLatitude(), loc.getLongitude());
		
		Geocoder gc = new Geocoder(this, Locale.getDefault());
		try {
			Address a = gc.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1).get(0);
			visited.setName(a.getThoroughfare());
		} catch (IOException e) {
			Log.e("MY LOCATION", e.getMessage());
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(loc.getTime());
		
		db.addVisit(new Visit(visited, cal));
		Toast.makeText(this, "Checked in at " + visited.getName(), Toast.LENGTH_LONG).show();
	}
}
