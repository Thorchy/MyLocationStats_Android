package mobile.mylocationstats;

import java.util.Observable;
import java.util.Observer;

import mobile.mylocationstats.domain.Facade;
import mobile.mylocationstats.domain.Target;

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

public class MyMapFragment extends Fragment implements Observer {

	private GoogleMap gMap;
	private boolean init;
	private Facade facade;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_map, container, false);
		initComponents();
		return v;
	}

	private void initComponents() {
		facade = new Facade();
		gMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.fullMap)).getMap();
	}

	public void updateLocation(Location location) {
		if (gMap != null) {
			LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
			gMap.clear();
			gMap.addMarker(new MarkerOptions().position(currentPosition).title("You"));
			
			if (!init) {
				gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 15));
				init = !init;
			}
			
			if (facade != null) {
				Target nearest = facade.getDatabase().getNearestTarget(new mobile.mylocationstats.domain.Location(location.getLatitude(), location.getLongitude()));
				gMap.addMarker(new MarkerOptions().position(new LatLng(nearest.getLatitude(), nearest.getLongitude())).title("Target"));
			}
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Fragment fragment = (getFragmentManager().findFragmentById(R.id.fullMap));
		FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
		ft.remove(fragment);
		ft.commit();
	}

	@Override
	public void update(Observable observable, Object data) {
		updateLocation((Location) data);
	}

}
