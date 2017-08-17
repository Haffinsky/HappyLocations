package com.haffa.happylocations.Utilities;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.geniusforapp.fancydialog.FancyAlertDialog;
import com.google.android.gms.maps.model.LatLng;
import com.haffa.happylocations.Data.DatabaseHelper;
import com.haffa.happylocations.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.haffa.happylocations.Data.DatabaseHelper.DISPLAY_TEXT;
import static com.haffa.happylocations.Data.DatabaseHelper.TABLE_NAME;
import static com.haffa.happylocations.Utilities.RetriveMyApplicationContext.getAppContext;

/**
 * Created by Rafal on 8/14/2017.
 */

public class Dialogs {

    Uri BASE_CONTENT_URI = Uri.parse("content://rafal.happylocations/locations");
    ContentResolver resolver = getAppContext().getContentResolver();
    ContentValues values = new ContentValues();
    DatabaseHelper databaseHelper = new DatabaseHelper(getAppContext());
    String displayText;
    ArrayList<String> favoriteStrings = new ArrayList<>();

    public void Dialogs() {
    }

    public void displayDialog(final String title,
                              final String header,
                              String text,
                              final Activity activity,
                              final String longitude,
                              final String latitude) {

        final FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(activity)
                .setImageDrawable(ResourcesCompat.
                        getDrawable(getAppContext().getResources(), R.drawable.globe, null))
                .setTextTitle(title)
                .setTextSubTitle(header)
                .setBody(text)
                .setBodyColor(R.color.darkGrey)
                .setNegativeColor(R.color.colorPrimaryDark)
                .setNegativeButtonText("no, thanks")
                .setPositiveColor(R.color.favoriteColor)
                .setPositiveButtonText("save")
                .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {
                        dialog.dismiss();

                        displayText = title + " " + header;

                        favoriteStrings = databaseHelper.GetAllVLocations(TABLE_NAME, new String[]{DISPLAY_TEXT});

                        if (!favoriteStrings.contains(displayText)) {

                            favoriteStrings.add(displayText);

                            Toast.makeText(getAppContext(),
                                    "saving to favorites...",
                                    Toast.LENGTH_SHORT)
                                    .show();

                            values.put(DISPLAY_TEXT, displayText);
                            values.put(databaseHelper.LONGITUDE, longitude);
                            values.put(databaseHelper.LATITUDE, latitude

                            );
                            resolver.insert(BASE_CONTENT_URI, values);

                        } else {

                            Toast.makeText(getAppContext(),
                                    "location already saved",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                })
                .setOnNegativeClicked(new FancyAlertDialog.OnNegativeClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build();
        alert.show();
    }

}
