package mobile.mylocationstats;

import java.util.ArrayList;

import mobile.mylocationstats.domain.Facade;
import mobile.mylocationstats.domain.Location;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class StatsFragment extends Fragment {
	
	private Facade facade;
	private ListView lvLocations;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		// Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_stats, container, false);
        initComponents(v);
        return v;
    }
	
	private void initComponents(View v) {
		facade = new Facade();
		lvLocations = (ListView) v.findViewById(R.id.lvLocations);
		
		ArrayList<Location> locationList = (ArrayList<Location>) facade.getAllVisitedLocations();
		Location[] locations = new Location[locationList.size()];
		locationList.toArray(locations);

		ArrayAdapter<Location> adapter = new ArrayAdapter<Location>(v.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, locations);
		lvLocations.setAdapter(adapter);
	}

}
