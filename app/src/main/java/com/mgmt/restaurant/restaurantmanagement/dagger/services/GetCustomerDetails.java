package com.mgmt.restaurant.restaurantmanagement.dagger.services;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import rx.Observable;

public interface GetCustomerDetails {
        @GET("/quandoo-assessment/customer-list.json")
        Observable<ResponseBody> getAllCustomersDetails();
        /*Call<ArrayList<CustomerDetails>> getAllCustomersDetails();*/
}
