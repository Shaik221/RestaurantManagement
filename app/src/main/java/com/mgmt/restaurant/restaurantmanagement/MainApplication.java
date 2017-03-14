package com.mgmt.restaurant.restaurantmanagement;

import android.app.Application;

import com.mgmt.restaurant.restaurantmanagement.dagger.component.DaggerNetComponent;
import com.mgmt.restaurant.restaurantmanagement.dagger.component.NetComponent;
import com.mgmt.restaurant.restaurantmanagement.dagger.module.AppModule;
import com.mgmt.restaurant.restaurantmanagement.dagger.module.NetModule;

public class MainApplication extends Application {
    private com.mgmt.restaurant.restaurantmanagement.dagger.component.NetComponent mNetComponent;
    private static MainApplication mInstance;
    public static final String SHARED_PREF_KEY="ReservTableDetais";
    public static final int TIME_INTERVAL = 10;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        //net component building
        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(BuildConfig.SERVER_URL,this))
                .build();
    }

    public static synchronized MainApplication getInstance() {
        return mInstance;
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}
