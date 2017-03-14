package com.mgmt.restaurant.restaurantmanagement.model;

public class TablesDetails {

    private int tableNo;

    private String isAvailable;


    //constructor
    public TablesDetails(int tableID, String tableAvilablity) {
        tableNo = tableID;
        isAvailable = tableAvilablity;
    }

    public int getTableNo() {
        return tableNo;
    }

    public void setTableNo(int tableNo) {
        this.tableNo = tableNo;
    }

    public String isAvailable() {
        return isAvailable;
    }

    public void setAvailable(String available) {
        isAvailable = available;
    }




}