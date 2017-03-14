package com.mgmt.restaurant.restaurantmanagement.dagger.module;

import com.mgmt.restaurant.restaurantmanagement.dagger.contracts.CustomersListContract;
import com.mgmt.restaurant.restaurantmanagement.utils.CustomScope;

import dagger.Module;
import dagger.Provides;

@Module
public class CustomersScreenModule {

    private CustomersListContract.View mView = null;

    public CustomersScreenModule(CustomersListContract.View mView) {
        this.mView = mView;
    }

    @Provides
    @CustomScope
    CustomersListContract.View providesMainScreenContractView() {
        return mView;
    }

}
