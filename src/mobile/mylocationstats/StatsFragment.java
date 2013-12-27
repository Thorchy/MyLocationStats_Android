package mobile.mylocationstats;

import java.util.ArrayList;

import mobile.mylocationstats.domain.Facade;
import mobile.mylocationstats.domain.Location;
import mobile.mylocationstats.ui.listviews.StatisticsBaseAdapter;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
		facade = new Facade(getActivity());
		lvLocations = (ListView) v.findViewById(R.id.lvLocations);	
		lvLocations.setAdapter(new StatisticsBaseAdapter(v.getContext(), (ArrayList<Location>) facade.getAllVisitedLocations()));
	}

}
