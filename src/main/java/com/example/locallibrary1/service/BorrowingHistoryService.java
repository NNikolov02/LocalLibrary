package com.example.locallibrary1.service;

import com.example.locallibrary1.dto.BorrowRequest;
import com.example.locallibrary1.error.NotFoundObjectException;
import com.example.locallibrary1.model.*;
import com.example.locallibrary1.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Component
@Service
public class BorrowingHistoryService {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private EBookRepository eBookRepo;

    @Autowired
    private PaperBookRepository paperBookRepo;
    @Autowired
    private BorrowingHistoryRepository repo;
    @Autowired
    private BorrowingHistoryPagingRepository pagingRepo;

    public BorrowingHistory save(BorrowingHistory borrowingHistory){
        return repo.save(borrowingHistory);
    }

    public Page<BorrowingHistory> fetchAll(int currentPage, int pageSize) {
        return pagingRepo.findAll(PageRequest.of(currentPage, pageSize));
    }
    public BorrowingHistory findById(String historyId) {
        return repo.findById(UUID.fromString(historyId)).orElseThrow(() -> {
            throw new NotFoundObjectException("History Not Found", BorrowingHistory.class.getName(), historyId);
        });
    }

    public void borrowForPaperBooks(String paperBooksTitle,String customerEmail ) {
        PaperBook paperBook = paperBookRepo.findByTitle(paperBooksTitle);
        Customer customer = customerRepo.findCustomerByEmail(customerEmail);

        if (paperBook != null && customer != null && paperBook.getNumberOfCopies() > 0) {

            paperBook.setNumberOfCopies(paperBook.getNumberOfCopies() - 1);
            paperBookRepo.save(paperBook);
            //BorrowingHistory history = BorrowingHistory
                    //.builder()
                    //.PaperBook(paperBook)
                    //.customer(customer)
                    //.borrowDate(LocalDate.now())
                    //.postponementDays(14)
                    //.build();



        }


    }
    public ResponseEntity<String> borrowForEbooks(String customerId, String eBookId) {
        try {


            Customer customer = customerRepo.findById(UUID.fromString(customerId)).orElseThrow(() -> {
                throw new NotFoundObjectException("Customer Not Found", EBook.class.getName(), customerId);
            });

            EBook eBook = eBookRepo.findById(UUID.fromString(eBookId)).orElseThrow(() -> {
                throw new NotFoundObjectException("EBook Not Found", EBook.class.getName(), eBookId);
            });

            if (eBook.getNumberOfCopies() > 0) {
                eBook.setNumberOfCopies(eBook.getNumberOfCopies() - 1);
                eBookRepo.save(eBook);

                //BorrowingHistory history = BorrowingHistory.builder()
                        //.EBook(eBook)
                        //.customer(customer)
                        //.borrowDate(LocalDate.now())
                        //.postponementDays(14)
                        //.build();

                // You can customize the success message here
                String successMessage = "Book borrowed successfully.";
                return ResponseEntity.ok(successMessage);
            } else {
                // You can customize the error message here
                String errorMessage = "No copies of the book available.";
                return ResponseEntity.badRequest().body(errorMessage);
            }
        } catch (IllegalArgumentException e) {
            // Invalid UUID format
            String errorMessage = "Invalid input parameters.";
            return ResponseEntity.badRequest().body(errorMessage);
        } catch (NotFoundObjectException e) {
            // Object not found in repository
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    public String setHistoryEbooks(String borrowHistoryId, Set<UUID> historyEbookIds) {
        BorrowingHistory borrowingHistory= repo.findById(UUID.fromString(borrowHistoryId)).orElseThrow(() -> {
            throw new NotFoundObjectException("History Not Found",BorrowingHistory.class.getName(), borrowHistoryId);
        });

        List<EBook> allAuthorEbooks =
                (List<EBook>) eBookRepo.findAllById(historyEbookIds);
        for(EBook eBook:allAuthorEbooks){
            if (eBook.getNumberOfCopies() > 0) {
                eBook.setNumberOfCopies(eBook.getNumberOfCopies() - 1);
                eBookRepo.save(eBook);
            }else{

            }
        }

        borrowingHistory.setEbooks(new HashSet<>(allAuthorEbooks));
        BorrowingHistory savedHistory = repo.save(borrowingHistory);

       // Set<UUID> allEbookCustomerIds = new HashSet<>();
        //for (EBook eBook : savedHistory.getEbooks()) {

            //allEbookCustomerIds.add(eBook.getId());

        //}

        return "Ebooks is  borrowed!";
    }
    public String setHistoryPaperBooks(String borrowHistoryId, Set<UUID> historypaperBooksIds) {
        BorrowingHistory borrowingHistory = repo.findById(UUID.fromString(borrowHistoryId)).orElseThrow(() -> {
            throw new NotFoundObjectException("History Not Found", BorrowingHistory.class.getName(), borrowHistoryId);
        });

        List<PaperBook> paperBooks =
                (List<PaperBook>) paperBookRepo.findAllById(historypaperBooksIds);
        for(PaperBook paperBook:paperBooks){
            if (paperBook.getNumberOfCopies() > 0) {
                paperBook.setNumberOfCopies(paperBook.getNumberOfCopies() - 1);
                paperBookRepo.save(paperBook);
            }
        }

        borrowingHistory.setPaperBooks(new HashSet<>(paperBooks));
        BorrowingHistory savedHistory = repo.save(borrowingHistory);

        //Set<UUID> allEbookCustomerIds = new HashSet<>();
        //for (PaperBook paperBook : savedHistory.getPaperBooks()) {
            //allEbookCustomerIds.add(paperBook.getId());
        //}
        return "PaperBook is borrowed!";
    }
        public Set<UUID> setHistoryCustomers(String borrowHistoryId, Set<UUID> historyCustomersIds) {
            BorrowingHistory borrowingHistory= repo.findById(UUID.fromString(borrowHistoryId)).orElseThrow(() -> {
                throw new NotFoundObjectException("History Not Found",BorrowingHistory.class.getName(), borrowHistoryId);
            });

            List<Customer> customers =
                    (List<Customer>) customerRepo.findAllById(historyCustomersIds);
            borrowingHistory.setCustomers(new HashSet<>(customers));
            BorrowingHistory savedHistory = repo.save(borrowingHistory);

            Set<UUID> allEbookCustomerIds = new HashSet<>();
            for (Customer customer : savedHistory.getCustomers()) {
                allEbookCustomerIds.add(customer.getId());
            }


        return allEbookCustomerIds;
    }
    public String returnEbooks(String borrowHistoryId,Set<UUID> historyEbookIds){
        BorrowingHistory borrowingHistory= repo.findById(UUID.fromString(borrowHistoryId)).orElseThrow(() -> {
            throw new NotFoundObjectException("History Not Found",BorrowingHistory.class.getName(), borrowHistoryId);
        });
        LocalDate currentDate = LocalDate.now();
        if(currentDate.isEqual(borrowingHistory.getReturnDate())) {

        List<EBook> allAuthorEbooks =
                (List<EBook>) eBookRepo.findAllById(historyEbookIds);

            for (EBook eBook : allAuthorEbooks) {

                eBook.setNumberOfCopies(eBook.getNumberOfCopies() + 1);
                eBookRepo.save(eBook);


            }


            borrowingHistory.setEbooks(null);
            BorrowingHistory savedHistory = repo.save(borrowingHistory);

            return "The Ebook is returned";
        }

       // Set<UUID> allEbookCustomerIds = new HashSet<>();
        //for (EBook eBook : savedHistory.getEbooks()) {

            //allEbookCustomerIds.add(eBook.getId());

        //}

        return "The Ebook cannot yet be  returned";

    }
    public String returnPaperBooks(String borrowHistoryId, Set<UUID> historypaperBooksIds) {
        BorrowingHistory borrowingHistory = repo.findById(UUID.fromString(borrowHistoryId)).orElseThrow(() -> {
            throw new NotFoundObjectException("History Not Found", BorrowingHistory.class.getName(), borrowHistoryId);
        });
        LocalDate currentDate = LocalDate.now();
        if(currentDate.isEqual(borrowingHistory.getReturnDate())) {


        List<PaperBook> paperBooks =
                (List<PaperBook>) paperBookRepo.findAllById(historypaperBooksIds);

            for (PaperBook paperBook : paperBooks) {

                paperBook.setNumberOfCopies(paperBook.getNumberOfCopies() + 1);
                paperBookRepo.save(paperBook);

            }

            borrowingHistory.setPaperBooks(null);
            BorrowingHistory savedHistory = repo.save(borrowingHistory);

            return "The PaperBook is returned";
        }

       // Set<UUID> allEbookCustomerIds = new HashSet<>();
        //for (PaperBook paperBook : savedHistory.getPaperBooks()) {
           // allEbookCustomerIds.add(paperBook.getId());
       // }
        return "The PaperBook cannot yet be returned";
    }





    public Set<UUID> setCustomersEBooksPaperBooks(String borrowHistoryId, Set<UUID> customerIds,Set<UUID> eBookIds,Set<UUID>paperBooksIds) {
        BorrowingHistory borrowingHistory= repo.findById(UUID.fromString(borrowHistoryId)).orElseThrow(() -> {
            throw new NotFoundObjectException("History Not Found",BorrowingHistory.class.getName(), borrowHistoryId);
        });
       // Optional<Customer> customer =customerRepo.findById(customerId);
        List<Customer> customers =
                (List<Customer>) customerRepo.findAllById(customerIds);
        List<EBook> eBooks =
                (List<EBook>) eBookRepo.findAllById(eBookIds);
        List<PaperBook> paperBooks =
                (List<PaperBook>) paperBookRepo.findAllById(paperBooksIds);


        borrowingHistory.setCustomers(new HashSet<>(customers));


        borrowingHistory.setPaperBooks(new HashSet<>(paperBooks));
        borrowingHistory.setEbooks(new HashSet<>(eBooks));
        BorrowingHistory savedHistory = repo.save(borrowingHistory);

        Set<UUID> allIds = new HashSet<>();
        for (PaperBook paperBook : savedHistory.getPaperBooks()) {
            if (paperBook.getNumberOfCopies() > 0) {
                paperBook.setNumberOfCopies(paperBook.getNumberOfCopies() - 1);
                paperBookRepo.save(paperBook);
            }
            allIds.add(paperBook.getId());
        }
        for (EBook eBook : savedHistory.getEbooks()) {
            allIds.add(eBook.getId());
            if (eBook.getNumberOfCopies() > 0) {
                eBook.setNumberOfCopies(eBook.getNumberOfCopies() - 1);
                eBookRepo.save(eBook);
            }
        }
        for (Customer customer : savedHistory.getCustomers()) {
            allIds.add(customer.getId());
        }


        return  allIds;
    }






    //public boolean returnEBook(String eBooksTitle,String customerEmail) {
       // EBook eBook = eBookRepo.findByTitle(eBooksTitle);
        //Customer customer = customerRepo.findCustomerByEmail(customerEmail);

        //if (eBook != null && customer != null) {
            //BorrowingHistory history = repo.findByCustomerAndEBookAndReturnDateIsNull(customer,eBook);

           // if (history != null) {


               // eBook.setNumberOfCopies(eBook.getNumberOfCopies() + 1);
                //eBookRepo.save(eBook);

               // history.setReturnDate(LocalDate.now());
               // repo.save(history);

               // return true;
            //}
        //}

        //return false;
    }
   // public boolean returnPaperBook(String paperBookTitle,String customerEmail) {
       // PaperBook paperBook= paperBookRepo.findByTitle(paperBookTitle);
       // Customer customer = customerRepo.findCustomerByEmail(customerEmail);

        //if (paperBook != null && customer != null) {
            //BorrowingHistory history = repo.findByCustomerAndPaperBookAndReturnDateIsNull(customer,paperBook);

           // if (history != null) {


                //paperBook.setNumberOfCopies(paperBook.getNumberOfCopies() + 1);
               // paperBookRepo.save(paperBook);

               // history.setReturnDate(LocalDate.now());
                //repo.save(history);

                //return true;
            //}
       // }

       // return false;
   // }



