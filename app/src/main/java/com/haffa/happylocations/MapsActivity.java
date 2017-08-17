package com.haffa.happylocations;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
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


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        locations = databaseHelper.GetAllVLocations(TABLE_NAME, new String[]{DISPLAY_TEXT});
        latitudes = databaseHelper.GetAllVLocations(TABLE_NAME, new String[]{LATITUDE});
        longitudes = databaseHelper.GetAllVLocations(TABLE_NAME, new String[]{LONGITUDE});

        Log.v("AYYYY LMAOOO", String.valueOf(locations.size()));

        if (locations.size() != 0 && latitudes.size() != 0 && longitudes.size() != 0){
            for (int i = 0; i < locations.size(); i++){
                double lat = Double.parseDouble(latitudes.get(i));
                double lng = Double.parseDouble(longitudes.get(i));
                LatLng latLng = new LatLng(lat, lng);
                mMap.addMarker(new MarkerOptions().position(latLng).title(locations.get(i)));
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
