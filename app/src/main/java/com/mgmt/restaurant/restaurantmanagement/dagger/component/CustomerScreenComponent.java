package com.mgmt.restaurant.restaurantmanagement.dagger.component;

import com.mgmt.restaurant.restaurantmanagement.dagger.module.CustomersScreenModule;
import com.mgmt.restaurant.restaurantmanagement.utils.CustomScope;
import com.mgmt.restaurant.restaurantmanagement.view.CustomersListFragment;

import dagger.Component;

@CustomScope
@Component(dependencies = NetComponent.class, modules = {CustomersScreenModule.class})
public interface CustomerScreenComponent {
    void inject(CustomersListFragment customersListFragment);
}
