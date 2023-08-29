package com.example.locallibrary1.registration;

import com.example.locallibrary1.model.BorrowingHistory;
import com.example.locallibrary1.model.Customer;
import com.example.locallibrary1.service.BorrowingHistoryService;
import com.example.locallibrary1.service.CustomerService;
import com.example.locallibrary1.service.EmailService;
import com.example.locallibrary1.web.OnRegistrationCompleteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private final CustomerService service;
    private final EmailService emailService;


    @Autowired
    public RegistrationListener(CustomerService service, EmailService emailService) {
        this.service = service;
        this.emailService = emailService;

    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
        this.confirmBorrowing(event);
        this.confirmReturn(event);
        this.overdueReturn(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        Customer customer = event.getCustomer();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(customer, token);

        String recipientAddress = customer.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl = event.getAppUrl() + "/confirmRegistration?token=" + token;
        String message = "Thank you for registering. Please click on the link below to verify your email address:\n"
                + confirmationUrl;

        emailService.sendSimpleMessage(recipientAddress, subject, message);
    }
    private void confirmBorrowing(OnRegistrationCompleteEvent event) {
        Customer customer = event.getCustomer();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(customer, token);

        String recipientAddress = customer.getEmail();
        String subject = "Borrowing Confirmation";
        String confirmationUrl = event.getAppUrl();
        String message = "Thank you for borrowing. Please click on the link below to see your borrow:\n"
                + confirmationUrl;

        emailService.sendSimpleMessage(recipientAddress, subject, message);
    }
    private void confirmReturn(OnRegistrationCompleteEvent event) {
        Customer customer = event.getCustomer();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(customer, token);

        String recipientAddress = customer.getEmail();
        String subject = "Return Confirmation";
        String confirmationUrl = event.getAppUrl();
        String message = "Thank you for returning. Please click on the link below to see your return:\n"
                + confirmationUrl;

        emailService.sendSimpleMessage(recipientAddress, subject, message);
    }
    private void overdueReturn(OnRegistrationCompleteEvent event) {
        Customer customer = event.getCustomer();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(customer, token);

        String recipientAddress = customer.getEmail();
        String subject = "Overdue Return Notification";
        String confirmationUrl = event.getAppUrl();
        String message = "Your book return is overdue. Please click on the link below to confirm your return:\n"
                + confirmationUrl;

        emailService.sendSimpleMessage(recipientAddress, subject, message);
    }
}

