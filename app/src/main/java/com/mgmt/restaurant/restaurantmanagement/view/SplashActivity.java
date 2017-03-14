package com.mgmt.restaurant.restaurantmanagement.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.mgmt.restaurant.restaurantmanagement.R;


public class SplashActivity extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    private Handler splashHandler;
    private Runnable splashRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashHandler =  new Handler();
        splashRunnable = new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
            }
        };
        splashHandler.postDelayed(splashRunnable, SPLASH_TIME_OUT);
    }



    @Override
    protected void onPause() {
        super.onPause();
        splashHandler.removeCallbacks(splashRunnable);
    }

    @Override
    protected void onStop() {
       super.onStop();
       finish();
    }



}