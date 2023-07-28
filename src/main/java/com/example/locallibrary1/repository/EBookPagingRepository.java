package com.example.locallibrary1.repository;

import com.example.locallibrary1.model.EBook;
import com.example.locallibrary1.model.PaperBook;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EBookPagingRepository extends PagingAndSortingRepository<EBook, UUID> {

}