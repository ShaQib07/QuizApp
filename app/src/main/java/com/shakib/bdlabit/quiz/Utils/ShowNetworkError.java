package com.shakib.bdlabit.quiz.Utils;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;

import com.shakib.bdlabit.quiz.R;


/**
 * Created by razon30 on 16-04-19.
 */

public class ShowNetworkError {

    public ShowNetworkError() {
    }

    public void ShowNetworkError(final Activity activity) {

        AlertDialog.Builder builderAlertDialog = new AlertDialog.Builder(activity);

        builderAlertDialog.setTitle("No Internet Connection")
                .setMessage("Turn on WIFI?")
                .setIcon(R.drawable.ic_action_warning)
                .setPositiveButton("Setting", (dialog, which) -> activity.startActivityForResult(new Intent(Settings.ACTION_WIFI_SETTINGS),0))
                .setCancelable(false)
                .show();

    }
}
