package com.example.locallibrary1.repository;

import com.example.locallibrary1.model.BorrowingHistory;
import com.example.locallibrary1.model.Customer;
import com.example.locallibrary1.model.EBook;
import com.example.locallibrary1.model.PaperBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BorrowingHistoryRepository extends CrudRepository<BorrowingHistory, UUID> {
    BorrowingHistory findByCustomerAndEBookAndReturnDateIsNull(Customer customer, EBook eBook);
    //BorrowingHistory findByCustomerAndPaperBookAndReturnDateIsNull(Customer customer, PaperBook paperBook);
}