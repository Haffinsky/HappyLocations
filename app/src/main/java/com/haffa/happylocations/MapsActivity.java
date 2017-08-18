package com.haffa.happylocations;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.haffa.happylocations.Data.DatabaseHelper;

import java.util.ArrayList;

import static com.haffa.happylocations.Data.DatabaseHelper.DISPLAY_TEXT;
import static com.haffa.happylocations.Data.DatabaseHelper.LATITUDE;
import static com.haffa.happylocations.Data.DatabaseHelper.LONGITUDE;
import static com.haffa.happylocations.Data.DatabaseHelper.TABLE_NAME;
import static com.haffa.happylocations.Utilities.RetriveMyApplicationContext.getAppContext;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<String> locations = new ArrayList<>();
    ArrayList<String> latitudes = new ArrayList<>();
    ArrayList<String> longitudes = new ArrayList<>();
    DatabaseHelper databaseHelper = new DatabaseHelper(getAppContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        locations = databaseHelper.GetAllEntries(TABLE_NAME, new String[]{DISPLAY_TEXT});
        latitudes = databaseHelper.GetAllEntries(TABLE_NAME, new String[]{LATITUDE});
        longitudes = databaseHelper.GetAllEntries(TABLE_NAME, new String[]{LONGITUDE});

        if (locations.size() != 0 && latitudes.size() != 0 && longitudes.size() != 0) {
            for (int i = 0; i < locations.size(); i++) {
                double lat = Double.parseDouble(latitudes.get(i));
                double lng = Double.parseDouble(longitudes.get(i));
                LatLng latLng = new LatLng(lat, lng);
                mMap.addMarker(new MarkerOptions().position(latLng).title(locations.get(i)));
                mMap.setMinZoomPreference(9);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        } else {
            Toast.makeText(this, "no favorite locations available - showing Sydney instead", Toast.LENGTH_LONG).show();
            LatLng sydney = new LatLng(-34, 151);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    }
}
