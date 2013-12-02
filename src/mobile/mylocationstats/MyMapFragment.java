package mobile.mylocationstats;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Location;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyMapFragment extends Fragment {

	private GoogleMap gMap;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_map, container, false);
		initComponents();
		return v;
	}

	private void initComponents() {
		gMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.fullMap)).getMap();
	}

	public void updateLocation(Location location) {
		LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
		gMap.clear();
		gMap.addMarker(new MarkerOptions().position(currentPosition).title("You"));
		gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 15));
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Fragment fragment = (getFragmentManager().findFragmentById(R.id.fullMap));
		FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
		ft.remove(fragment);
		ft.commit();
	}

}
