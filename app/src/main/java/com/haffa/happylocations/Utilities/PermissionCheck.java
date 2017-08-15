package com.haffa.happylocations.Utilities;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

/**
 * Created by Rafal on 8/12/2017.
 */

public class PermissionCheck {

    public  static final int PERMISSIONS_MULTIPLE_REQUEST = 123;
    private Activity activity;

    public PermissionCheck(){
    }

    public void checkForPermissions(Activity activity){

        this.activity = activity;
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_COARSE_LOCATION) +
                ContextCompat.checkSelfPermission(activity,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_COARSE_LOCATION) ||
                    (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                            Manifest.permission.ACCESS_FINE_LOCATION)))  {

            } else {

                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_MULTIPLE_REQUEST);


            }
        }
    }
}
