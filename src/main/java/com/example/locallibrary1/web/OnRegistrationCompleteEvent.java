package com.example.locallibrary1.web;

import com.example.locallibrary1.dto.borrowinghistory.BorrowResponse;
import com.example.locallibrary1.model.Customer;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;



public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private String appUrl;
    private Locale locale;
    private Customer customer;
    private BorrowResponse borrowResponse;

    public OnRegistrationCompleteEvent(Customer customer, Locale locale, String appUrl) {
        super(customer);
        this.customer = customer;
        this.locale = locale;
        this.appUrl = appUrl;
       // this.borrowResponse = borrowResponse;
    }


    // Getters and setters (if needed)

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}