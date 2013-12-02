package mobile.mylocationstats;

import java.util.Observable;
import java.util.Observer;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Location;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OverviewFragment extends Fragment implements Observer {

	private GoogleMap gMap;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.activity_overview, container, false);
		initComponents();
		return v;
	}

	private void initComponents() {
		gMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
	}

	public void updateLocation(Location location) {
		LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
		if (gMap != null) {
			gMap.clear();
			gMap.addMarker(new MarkerOptions().position(currentPosition).title("You"));
			gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 15));
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Fragment fragment = (getFragmentManager().findFragmentById(R.id.map));
		FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
		ft.remove(fragment);
		ft.commit();
	}

	@Override
	public void update(Observable observable, Object data) {
		Log.v("MYLOCATION", "It works!");
		updateLocation((Location) data);
	}
}
