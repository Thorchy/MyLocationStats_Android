package mobile.mylocationstats;

import java.util.ArrayList;

import mobile.mylocationstats.domain.Facade;
import mobile.mylocationstats.domain.Target;
import mobile.mylocationstats.ui.listviews.TargetsBaseAdapter;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class TargetOverviewFragment extends Fragment {
	
	private ListView lvTargets;
	private Facade facade;
	private View v;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_target_overview, container, false);
		this.v = v;
		initComponents();
		return v;
	}
	
	@Override
	public void onResume() {
		loadTargetOverview();
		super.onResume();
	}

	private void initComponents() {
		facade = new Facade();
		lvTargets = (ListView) v.findViewById(R.id.lvTargets);
	}
	
	private void loadTargetOverview() {
		lvTargets.setAdapter(new TargetsBaseAdapter(v.getContext(), (ArrayList<Target>) facade.getAllTargets()));
	}
}
