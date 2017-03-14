package com.mgmt.restaurant.restaurantmanagement.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;


public class LocalStoreCustomerDetails {

    static LocalStoreCustomerDetails localStoreCustomerDetails;

    public static LocalStoreCustomerDetails getInstance() {
        if (localStoreCustomerDetails == null) {
            localStoreCustomerDetails = new LocalStoreCustomerDetails();
        }
        return localStoreCustomerDetails;
    }

    //store in shared preference
    public void preserveDetails(Context context, String key, String firstName, String lastName, int position) {

        SharedPreferences sharedpreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        //store the data in shared preference along with current time

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("CustomerFirstName", firstName);
        editor.putString("CustomerLastName", lastName);
        editor.putInt("TableID", position);
        editor.putInt("CurrentTime", Calendar.getInstance().get(Calendar.MINUTE));
        editor.apply();
    }

    //clear the shared preference
    public void clearDetails(Context context, String key)
    {
        SharedPreferences prefs = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }

}
