package mobile.mylocationstats.ui.listviews;

import java.util.ArrayList;
import mobile.mylocationstats.R;
import mobile.mylocationstats.domain.Location;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StatisticsBaseAdapter extends BaseAdapter {
	
	private static ArrayList<Location> searchArrayList;
	private LayoutInflater mInflater;

	public StatisticsBaseAdapter(Context context, ArrayList<Location> results) {
		searchArrayList = results;
		mInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return searchArrayList.size();
	}

	public Object getItem(int position) {
		return searchArrayList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.listview_statistics, null);
			holder = new ViewHolder();
			holder.txtName = (TextView) convertView.findViewById(R.id.name);
			holder.txtLastVisited = (TextView) convertView.findViewById(R.id.last_visited);
			holder.txtTotalVisited = (TextView) convertView.findViewById(R.id.total_visited);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txtName.setText(searchArrayList.get(position).getName());
		holder.txtLastVisited.setText("Last visited on: " + searchArrayList.get(position).getLastVisitedFormatted());
		holder.txtTotalVisited.setText("Amount visited: " + searchArrayList.get(position).getTotalVisited() + " ");

		return convertView;
	}

	static class ViewHolder {
		TextView txtName;
		TextView txtLastVisited;
		TextView txtTotalVisited;
	}
}