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
import android.widget.TextView;

public class OverviewFragment extends Fragment implements Observer {

	private GoogleMap gMap;
	private Facade facade;
	private TextView mostVisited;
	private TextView lastVisited;
	private TextView closestTarget;
	private boolean init;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_overview, container, false);
		initComponents(v);
		return v;
	}

	private void initComponents(View v) {
		gMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		facade = new Facade(getActivity());

		mostVisited = (TextView) v.findViewById(R.id.txtMostVisited);
		lastVisited = (TextView) v.findViewById(R.id.txtLastVisited);
		closestTarget = (TextView) v.findViewById(R.id.txtClosestTarget);

		mostVisited.setText(facade.getMostVisited());
		lastVisited.setText(facade.getLastVisited());
	}

	public void updateLocation(Location location) {
		LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
		if (gMap != null) {
			gMap.clear();
			gMap.addMarker(new MarkerOptions().position(currentPosition).title("You"));
			if (!init) {
				gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 15));
				init = !init;
			}
		}

		if (facade != null) {
			Target nearest = facade.getDatabase().getNearestTarget(new mobile.mylocationstats.domain.Location(location.getLatitude(), location.getLongitude()));
			if (nearest != null && nearest.getLocation() != null) {
				closestTarget.setText(nearest.getLocation().getName());
				gMap.addMarker(new MarkerOptions().position(new LatLng(nearest.getLatitude(), nearest.getLongitude())).title("Target"));
			} else {
				closestTarget.setText("No nearest target found.");
			}
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
		if (facade != null) {
			facade.checkVisited((Location) data);
		}
		updateLocation((Location) data);
	}
}
