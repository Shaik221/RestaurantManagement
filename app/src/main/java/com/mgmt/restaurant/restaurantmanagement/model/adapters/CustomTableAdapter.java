package com.mgmt.restaurant.restaurantmanagement.model.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mgmt.restaurant.restaurantmanagement.R;
import com.mgmt.restaurant.restaurantmanagement.model.TablesDetails;

import java.util.ArrayList;


public class CustomTableAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<TablesDetails> myList = new ArrayList();
	private CustomTableAdapter.MyViewHolder mViewHolder;
	private Boolean currentTableStatus;
	private LayoutInflater inflater;

	public CustomTableAdapter(Context context, ArrayList<TablesDetails> myList) {
		this.context = context;
		this.myList = myList;
		inflater = LayoutInflater.from(this.context);
	}


	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.table_status_layout, parent, false);
			mViewHolder = new CustomTableAdapter.MyViewHolder(convertView);
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (CustomTableAdapter.MyViewHolder) convertView.getTag();
		}

		currentTableStatus = myList.get(position).isAvailable().equals("true");

		if(currentTableStatus) {
			mViewHolder.tableIcon.setImageResource(R.drawable.green_table_icon);
		} else {
			mViewHolder.tableIcon.setImageResource(R.drawable.yellow_table_icon);
		}
		return convertView;
	}

	private class MyViewHolder {
		TextView tableSelected;
		ImageView tableIcon;

		public MyViewHolder(View item) {
			//tableSelected = (TextView) item.findViewById(R.id.grid_item_label);
			tableIcon = (ImageView) item.findViewById(R.id.grid_item_image);
		}
	}

	@Override
	public int getCount() {
		return myList.size();
	}

	@Override
	public Boolean getItem(int position) {
		return true;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

}