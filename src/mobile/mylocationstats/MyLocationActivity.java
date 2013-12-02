package mobile.mylocationstats;

import mobile.mylocationstats.domain.MyLocation;
import mobile.mylocationstats.domain.TabNames;

import com.google.android.gms.maps.model.LatLng;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class MyLocationActivity extends Activity {

	private Context context;
	private LocationManager locationManager;
	private LocationListener locationListener;
	String locationProvider;

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
		//updateLocation(locationManager.getLastKnownLocation(locationProvider));
		
		context = this;
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			
			private Fragment newFragment = new OverviewFragment();

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				if (tab != null) {
					Log.v("MYLOCATION", tab.getTag().toString());
					
					TabNames newTab = TabNames.valueOf(tab.getTag().toString());
					FragmentTransaction transaction = getFragmentManager().beginTransaction();

					switch (newTab) {
					case HOME:
						newFragment = new OverviewFragment();
						break;
					case MAP:
						newFragment = new MyMapFragment();
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
		locationListener = new MyLocation(this);
		locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
	}
	
	public void updateLocation(Location location) {
		LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
		//
	}

}
