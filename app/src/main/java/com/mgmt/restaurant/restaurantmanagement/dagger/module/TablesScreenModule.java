package com.mgmt.restaurant.restaurantmanagement.dagger.module;

import com.mgmt.restaurant.restaurantmanagement.dagger.contracts.TablesListContract;
import com.mgmt.restaurant.restaurantmanagement.utils.CustomScope;

import dagger.Module;
import dagger.Provides;

@Module
public class TablesScreenModule {

    private TablesListContract.View mView = null;

    public TablesScreenModule(TablesListContract.View mView) {
        this.mView = mView;
    }

    @Provides
    @CustomScope
    TablesListContract.View providesMainScreenContractView() {
        return mView;
    }

}
