package com.mgmt.restaurant.restaurantmanagement.view;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.mgmt.restaurant.restaurantmanagement.MainApplication;
import com.mgmt.restaurant.restaurantmanagement.R;
import com.mgmt.restaurant.restaurantmanagement.dagger.component.DaggerTablesScreenComponent;
import com.mgmt.restaurant.restaurantmanagement.dagger.contracts.TablesListContract;
import com.mgmt.restaurant.restaurantmanagement.dagger.module.TablesScreenModule;
import com.mgmt.restaurant.restaurantmanagement.db.CustomerDetailsDatabaseHelper;
import com.mgmt.restaurant.restaurantmanagement.model.TablesDetails;
import com.mgmt.restaurant.restaurantmanagement.model.adapters.CustomTableAdapter;
import com.mgmt.restaurant.restaurantmanagement.presenter.GetTablesScreenPresenter;
import com.mgmt.restaurant.restaurantmanagement.utils.ConnectivityReceiver;
import com.mgmt.restaurant.restaurantmanagement.utils.UserAlerts;
import com.mgmt.restaurant.restaurantmanagement.view.widgets.TransparentProgressDialog;

import java.util.ArrayList;

import javax.inject.Inject;

public class ReserveTableFragment extends Fragment implements View.OnClickListener,TablesListContract.View  {

    private View currentView;
    private View view;
    private String firstName,lastName;
    private CoordinatorLayout coordinatorLayout;
    private ArrayList<TablesDetails> tablesDetails;

    GridView gridView;
    //progress dialog
    private TransparentProgressDialog pDialog;

    @Inject
    GetTablesScreenPresenter getTablesScreenPresenter;

    public ReserveTableFragment()
    {//default constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //progress dialog instance created
        pDialog = new TransparentProgressDialog(getActivity());

        //retrieving user profile detail
        DaggerTablesScreenComponent.builder()
                .netComponent(((MainApplication)getActivity().getApplicationContext()).getNetComponent())
                .tablesScreenModule(new TablesScreenModule(this))
                .build().inject(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.grid_tables_layout, container, false);
        coordinatorLayout = (CoordinatorLayout) view.findViewById( R.id.coordinatorLayout);

        gridView = (GridView) view.findViewById(R.id.gridView);
        currentView = view;

        //Retrieve the customer data
        firstName = getArguments().getString("FirstName");
        lastName = getArguments().getString("LastName");

        getAllTables();
        return view;
    }

    public void showTablesData(final ArrayList<Boolean> tablesList)
    {
        gridView.setDrawSelectorOnTop(false);

        //storing in table details bean object
        if(tablesList.size() > 0)
        {
            tablesDetails = new ArrayList<TablesDetails>();
            for (int i = 0; i < tablesList.size(); i++)
            {
                TablesDetails obj = new TablesDetails(i+1,tablesList.get(i).toString());
                tablesDetails.add(obj);
            }
        }

        showTableDataM(tablesDetails);

    }

    private void showTableDataM(final ArrayList<TablesDetails> tablesDetails) {

        gridView.setAdapter(new CustomTableAdapter(getContext(), tablesDetails));

        //save data for offline support
        CustomerDetailsDatabaseHelper.getInstance(getActivity()).putTables(tablesDetails);

        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if(tablesDetails.get(position).isAvailable().equals("true"))
                {
                    //loading confirmation  fragment
                    ReserveTableConfirmationFragment frag = new ReserveTableConfirmationFragment();
                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.frgament_container,frag,"ReserveTableConfirmationFragment");
                    transaction.addToBackStack(null);

                    //send data to other fragments
                    Bundle args = new Bundle();
                    args.putString("FirstName", firstName);
                    args.putString("LastName", lastName);
                    frag.setArguments(args);

                    transaction.commit();
                } else{
                    Toast.makeText(getContext(), "Table not Available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void showAllTables(final ArrayList<Boolean> tablesList)
    {
        //show tables data
        showTablesData(tablesList);
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

    public void loadOfflineTablesData()
    {
        //get customers list from offline
        if(CustomerDetailsDatabaseHelper.getInstance(getActivity()) != null )
        {
            ArrayList<TablesDetails> tablesList = CustomerDetailsDatabaseHelper.getInstance(getActivity()).getAllTables();
            showTableDataM(tablesList);
        }
        UserAlerts.showSnackBar(coordinatorLayout,getString(R.string.alert_offline));
    }


    public void getAllTables()
    {
        //check for network availability and call list of cards
        if(ConnectivityReceiver.isConnected()) {
            pDialog.show();
            getTablesScreenPresenter.loadTables();
        } else {
            loadOfflineTablesData();
        }



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