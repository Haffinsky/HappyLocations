package com.haffa.happylocations.Utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.geniusforapp.fancydialog.FancyAlertDialog;
import com.haffa.happylocations.Data.DatabaseHelper;
import com.haffa.happylocations.R;

import java.util.ArrayList;

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
    AlertDialog.Builder alertBuilder;
    final EditText edittext = new EditText(getAppContext());
    String userInput;
    public void Dialogs() {}

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


                        displayText = title + " " + header;

                        favoriteStrings = databaseHelper.GetAllEntries(TABLE_NAME, new String[]{DISPLAY_TEXT});

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
    public void showMaterialDialog(final String title,
                                   final String header,
                                   String text,
                                   final Activity activity,
                                   final String longitude,
                                   final String latitude){
        new MaterialDialog.Builder(activity).title(title + " " + header)
                .content(text)
                .positiveText("Save")
                .negativeText("No thanks")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("add a note", null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        userInput = String.valueOf(input);
                        Log.v("Input", userInput);
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        String i = dialog.getInputEditText().getText().toString();

                        displayText = title + " " + header;
                        favoriteStrings = databaseHelper.GetAllEntries(TABLE_NAME, new String[]{DISPLAY_TEXT});

                        if (!favoriteStrings.contains(displayText)) {

                            if (!dialog.getInputEditText().getText().toString().isEmpty() && dialog.getInputEditText().getText().toString() != null){
                                displayText =  title + " " + header + " (" + dialog.getInputEditText().getText() + ")";
                            } else {
                                displayText = title + " " + header;
                            }

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
                            dialog.dismiss();
                        } else {

                            Toast.makeText(getAppContext(),
                                    "location already saved",
                                    Toast.LENGTH_SHORT)
                                    .show();
                            dialog.dismiss();
                        }
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}

