package com.example.locallibrary1.service;

import com.example.locallibrary1.dto.customer.CustomerResponse;
import com.example.locallibrary1.error.NotFoundObjectException;
import com.example.locallibrary1.model.Customer;
import com.example.locallibrary1.model.EBook;
import com.example.locallibrary1.model.PaperBook;
import com.example.locallibrary1.repository.CustomerPagingRepository;
import com.example.locallibrary1.repository.CustomerRepository;
import com.example.locallibrary1.repository.EBookRepository;
import com.example.locallibrary1.repository.PaperBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

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


    public Customer findCustomer(String email){
        return repo.findCustomerByEmail(email);
    }
    public Page<Customer> fetchAll(int currentPage, int pageSize) {
        return pagingRepo.findAll(PageRequest.of(currentPage, pageSize));
    }

    public Customer findById(String customerId) {
        return repo.findById(UUID.fromString(customerId)).orElseThrow(() -> {
            throw new NotFoundObjectException("Customer Not Found", EBook.class.getName(), customerId);
        });
    }
    public void deleteByEmail(String email){
        repo.deleteAllByEmail(email);
    }
    public Set<UUID> setCustomersEbooks(String customerId, Set<UUID> customerEbookIds) {
        Customer customer = repo.findById(UUID.fromString(customerId)).orElseThrow(() -> {
            throw new NotFoundObjectException("Customer Not Found", Customer.class.getName(), customerId);
    });

        List<EBook> allAuthorEbooks =
                (List<EBook>) eBookRepo.findAllById(customerEbookIds);

        customer.setEBooks(new HashSet<>(allAuthorEbooks));
        Customer savedCustomer = repo.save(customer);

        Set<UUID> allEbookCustomerIds = new HashSet<>();
        for (EBook eBook : savedCustomer.getEBooks()) {
            allEbookCustomerIds.add(eBook.getId());
        }

        return allEbookCustomerIds;
    }

    public Set<UUID> setCustomersPaperBooks(String customerId, Set<UUID> customerPaperBooksIds) {
        Customer customer = repo.findById(UUID.fromString(customerId)).orElseThrow(() -> {
            throw new NotFoundObjectException("Customer Not Found", EBook.class.getName(), customerId);
        });

        List<PaperBook> allCustomerPaperBooks =
                (List<PaperBook>) paperBookRepo.findAllById(customerPaperBooksIds);

        customer.setPaperBooks(new HashSet<>(allCustomerPaperBooks));
        Customer savedCustomer = repo.save(customer);

        Set<UUID> allPaperBooksCustomerIds = new HashSet<>();
        for (PaperBook paperBook : savedCustomer.getPaperBooks()) {
            allPaperBooksCustomerIds.add(paperBook.getId());
        }

        return  allPaperBooksCustomerIds;
    }





}
