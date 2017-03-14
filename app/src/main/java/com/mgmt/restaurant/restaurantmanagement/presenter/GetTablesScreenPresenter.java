package com.mgmt.restaurant.restaurantmanagement.presenter;

import android.util.Log;

import com.mgmt.restaurant.restaurantmanagement.dagger.contracts.TablesListContract;
import com.mgmt.restaurant.restaurantmanagement.dagger.services.GetTablesList;
import com.mgmt.restaurant.restaurantmanagement.model.CustomerDetails;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GetTablesScreenPresenter implements TablesListContract.Presenter {

    Retrofit retrofit;
    TablesListContract.View mView;

    @Inject
    public GetTablesScreenPresenter(Retrofit retrofit, TablesListContract.View mView) {
        this.retrofit = retrofit;
        this.mView = mView;
    }

    @Override
    public void loadTables() {
       retrofit.create(GetTablesList.class).getAllTableDetails().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ArrayList<Boolean>>() {
                    @Override
                    public void onCompleted() {
                        mView.showComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        try {
                            mView.showError(e.getMessage());
                        } catch (Throwable et) {
                            et.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(ArrayList<Boolean> responseBody) {

                        mView.showAllTables(responseBody);
                    }

                });
    }

    @Override
    public void onTableClicked(CustomerDetails customerDetails)
    {
        Log.d("Presenter","Clicked customer name is::"+customerDetails.getCustomerFirstName());
    }
}