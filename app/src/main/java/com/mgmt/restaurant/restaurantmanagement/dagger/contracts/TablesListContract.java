package com.mgmt.restaurant.restaurantmanagement.dagger.contracts;


import com.mgmt.restaurant.restaurantmanagement.model.CustomerDetails;

import java.util.ArrayList;

public interface TablesListContract {
    //get all tables list
    interface View {
        void showAllTables(ArrayList<Boolean> tablesList);

        void showError(String message);

        void showComplete();
    }

    interface Presenter {
        void loadTables();
        void onTableClicked(CustomerDetails customerDetails);
    }

    interface Repository {

        CustomerDetails getCustomerById(long id);
        CustomerDetails getCustomerByFirstName(String name);
        CustomerDetails getCustomerByLastName(String lastName);

    }
}
