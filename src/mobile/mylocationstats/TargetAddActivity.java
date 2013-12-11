package mobile.mylocationstats;

import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import mobile.mylocationstats.domain.Facade;
import mobile.mylocationstats.domain.Target;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class TargetAddActivity extends Activity implements OnDateSetListener, OnMapLongClickListener {

	private GoogleMap gMap;
	private Calendar calendar;
	private Calendar dueDate;
	private Marker marker;
	private Facade facade;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_target);
		initComponents();
	}

	private void initComponents() {
		facade = new Facade();
		calendar = Calendar.getInstance();
		dueDate = Calendar.getInstance();
		gMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.targetMap)).getMap();
		gMap.setOnMapLongClickListener(this);
		getLocation();
	}

	public void showDateDialog(View v) {
		DatePickerDialog tp1 = new DatePickerDialog(this, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		tp1.show();
	}

	public void save(View v) {
		mobile.mylocationstats.domain.Location loc = new mobile.mylocationstats.domain.Location(marker.getPosition().latitude, marker.getPosition().longitude);
		
		Geocoder gc = new Geocoder(this, Locale.getDefault());
		try {
			Address a = gc.getFromLocation(loc.getX(), loc.getY(), 1).get(0);
			loc.setName(a.getThoroughfare());
		} catch (IOException e) {
			Log.e("MY LOCATION", e.getMessage());
		}
		
		Target target = new Target(loc, dueDate);
		facade.addTarget(target);
		finish();
	}

	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
		calendar.set(Calendar.MINUTE, minute);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		dueDate.set(year, monthOfYear, dayOfMonth);
	}
	
	private void getLocation() {
		LocationManager locManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		updateLocation(locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
	}
	
	public void updateLocation(Location location) {
		if (gMap != null) {
			LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
			marker = gMap.addMarker(new MarkerOptions().position(currentPosition).title("New Target"));
			gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 15));
		}
	}

	@Override
	public void onMapLongClick(LatLng point) {
		marker.setPosition(point);
	}

}
