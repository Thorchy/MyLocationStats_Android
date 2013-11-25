package mobile.mylocationstats.domain;

import mobile.mylocationstats.MyLocationActivity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;


public class MyLocation implements LocationListener {
	
	private Context context;
		
	public MyLocation(Context context) {
		this.context = context;
	}

	public void onLocationChanged(Location location) {
		// Called when a new location is found by the network location
		// provider.
		//TODO: Change to observer!
		((MyLocationActivity) context).updateLocation(location);
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	public void onProviderEnabled(String provider) {
	}

	public void onProviderDisabled(String provider) {
	}

}
