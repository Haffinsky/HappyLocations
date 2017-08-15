package com.haffa.happylocations.LocationLogic;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import com.haffa.happylocations.Utilities.Dialogs;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.haffa.happylocations.Utilities.RetriveMyApplicationContext.getAppContext;
import static java.lang.Math.round;

/**
 * Created by Rafal on 8/13/2017.
 */

public class LocationFetcher {
    private FusedLocationProviderClient mFusedLocationClient;
    private String displayLongitude;
    private String displayLatitude;
    private String title;
    private String header;
    private String body = "Our spy network found out that this is your current location. " +
            "Do you want to save it to favorites?"; // write to string file
    private String city;
    private String streetName; //lil marco :D
    private String streetNumber;
    private String actualLatitude;
    private String actualLongitude;
    Dialogs dialog;

    public LocationFetcher() {
    }


    public void displayCurrentLocation(final Activity activity) {

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getAppContext());

        if (ActivityCompat.checkSelfPermission(getAppContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getAppContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(activity, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    List<Address> addresses;
                    Geocoder geocoder = new Geocoder(getAppContext());
                    try {
                        displayLongitude = String.format("%.2f", location.getLongitude());
                        displayLatitude = String.format("%.2f", location.getLatitude());

                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        Address address = addresses.get(0);
                        streetName = address.getThoroughfare();
                        streetNumber = address.getFeatureName();
                        city = address.getLocality();
                        title = displayLatitude + "/" + displayLongitude;
                        header = city + ", " + streetName + " " + streetNumber;
                        actualLatitude = String.valueOf(location.getLatitude());
                        actualLongitude = String.valueOf(location.getLongitude());

                        dialog = new Dialogs();
                        dialog.displayDialog(title, header, body, activity,
                               actualLongitude, actualLatitude);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getAppContext(), "gps service unavailable", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}