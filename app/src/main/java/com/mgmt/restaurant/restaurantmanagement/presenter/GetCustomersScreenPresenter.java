package com.mgmt.restaurant.restaurantmanagement.presenter;

import android.util.Log;

import com.mgmt.restaurant.restaurantmanagement.dagger.contracts.CustomersListContract;
import com.mgmt.restaurant.restaurantmanagement.dagger.services.GetCustomerDetails;
import com.mgmt.restaurant.restaurantmanagement.model.CustomerDetails;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GetCustomersScreenPresenter implements CustomersListContract.Presenter {

    Retrofit retrofit;
    CustomersListContract.View mView;
    ArrayList<CustomerDetails> itemsList =  new ArrayList<>();

    @Inject
    public GetCustomersScreenPresenter(Retrofit retrofit, CustomersListContract.View mView) {
        this.retrofit = retrofit;
        this.mView = mView;
    }

    @Override
    public void loadCustomers() {
       retrofit.create(GetCustomerDetails.class).getAllCustomersDetails().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ResponseBody>() {
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
                    public void onNext(ResponseBody responseBody) {

                        try {
                            //String jsonStr = responseBody.toString();
                            //String firstName="",lastName="";
                            //int userID=-1;
                            JSONArray jsonArray = new JSONArray(responseBody.string());

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonobject = jsonArray.getJSONObject(i);
                                Integer id = jsonobject.getInt("id");
                                String firstName = jsonobject.getString("customerFirstName");
                                String lastName = jsonobject.getString("customerLastName");
                                itemsList.add(new CustomerDetails(firstName, lastName, id));
                            }

                            mView.showCustomers(itemsList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onCustomerClicked(CustomerDetails customerDetails)
    {
        Log.d("Presenter","Clicked customer name is::"+customerDetails.getCustomerFirstName());
    }
}