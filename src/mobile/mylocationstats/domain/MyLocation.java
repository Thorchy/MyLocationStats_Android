package mobile.mylocationstats.domain;

import java.util.Observable;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class MyLocation extends Observable implements LocationListener {

	private float lastAccuracy = Float.MAX_VALUE;
	private Location location;

	public void onLocationChanged(Location location) {
		if (location != null && location.getAccuracy() < lastAccuracy && location.hasAccuracy()) {
			this.location = location;
			notifyObservers(location);
		}
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	public void onProviderEnabled(String provider) {
	}

	public void onProviderDisabled(String provider) {
	}

	public void notifyLastLocation(Location location) {
		if (location != null) {
			if (location.getAccuracy() < lastAccuracy && location.hasAccuracy()) {
				this.location = location;
				notifyObservers(location);
			}
		}
	}

	@Override
	public void notifyObservers(Object data) {
		setChanged();
		super.notifyObservers(data);
	}

	public Location getLatestLocation() {
		return location;
	}

}
