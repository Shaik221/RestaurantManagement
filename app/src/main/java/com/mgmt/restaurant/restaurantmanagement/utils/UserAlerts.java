package com.mgmt.restaurant.restaurantmanagement.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mgmt.restaurant.restaurantmanagement.R;


public class UserAlerts {

    public static String TAG = "UserAlerts";
    private static AlertDialog alertDialog = null;


    public static void showAlertDialog(Context context, String message){

        if (message != null) {

            if (alertDialog == null || (!alertDialog.isShowing())) {

                alertDialog = new AlertDialog.Builder(
                        context).create();

                // Setting Dialog Title
                alertDialog.setTitle(context.getString(R.string.app_name));

                alertDialog.setCancelable(false);

                // Setting Dialog Message
                alertDialog.setMessage(message);

                // Setting Icon to Dialog
                alertDialog.setIcon(R.drawable.alert_icon);

                // Setting OK Button
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.alert_ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.cancel();

                    }
                });

                // Showing Alert Message
                alertDialog.show();
            }
        } else {
            Log.d(TAG, "Error message was NULL:\n");
        }

    }

    public static void showSnackBar(CoordinatorLayout coordinatorLayout, String message){
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

}
