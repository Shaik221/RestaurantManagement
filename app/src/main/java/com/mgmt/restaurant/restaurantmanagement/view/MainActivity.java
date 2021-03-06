package com.mgmt.restaurant.restaurantmanagement.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.mgmt.restaurant.restaurantmanagement.MainApplication;
import com.mgmt.restaurant.restaurantmanagement.R;
import com.mgmt.restaurant.restaurantmanagement.db.CustomerDetailsDatabaseHelper;
import com.mgmt.restaurant.restaurantmanagement.utils.LocalStoreCustomerDetails;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //clear the reservation details for the interval of 10 min
        SharedPreferences preferences = getSharedPreferences(MainApplication.SHARED_PREF_KEY, MODE_PRIVATE);
        int storedTime = preferences.getInt("CurrentTime", 0);
        int currentTime = Calendar.getInstance().get(Calendar.MINUTE);
        int timeInterval = currentTime-storedTime;

        if (preferences != null && timeInterval >= MainApplication.TIME_INTERVAL) {
            LocalStoreCustomerDetails.getInstance().clearDetails(getApplicationContext(), MainApplication.SHARED_PREF_KEY);
            CustomerDetailsDatabaseHelper.getInstance(getApplicationContext()).clearTables();
        }


        //loading customers list fragment
        CustomersListFragment frag = new CustomersListFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frgament_container, frag, "CustomersListFragment");
        transaction.commit();

    }
}
