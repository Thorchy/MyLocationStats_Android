package mobile.mylocationstats;

import java.util.ArrayList;

import mobile.mylocationstats.domain.Facade;
import mobile.mylocationstats.domain.Target;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
		ArrayList<Target> targetList = (ArrayList<Target>) facade.getAllTargets();
		Target[] targets = new Target[targetList.size()];
		targetList.toArray(targets);

		ArrayAdapter<Target> adapter = new ArrayAdapter<Target>(v.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, targets);
		lvTargets.setAdapter(adapter);
	}
}
