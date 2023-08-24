package com.example.locallibrary1.service;

import com.example.locallibrary1.dto.customer.CustomerCreateRequest;
import com.example.locallibrary1.error.NotFoundObjectException;
import com.example.locallibrary1.model.Customer;
import com.example.locallibrary1.model.EBook;
import com.example.locallibrary1.model.PaperBook;
import com.example.locallibrary1.model.VerificationToken;
import com.example.locallibrary1.registration.EmailPatternBuilder;
import com.example.locallibrary1.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
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
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private EmailService emailService;


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
    @Transactional
    public boolean deleteByEmail(String email){
        try {
            repo.deleteAllByEmail(email);
            return true; // Return 'true' on successful deletion
        } catch (Exception e) {
            // Handle any exceptions that might occur during deletion
            return false; // Return 'false' on deletion failure
        }
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
    public Customer registerNewCustomerAccount(CustomerCreateRequest customerDto)  {


        Customer customer = Customer.builder()
                .name(customerDto.getName())
                .country(customerDto.getCountry())
                .email(customerDto.getEmail())
                .build();

        Customer savedCustomer = repo.save(customer);

        // Create a verification token and associate it with the customer
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .customer(savedCustomer)
                .expiryDate(calculateExpiryDate(24 * 60)) // 24 hours expiration
                .build();
        emailService.sendSimpleMessage(customerDto.getEmail(),"Email Verification", EmailPatternBuilder.buildEmail("http://localhost:8084/library/customers/registration-success?" + verificationToken));

        verificationTokenRepository.save(verificationToken);

        return savedCustomer;
    }
    public void createVerificationToken(Customer customer, String token) {
        System.out.println("Creating verification token for customer: " + customer.getName());
        System.out.println("Token: " + token);
    }

    private boolean emailExists(String email) {
        return repo.findByEmail(email).isPresent();
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
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
