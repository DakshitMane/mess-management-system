package com.example.homepage;

import java.io.Serializable;

public class Customer implements Serializable {
    // Your existing code for Customer class

    private String id;
    private String name;
    private String phoneNumber;
    private String address;
    private String state;
    private String pincode;
    private String email;
    private double accountBalance;

    private double totalBreakfastAmount;
    private double attendanceBill;// Add the totalBreakfastAmount field

    public Customer() {
        // Default constructor required for Firestore
    }

    // Getters and setters for all fields

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getTotalBreakfastAmount() {
        return totalBreakfastAmount;
    }

    public void setTotalBreakfastAmount(double totalBreakfastAmount) {
        this.totalBreakfastAmount = totalBreakfastAmount;
    }


        public double getAccountBalance() {
            return accountBalance;
        }

        public void setAccountBalance(double accountBalance) {
            this.accountBalance = accountBalance;
        }

    }

