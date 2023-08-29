package com.example.locallibrary1.web;

import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class OnRegistrationCompleteEventHistory extends ApplicationEvent {

    private String appUrl;
    private Locale locale;

    private String customer;

    public OnRegistrationCompleteEventHistory(String customer, Locale locale, String appUrl) {
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

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
}

