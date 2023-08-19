package com.example.locallibrary1.service;

import com.example.locallibrary1.error.NotFoundObjectException;
import com.example.locallibrary1.model.BorrowingHistory;
import com.example.locallibrary1.model.Customer;
import com.example.locallibrary1.model.EBook;
import com.example.locallibrary1.model.PaperBook;
import com.example.locallibrary1.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

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

    public Page<BorrowingHistory> fetchAll(int currentPage, int pageSize) {
        return pagingRepo.findAll(PageRequest.of(currentPage, pageSize));
    }
    public BorrowingHistory findById(String historyId) {
        return repo.findById(UUID.fromString(historyId)).orElseThrow(() -> {
            throw new NotFoundObjectException("History Not Found", BorrowingHistory.class.getName(), historyId);
        });
    }

    public boolean borrowForPaperBooks(String paperBooksTitle,String customerEmail ) {
        PaperBook paperBook = paperBookRepo.findByTitle(paperBooksTitle);
        Customer customer = customerRepo.findCustomerByEmail(customerEmail);

        if (paperBook != null && customer != null && paperBook.getNumberOfCopies() > 0) {

            paperBook.setNumberOfCopies(paperBook.getNumberOfCopies() - 1);
            paperBookRepo.save(paperBook);
            BorrowingHistory history = BorrowingHistory
                    .builder()
                    .PaperBook(paperBook)
                    .customer(customer)
                    .borrowDate(LocalDate.now())
                    .postponementDays(14)
                    .build();

            return true;

        }

        return false;
    }
    public boolean borrowForEbooks(String eBooksTitle,String customerEmail ) {
        EBook eBook = eBookRepo.findByTitle(eBooksTitle);
        Customer customer = customerRepo.findCustomerByEmail(customerEmail);

        if (eBook != null && customer != null && eBook.getNumberOfCopies() > 0) {

            eBook.setNumberOfCopies(eBook.getNumberOfCopies() - 1);
            eBookRepo.save(eBook);
            BorrowingHistory history = BorrowingHistory
                    .builder()
                    .EBook(eBook)
                    .customer(customer)
                    .borrowDate(LocalDate.now())
                    .postponementDays(14)
                    .build();

            return true;

        }

        return false;
    }

    public boolean returnEBook(String eBooksTitle,String customerEmail) {
        EBook eBook = eBookRepo.findByTitle(eBooksTitle);
        Customer customer = customerRepo.findCustomerByEmail(customerEmail);

        if (eBook != null && customer != null) {
            BorrowingHistory history = repo.findByCustomerAndEBookAndReturnDateIsNull(customer,eBook);

            if (history != null) {


                eBook.setNumberOfCopies(eBook.getNumberOfCopies() + 1);
                eBookRepo.save(eBook);

                history.setReturnDate(LocalDate.now());
                repo.save(history);

                return true;
            }
        }

        return false;
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


}
