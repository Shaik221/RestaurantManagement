package com.mgmt.restaurant.restaurantmanagement.view;

import com.mgmt.restaurant.restaurantmanagement.model.CustomerDetails;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;


@RunWith(MockitoJUnitRunner.class)
public class CustomersListFragmentTest {

    private CustomerDetails customerDetails;
    private ArrayList<CustomerDetails> customerDetailsArrayList;
    private static final String TEST_FIRSTNAME = "Jani";
    private static final String TEST_LASTNAME = "Basha";
    private static final Integer TEST_ID = 007;


    @Before
    public void initMocks() {
        // Create new Customer to persist.
        customerDetails = new CustomerDetails(TEST_FIRSTNAME, TEST_LASTNAME,
                TEST_ID);

    }


    @Test
    public void customerListFragmetHelper_SaveAndReadInformation() {
        // Save the personal information to SharedPreferences
        String name = customerDetails.getCustomerFirstName();
        String lastName = customerDetails.getCustomerLastName();
        Integer id = customerDetails.getId();

        assertNotNull(name);
        assertNotNull(lastName);
        assertNotNull(id);
        assertEquals(name, TEST_FIRSTNAME);
        assertEquals(lastName, TEST_LASTNAME);
        assertEquals(id,TEST_ID);

        assertTrue(true);

    }

}