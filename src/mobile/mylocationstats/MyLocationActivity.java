package mobile.mylocationstats;

import java.util.Observable;
import java.util.Observer;

import mobile.mylocationstats.domain.MyLocation;
import mobile.mylocationstats.domain.TabNames;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;

public class MyLocationActivity extends Activity {

	private LocationManager locationManager;
	private MyLocation locationListener = new MyLocation();
	private String locationProviderGPS;
	private String locationProviderNetwork;
	
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

			private Fragment newFragment = new OverviewFragment();

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
						newFragment = new TargetFragment();
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
}
