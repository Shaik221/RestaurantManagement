package com.mgmt.restaurant.restaurantmanagement.dagger.component;

import com.mgmt.restaurant.restaurantmanagement.dagger.module.TablesScreenModule;
import com.mgmt.restaurant.restaurantmanagement.utils.CustomScope;
import com.mgmt.restaurant.restaurantmanagement.view.ReserveTableFragment;

import dagger.Component;

@CustomScope
@Component(dependencies = NetComponent.class, modules = {TablesScreenModule.class})
public interface TablesScreenComponent {
    void inject(ReserveTableFragment tablesList);
}
