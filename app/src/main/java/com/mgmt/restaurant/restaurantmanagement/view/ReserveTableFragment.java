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
import com.mgmt.restaurant.restaurantmanagement.utils.LocalStoreCustomerDetails;
import com.mgmt.restaurant.restaurantmanagement.utils.UserAlerts;
import com.mgmt.restaurant.restaurantmanagement.view.widgets.TransparentProgressDialog;

import java.util.ArrayList;

import javax.inject.Inject;

public class ReserveTableFragment extends Fragment implements TablesListContract.View  {

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

        //api to get all tables from service
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


        //save data for offline support
        CustomerDetailsDatabaseHelper.getInstance(getContext()).clearTables();
        CustomerDetailsDatabaseHelper.getInstance(getActivity()).putTables(tablesDetails);

        //tablesDetails = new ArrayList<TablesDetails>();
        tablesDetails = CustomerDetailsDatabaseHelper.getInstance(getActivity()).getAllTablesFromDB();
        showTableDataM();

    }


    private void showTableDataM() {


        gridView.setAdapter(new CustomTableAdapter(getContext(), tablesDetails));

        gridView.invalidateViews();

        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if(tablesDetails.get(position).isAvailable().equals("true"))
                {
                    tablesDetails.get(position).setAvailable("false");
                    CustomerDetailsDatabaseHelper.getInstance(getActivity()).updateTable(tablesDetails.get(position));
                    //tablesDetails = new ArrayList<TablesDetails>();
                    tablesDetails = CustomerDetailsDatabaseHelper.getInstance(getContext()).getAllTablesFromDB();

                    //shared prefernce store
                    LocalStoreCustomerDetails.getInstance().preserveDetails(getContext(),
                            MainApplication.SHARED_PREF_KEY,firstName,lastName,position);

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
        //check data in db if selections not exists then get the list from service
        tablesDetails = CustomerDetailsDatabaseHelper.getInstance(getActivity()).getAllTablesFromDB();
        if(tablesDetails.size() > 0)
        {
            showTableDataM();
        } else {
            //show tables data from service
            showTablesData(tablesList);
        }

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
            //tablesDetails = new ArrayList<TablesDetails>();
            tablesDetails = CustomerDetailsDatabaseHelper.getInstance(getActivity()).getAllTablesFromDB();
            showTableDataM();
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
        if(gridView!=null && tablesDetails!=null) {
            gridView.setAdapter(new CustomTableAdapter(getContext(), tablesDetails));
            gridView.invalidateViews();
        }
    }

}