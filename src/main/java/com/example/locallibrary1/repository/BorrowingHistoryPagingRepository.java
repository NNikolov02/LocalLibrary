package com.example.locallibrary1.repository;

import com.example.locallibrary1.model.BorrowingHistory;
import com.example.locallibrary1.model.PaperBook;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BorrowingHistoryPagingRepository extends PagingAndSortingRepository<BorrowingHistory, UUID> {

}
