package com.mgmt.restaurant.restaurantmanagement.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.mgmt.restaurant.restaurantmanagement.model.CustomerDetails;

import java.util.ArrayList;


public class LocalStoreCustomerDetails {

    static LocalStoreCustomerDetails localStoreCustomerDetails;

    public static LocalStoreCustomerDetails getInstance(){
        if(localStoreCustomerDetails==null){
            localStoreCustomerDetails = new LocalStoreCustomerDetails();
        }
        return localStoreCustomerDetails;
    }


    public void preserveCustomersList(Context context, String key, ArrayList<CustomerDetails> customerDetailsArrayList) {

        try {
            Gson gson = new Gson();
            String user_json = gson.toJson(customerDetailsArrayList);
            SharedPreferences sharedpreferences = PreferenceManager
                    .getDefaultSharedPreferences(context.getApplicationContext());

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(key, user_json);
            editor.apply();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*public ArrayList<CustomerDetails> getCustomersDetails(Context context){

        SharedPreferences sharedpreferences = context.getSharedPreferences("CustomersList",
                Context.MODE_PRIVATE);
        String customerList = sharedpreferences.getString("CustomersList","");


        if(customerList!=null && customerList.length()>0){
            Gson gson = new Gson();
            ArrayList<CustomerDetails> favoriteItems = gson.fromJson(jsonFavorites,BeanSampleList[].class);
            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList(favorites);,

            Gson gson = new Gson();
            return gson.fromJson(customerList, ArrayList<CustomerDetails.class>);

            UserProfile profile = convertStringToUserProfile(stringUserDetails);
            if(profile!=null){
                return profile;
            }
        }
        IntegrationUtility.Log("Object not available you may have to request UserDetails again returning NULL",context);
        return null;
    }*/
}
