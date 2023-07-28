package com.example.locallibrary1.service;

import com.example.locallibrary1.dto.customer.CustomerResponse;
import com.example.locallibrary1.model.Customer;
import com.example.locallibrary1.repository.CustomerPagingRepository;
import com.example.locallibrary1.repository.CustomerRepository;
import com.example.locallibrary1.repository.EBookRepository;
import com.example.locallibrary1.repository.PaperBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Component
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repo;

    @Autowired
    private CustomerPagingRepository pagingRepo;

    @Autowired
    private EBookRepository eBookRepo;

    @Autowired
    private PaperBookRepository paperBookRepo;


    public Customer save(Customer customer){
        return repo.save(customer);
    }






}
