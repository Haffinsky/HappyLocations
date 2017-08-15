package com.haffa.happylocations;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.haffa.happylocations.Utilities.PermissionCheck;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionCheck permissionCheck = new PermissionCheck();
        permissionCheck.checkForPermissions(this);
    }
}

