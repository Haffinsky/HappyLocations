package com.haffa.happylocations.LocationLogic;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.geniusforapp.fancydialog.FancyAlertDialog;
import com.haffa.happylocations.MainActivity;
import com.haffa.happylocations.R;
import com.haffa.happylocations.Utilities.Dialogs;
import com.haffa.happylocations.WelcomeScreenFragment;
import com.mohan.location.locationtrack.LocationProvider;
import com.mohan.location.locationtrack.LocationTrack;
import com.mohan.location.locationtrack.pojo.LocationObj;
import com.mohan.location.locationtrack.providers.FusedLocationProvider;

import java.io.IOException;
import java.util.List;

import static com.haffa.happylocations.Utilities.RetriveMyApplicationContext.getAppContext;
import static java.lang.Math.round;

/**
 * Created by Rafal on 8/13/2017.
 */

public class LocationFetcher {
    private LocationProvider locationProvider;
    private LocationObj locationObject;
    private String displayLongitude;
    private String displayLatitude;
    private String title;
    private String header;
    private String body = "Our spy network found out that this is your current location. " +
            "Do you want to save it to favorites?"; // write to string file
    private String city;
    private int black;
    private String streetName; //lil marco :D
    private String streetNumber;
    Dialogs dialog;
    public LocationFetcher(){}


    public void displayCurrentLocation(Activity activity){

        locationProvider = new FusedLocationProvider(getAppContext());
        locationObject =
                new LocationTrack
                        .Builder(getAppContext())
                        .withProvider(locationProvider)
                        .build()
                        .getLastKnownLocation();

        displayLongitude = String.format("%.2f", locationObject.getLongitude());
        displayLatitude = String.format("%.2f", locationObject.getLatitude());


        List<Address> addresses;
        Geocoder geocoder = new Geocoder(getAppContext());
        try {
            addresses = geocoder
                    .getFromLocation
                            (locationObject.getLatitude(), locationObject.getLongitude(), 1);

            Address address = addresses.get(0);
            streetName = address.getThoroughfare();
            streetNumber = address.getFeatureName();
            city = address.getLocality();
            title = displayLatitude + "/" + displayLongitude;
            header = city + ", " + streetName + " " + streetNumber;

            dialog = new Dialogs();
            dialog.displayDialog(title, header, body, activity);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void startLocationUpdates(){
    }
}
