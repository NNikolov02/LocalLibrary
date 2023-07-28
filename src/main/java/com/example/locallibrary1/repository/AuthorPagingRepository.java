package com.example.locallibrary1.repository;

import com.example.locallibrary1.model.Author;
import com.example.locallibrary1.model.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthorPagingRepository extends PagingAndSortingRepository<Author, UUID> {

}
