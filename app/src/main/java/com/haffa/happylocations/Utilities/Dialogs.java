package com.haffa.happylocations.Utilities;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.Toast;

import com.geniusforapp.fancydialog.FancyAlertDialog;
import com.haffa.happylocations.R;

import static com.haffa.happylocations.Utilities.RetriveMyApplicationContext.getAppContext;

/**
 * Created by Rafal on 8/14/2017.
 */

public class Dialogs {

    public void Dialogs(){}

    public void displayDialog(String title, String header, String text, final Activity activity){
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
                        Toast.makeText(getAppContext(),
                                "saving to favorites...",
                                Toast.LENGTH_SHORT)
                                .show();
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
