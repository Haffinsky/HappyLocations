package com.haffa.happylocations;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.akhgupta.easylocation.EasyLocationAppCompatActivity;
import com.akhgupta.easylocation.EasyLocationRequest;
import com.akhgupta.easylocation.EasyLocationRequestBuilder;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.haffa.happylocations.LocationLogic.LocationFetcher;
import com.haffa.happylocations.Utilities.PermissionCheck;

public class MainActivity extends EasyLocationAppCompatActivity {
    LocationFetcher locationFetcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionCheck permissionCheck = new PermissionCheck();
        permissionCheck.checkForPermissions(this);

        locationFetcher = new LocationFetcher();

        LocationRequest locationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                .setInterval(5000)
                .setFastestInterval(5000);

        EasyLocationRequest easyLocationRequest = new EasyLocationRequestBuilder()
                .setLocationRequest(locationRequest)
                .setFallBackToLastLocationTime(3000)
                .build();

        requestLocationUpdates(easyLocationRequest);
    }

    @Override
    public void onLocationPermissionGranted() {}

    @Override
    public void onLocationPermissionDenied() {}

    @Override
    public void onLocationReceived(Location location) {}

    @Override
    public void onLocationProviderEnabled() {}

    @Override
    public void onLocationProviderDisabled() {}
}

