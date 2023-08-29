package com.example.locallibrary1.repository;

import com.example.locallibrary1.model.BorrowingHistory;
import com.example.locallibrary1.model.Customer;
import com.example.locallibrary1.model.EBook;
import com.example.locallibrary1.model.PaperBook;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface BorrowingHistoryRepository extends CrudRepository<BorrowingHistory, UUID> {
  //  BorrowingHistory findByCustomerAndEBookAndReturnDateIsNull(Customer customer, EBook eBook);
    //BorrowingHistory findByCustomerAndPaperBookAndReturnDateIsNull(Customer customer, PaperBook paperBook);
  @Query("SELECT h FROM BorrowingHistory h WHERE h.returnDate <= :currentDate")
  List<BorrowingHistory> findOverdueHistories(@Param("currentDate") LocalDate currentDate);
}
