package com.example.locallibrary1.service;

import com.example.locallibrary1.model.BorrowingHistory;
import com.example.locallibrary1.model.Customer;
import com.example.locallibrary1.repository.BorrowingHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

@Service
public class ReturnNotificationService {

    @Autowired
    private BorrowingHistoryRepository borrowingHistoryRepository;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 0 0 * * *") // Schedule to run daily at midnight
    public void sendOverdueReturnNotifications() {
        LocalDate currentDate = LocalDate.now();
        List<BorrowingHistory> overdueHistories = borrowingHistoryRepository.findOverdueHistories(currentDate);


        for (BorrowingHistory history : overdueHistories) {


            if(history.getPaperBooks() != null && history.getEbooks() != null) {
                long daysOverdue = ChronoUnit.DAYS.between(history.getReturnDate(), currentDate);


                BigDecimal fine = BigDecimal.valueOf(daysOverdue * 5);
                history.setFine(fine);
                Set<Customer> customers = history.getCustomers();
                String confirmationUrl = "http://localhost:8084/library/history/" + history.getId();
                String message = "Your book return is overdue. Please click on the link below to confirm your return:\nThe fine is:" + "$" + history.getFine() + "\n"
                        + confirmationUrl;

                for (Customer customer : customers) {
                    emailService.sendSimpleMessage(customer.getEmail(), "Overdue Return Notification", message);
                }
            }
        }
    }
}
