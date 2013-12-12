package mobile.mylocationstats.ui.listviews;

import java.util.ArrayList;
import mobile.mylocationstats.R;
import mobile.mylocationstats.domain.Target;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TargetsBaseAdapter extends BaseAdapter {
	
	private static ArrayList<Target> targets;
	private LayoutInflater mInflater;

	public TargetsBaseAdapter(Context context, ArrayList<Target> results) {
		targets = results;
		mInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return targets.size();
	}

	public Object getItem(int position) {
		return targets.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.listview_targets, null);
			holder = new ViewHolder();
			holder.txtName = (TextView) convertView.findViewById(R.id.name);
			holder.txtDueDate = (TextView) convertView.findViewById(R.id.due_date);
			holder.txtCoordinates = (TextView) convertView.findViewById(R.id.coordinates);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txtName.setText(targets.get(position).getLocation().getName());
		holder.txtDueDate.setText("Due date: " + targets.get(position).getDueDateFormatted());
		holder.txtCoordinates.setText("Coordinates: " + targets.get(position).getLocation().getCoordinates());

		return convertView;
	}

	static class ViewHolder {
		TextView txtName;
		TextView txtDueDate;
		TextView txtCoordinates;
	}
}