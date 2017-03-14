package com.mgmt.restaurant.restaurantmanagement.dagger.contracts;


import com.mgmt.restaurant.restaurantmanagement.model.CustomerDetails;

import java.util.ArrayList;

public interface CustomersListContract {
    //get list of all customer
    interface View {
        void showCustomers(ArrayList<CustomerDetails> customerDetailsArrayList);

        void showError(String message);

        void showComplete();
    }

    interface Presenter {
        void loadCustomers();
        void onCustomerClicked(CustomerDetails customerDetails);
    }

    interface Repository {

        CustomerDetails getCustomerById(long id);
        CustomerDetails getCustomerByFirstName(String name);
        CustomerDetails getCustomerByLastName(String lastName);

    }
}
