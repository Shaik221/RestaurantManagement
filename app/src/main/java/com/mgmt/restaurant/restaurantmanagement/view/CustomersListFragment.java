package com.mgmt.restaurant.restaurantmanagement.view;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mgmt.restaurant.restaurantmanagement.MainApplication;
import com.mgmt.restaurant.restaurantmanagement.R;
import com.mgmt.restaurant.restaurantmanagement.dagger.component.DaggerCustomerScreenComponent;
import com.mgmt.restaurant.restaurantmanagement.dagger.contracts.CustomersListContract;
import com.mgmt.restaurant.restaurantmanagement.dagger.module.CustomersScreenModule;
import com.mgmt.restaurant.restaurantmanagement.model.CustomerDetails;
import com.mgmt.restaurant.restaurantmanagement.db.CustomerDetailsDatabaseHelper;
import com.mgmt.restaurant.restaurantmanagement.model.adapters.CustomersListAdapter;
import com.mgmt.restaurant.restaurantmanagement.presenter.GetCustomersScreenPresenter;
import com.mgmt.restaurant.restaurantmanagement.utils.ConnectivityReceiver;
import com.mgmt.restaurant.restaurantmanagement.utils.UserAlerts;
import com.mgmt.restaurant.restaurantmanagement.view.widgets.TransparentProgressDialog;

import java.util.ArrayList;

import javax.inject.Inject;


public class CustomersListFragment extends Fragment implements View.OnClickListener, CustomersListContract.View {

    private View currentView;
    private View view;
    //logging tag
    private static final String TAG = "CustomersListFragment";
    private CoordinatorLayout coordinatorLayout;

    private ListView listView;
    private CustomersListAdapter adapter;

    //progress dialog
    private TransparentProgressDialog pDialog;
    private SQLiteDatabase db;

    @Inject
    GetCustomersScreenPresenter getCustomersScreenPresenter;

    public CustomersListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //progress dialog instance created
        pDialog = new TransparentProgressDialog(getActivity());

        //retrieving user profile detail
        DaggerCustomerScreenComponent.builder()
                .netComponent(((MainApplication)getActivity().getApplicationContext()).getNetComponent())
                .customersScreenModule(new CustomersScreenModule(this))
                .build().inject(this);

    }

    @Override
    public void showError(String message) {
        pDialog.cancel();
        if(message!=null ){
            UserAlerts.showAlertDialog(getActivity(),message);
        }

    }


    @Override
    public void showComplete() {
        pDialog.cancel();

    }

    @Override
    public void showCustomers(final ArrayList<CustomerDetails> customerDetailsList){

        listView = (ListView) currentView.findViewById(R.id.list);
        if(customerDetailsList.size() > 0) {

            //save data for offline support
            CustomerDetailsDatabaseHelper.getInstance(getActivity()).putCustomers(customerDetailsList);

            //LocalStoreCustomerDetails.getInstance().preserveCustomersList(getActivity(), "CustomersList", customerDetailsList);

            adapter = new CustomersListAdapter(getActivity(), customerDetailsList);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    CustomerDetails currentData = customerDetailsList.get(position);

                        String customerName = currentData.getCustomerFirstName();
                        Toast.makeText(getActivity(), "Selected::"+customerName,Toast.LENGTH_SHORT).show();
                        //loading tables list fragment
                        ReserveTableFragment frag = new ReserveTableFragment();
                        FragmentManager manager = getFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.replace(R.id.frgament_container,frag,"ReserveTableFragment");
                        transaction.addToBackStack(null);

                        //send data to other fragments
                        Bundle args = new Bundle();
                        args.putString("FirstName", currentData.getCustomerFirstName());
                        args.putString("LastName", currentData.getCustomerLastName());
                        frag.setArguments(args);

                        transaction.commit();

                }
            });
        } else {
            Toast.makeText(getActivity(), "Customers List Empty", Toast.LENGTH_SHORT).show();
        }

    }

    public void showCustomersList(final ArrayList<CustomerDetails> customerDetailsList)
    {
        adapter = new CustomersListAdapter(getActivity(), customerDetailsList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                CustomerDetails currentData = customerDetailsList.get(position);

                String customerName = currentData.getCustomerFirstName();
                Toast.makeText(getActivity(), "Selected::"+customerName,Toast.LENGTH_SHORT).show();
                //loading tables list fragment
                ReserveTableFragment frag = new ReserveTableFragment();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.frgament_container,frag,"ReserveTableFragment");
                transaction.addToBackStack(null);

                //send data to other fragments
                Bundle args = new Bundle();
                args.putString("FirstName", currentData.getCustomerFirstName());
                args.putString("LastName", currentData.getCustomerLastName());
                frag.setArguments(args);

                transaction.commit();

            }
        });
    }

    public void loadOfflineCustomersData()
    {
        //get customers list from offline
        if(CustomerDetailsDatabaseHelper.getInstance(getActivity()) != null )
        {
            ArrayList<CustomerDetails> customerList = CustomerDetailsDatabaseHelper.getInstance(getActivity()).getAllCustomers();
            showCustomersList(customerList);
        }
        UserAlerts.showSnackBar(coordinatorLayout,getString(R.string.alert_offline));
    }

    public void getAllCustomers(){
        //check for network availability and call list of cards
        if(ConnectivityReceiver.isConnected()) {
            pDialog.show();
            getCustomersScreenPresenter.loadCustomers();
        } else {
            loadOfflineCustomersData();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.customers_list, container, false);
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id
                .coordinatorLayout);

        listView = (ListView) view.findViewById(R.id.list);
        currentView = view;
        getAllCustomers();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

        }
    }

}