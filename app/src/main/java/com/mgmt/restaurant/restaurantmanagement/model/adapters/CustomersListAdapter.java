package com.mgmt.restaurant.restaurantmanagement.model.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.mgmt.restaurant.restaurantmanagement.R;
import com.mgmt.restaurant.restaurantmanagement.model.CustomerDetails;

import java.util.ArrayList;


public class CustomersListAdapter extends BaseAdapter{

    private ArrayList<CustomerDetails> myList = new ArrayList();
    private LayoutInflater inflater;
    private Context context;
    private CustomerDetails currentListData;
    private MyViewHolder mViewHolder;


    public CustomersListAdapter(Context context, ArrayList<CustomerDetails>  myList) {
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public CustomerDetails getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.customer_details, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        currentListData = getItem(position);

        if(currentListData !=null) {
            mViewHolder.firstName.setText(currentListData.getCustomerFirstName());
            mViewHolder.lastName.setText(currentListData.getCustomerLastName());
            mViewHolder.userId.setText(currentListData.getId().toString());
        }
        return convertView;
    }


    private class MyViewHolder {
        TextView firstName,lastName, userId;
        Button forwardButton;

        public MyViewHolder(View item) {
            firstName = (TextView) item.findViewById(R.id.first_name_text);
            lastName = (TextView) item.findViewById(R.id.last_name_text);
            userId = (TextView) item.findViewById(R.id.user_id);
            forwardButton = (Button) item.findViewById(R.id.fwdBtn);
        }
    }

}