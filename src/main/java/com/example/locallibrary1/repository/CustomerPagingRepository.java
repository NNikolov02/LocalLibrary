package com.example.locallibrary1.repository;


import com.example.locallibrary1.model.Customer;
import com.example.locallibrary1.model.EBook;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerPagingRepository extends PagingAndSortingRepository<Customer, UUID> {

}
