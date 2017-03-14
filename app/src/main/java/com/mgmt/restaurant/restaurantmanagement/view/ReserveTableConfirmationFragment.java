package com.mgmt.restaurant.restaurantmanagement.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mgmt.restaurant.restaurantmanagement.R;


public class ReserveTableConfirmationFragment extends Fragment{

    private TextView confirmText;
    private String firstName, lastName;


    public ReserveTableConfirmationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.table_reserve_confirm_fragment, container, false);

        //Retrieve the customer data
        firstName = getArguments().getString("FirstName");
        lastName = getArguments().getString("LastName");

        confirmText = (TextView) view.findViewById(R.id.confirm_text);
        confirmText.setText("Table Confirmed for the Customer: "+firstName + ' ' +lastName);

        return view;
    }

}