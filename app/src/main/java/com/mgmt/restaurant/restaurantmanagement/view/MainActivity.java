package com.mgmt.restaurant.restaurantmanagement.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.mgmt.restaurant.restaurantmanagement.MainApplication;
import com.mgmt.restaurant.restaurantmanagement.R;
import com.mgmt.restaurant.restaurantmanagement.utils.LocalStoreCustomerDetails;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //clear the reservation details for the interval of 10 min
        SharedPreferences preferences = getSharedPreferences(MainApplication.SHARED_PREF_KEY, MODE_PRIVATE);
        int storedTime = preferences.getInt("CurrentTime",0);

        if (preferences != null && storedTime >= MainApplication.TIME_INTERVAL ) {
            LocalStoreCustomerDetails.getInstance().clearDetails(getApplicationContext(),MainApplication.SHARED_PREF_KEY);
        }

        //loading customers list fragment
        CustomersListFragment frag = new CustomersListFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frgament_container,frag,"CustomersListFragment");
        transaction.commit();
    }
}
