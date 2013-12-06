package mobile.mylocationstats;

import java.util.Calendar;

import android.os.Bundle;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TimePicker;

public class TargetFragment extends Fragment {
	
	private Button setTime;
	private Calendar calendar;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_target, container, false);
        initComponents();
        return v;
    }
	
	private void initComponents() {
		setTime = (Button) getView().findViewById(R.id.btnDueDate);
		calendar = Calendar.getInstance();
	}
	
	public void showDateDialog() {
		TimePickerDialog tp1 = new TimePickerDialog(getView().getContext(), new CalendarListener(), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
	    tp1.show();
	}
	
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
	    calendar.set(Calendar.MINUTE, minute);
	    Log.d("MY LOCATION", "Woohoo!");
	}
	
	private class CalendarListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			
		}
		
	}
	

}
