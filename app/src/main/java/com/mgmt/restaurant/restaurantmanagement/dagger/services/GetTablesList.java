package com.mgmt.restaurant.restaurantmanagement.dagger.services;

import java.util.ArrayList;

import retrofit2.http.GET;
import rx.Observable;

public interface GetTablesList {
        @GET("/quandoo-assessment/table-map.json")
        Observable<ArrayList<Boolean>> getAllTableDetails();
}
