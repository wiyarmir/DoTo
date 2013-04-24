package pl.orellana.doto;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GeoTagDialogFragment extends DialogFragment implements
		OnMapClickListener, OnMapLongClickListener {
	private static View view = null;
	private GoogleMap map;
	private Marker lastmarker = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Log.d("onCreateDialog", "start");
		if (view != null) {
			Log.d("onCreateDialog", "view is not null");
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null) {
				Log.d("onCreateDialog", "parent is not null");
				parent.removeView(view);
			}
		}
		try {
			Log.d("onCreateDialog", "inflating");
			view = getActivity().getLayoutInflater().inflate(
					R.layout.fragment_geotag, null);
		} catch (InflateException e) {
			Log.d("onCreateDialog", "already inflated? " + e.getMessage());
			// map is already there, just return view as it is
		}

		if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()) == 0) {
			map = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.mapFragment)).getMap();
			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

			map.setOnMapClickListener(this);
			map.setOnMapLongClickListener(this);

			LocationManager lm = (LocationManager) getActivity()
					.getSystemService(Context.LOCATION_SERVICE);
			Location loc = lm
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

			if (loc != null) {
				LatLng ll = new LatLng(loc.getLatitude(), loc.getLongitude());
				map.addMarker(new MarkerOptions().position(ll).title(
						"You are here"));
				map.animateCamera(CameraUpdateFactory.newLatLngZoom(ll, 15));
			}
		}

		AlertDialog.Builder b = new AlertDialog.Builder(getActivity());

		Log.d("onCreateDialog", "setting view " + view);
		b.setView(view);
		b.setPositiveButton("OK", null).setNegativeButton("Cancel", null);

		return b.create();
	}

	@Override
	public void onMapLongClick(LatLng point) {
	}

	@Override
	public void onMapClick(LatLng point) {
		Log.d("onMapLongClick", "tap point: " + point);
		if (lastmarker != null) {
			lastmarker.remove();
		}
		lastmarker = map.addMarker(new MarkerOptions()
				.position(point)
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
				.title("Geotag location"));

	}
}
