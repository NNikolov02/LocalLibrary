package com.example.locallibrary1.web;

import com.example.locallibrary1.dto.BorrowRequest;
import com.example.locallibrary1.dto.BorrowResponse;
import com.example.locallibrary1.dto.ReturnRequest;
import com.example.locallibrary1.dto.ReturnResponse;
import com.example.locallibrary1.dto.borrowinghistory.BorrowingHistoryApiPage;
import com.example.locallibrary1.dto.borrowinghistory.BorrowingHistoryCreateRequest;
import com.example.locallibrary1.dto.borrowinghistory.BorrowingHistoryResponse;
import com.example.locallibrary1.dto.customer.CustomerApiPage;
import com.example.locallibrary1.dto.customer.CustomerEBookResponse;
import com.example.locallibrary1.dto.customer.CustomerResponse;
import com.example.locallibrary1.dto.customer.SetCustomerRequest;
import com.example.locallibrary1.dto.ebook.SetEBookRequest;
import com.example.locallibrary1.dto.paperbook.SetPaperBookRequest;
import com.example.locallibrary1.error.InvalidObjectException;
import com.example.locallibrary1.mapping.BorrowingHistoryMapper;
import com.example.locallibrary1.model.BorrowingHistory;
import com.example.locallibrary1.service.BorrowingHistoryService;
import com.example.locallibrary1.validation.ObjectValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/library/history")
public class BorrowingHistoryController {

    @Autowired
    private BorrowingHistoryService borrowingHistoryService;
    @Autowired
    private BorrowingHistoryMapper borrowingHistoryMapper;
    @Autowired
    private ObjectValidator validator;

    @GetMapping(name = "", produces = "application/json")
    public BorrowingHistoryApiPage<BorrowingHistoryResponse> getAllHistory(



        @RequestParam(required = false, defaultValue = "0") Integer currPage) {
            Page<BorrowingHistoryResponse> historyPage =
                    borrowingHistoryService.fetchAll(currPage, 10).map(borrowingHistoryMapper::responseFromModel);
            //for(BorrowingHistoryResponse borrowingHistoryResponse:historyPage){
                //borrowingHistoryResponse.setBorrowDate(LocalDate.now());
               // borrowingHistoryResponse.setReturnDate(borrowingHistoryResponse.getBorrowDate().plusDays(borrowingHistoryResponse.getPostponementDays()));

            //}
            return new BorrowingHistoryApiPage<>(historyPage);
    }
    @GetMapping(value ="{historyId}")
    public ResponseEntity<BorrowingHistoryResponse>findById(@PathVariable String historyId){
        BorrowingHistory borrowingHistory = borrowingHistoryService.findById(historyId);
        BorrowingHistoryResponse borrowingHistoryResponse = borrowingHistoryMapper.responseFromModel(borrowingHistory);
        //borrowingHistoryResponse.setBorrowDate(LocalDate.now());
        //borrowingHistoryResponse.setReturnDate(borrowingHistoryResponse.getBorrowDate().plusDays(borrowingHistoryResponse.getPostponementDays()));
        //if (borrowingHistoryResponse.getEbooks()..getNumberOfCopies() > 0) {
            // eBook.setNumberOfCopies(eBook.getNumberOfCopies() - 1);
            //eBookRepo.save(eBook);


        return ResponseEntity.ok().body(borrowingHistoryResponse);
    }
    @PutMapping(value = "/{borrowHistoryId}/borrow")
    public BorrowResponse setCustomersEbooksPaperBooks(@PathVariable String borrowHistoryId, @RequestBody BorrowRequest request) {

        String historyPaperBooks = borrowingHistoryService.setHistoryPaperBooks(borrowHistoryId,request.getSetPaperBooks());
        String historyEbooks= borrowingHistoryService.setHistoryEbooks(borrowHistoryId,request.getSetEBooks());
        Set<UUID> historyCustomers = borrowingHistoryService.setHistoryCustomers(borrowHistoryId,request.getSetCustomers());



        BorrowResponse result = BorrowResponse.builder()
                .paperBook(historyPaperBooks)
                .eBook(historyEbooks)
                .HistoryCustomerIds(historyCustomers)
                .build();

        return result;
    }

    @PutMapping(value = "/{borrowHistoryId}/return")
    public ReturnResponse setCustomersEbooksPaperBooks(@PathVariable String borrowHistoryId, @RequestBody ReturnRequest request) {
        String historyEbooks = borrowingHistoryService.returnEbooks(borrowHistoryId,request.getSetEBooks());
        String historyPaperBooks = borrowingHistoryService.returnPaperBooks(borrowHistoryId,request.getSetPaperBooks());


        ReturnResponse result = ReturnResponse.builder()
                .paperBooks(historyPaperBooks)
                .eBooks(historyEbooks)
                .build();

        return result;
    }

    @PostMapping("")
    public ResponseEntity<BorrowingHistoryResponse>create(@RequestBody BorrowingHistoryCreateRequest createDto){
        Map<String, String> validationErrors = validator.validate(createDto);
        if (validationErrors.size() != 0) {
            throw new InvalidObjectException("Invalid History Create", validationErrors);
        }
            BorrowingHistory borrowingHistory = borrowingHistoryMapper.modelFromCreateRequest(createDto);
            borrowingHistory.setBorrowDate(LocalDate.now());
            borrowingHistory.setReturnDate(borrowingHistory.getBorrowDate().plusDays(borrowingHistory.getPostponementDays()));
            BorrowingHistory saved = borrowingHistoryService.save(borrowingHistory);
            BorrowingHistoryResponse borrowingHistoryResponse = borrowingHistoryMapper.responseFromModel(saved);



            return ResponseEntity.status(201).body(borrowingHistoryResponse);
        }


    //@PostMapping("/borrow")
    //public ResponseEntity<String> borrowEBook(@RequestBody BorrowRequest request) {
        //Map<String, String> validationErrors = validator.validate( request);
        //if (validationErrors.size() != 0) {
            //throw new InvalidObjectException("Invalid  Create", validationErrors);
       // }

        //ResponseEntity<String> response = borrowingHistoryService.borrowForEbooks(request.getCustomerId(),request.getEBookId());
       // return response;
   // }


    //if (customerEmail == null || eBookTitle == null) {
            //return ResponseEntity.badRequest().body("Invalid request data.");
        //}

        //boolean success = borrowingHistoryService.borrowForEbooks(customerEmail, eBookTitle);
       // if (success) {
          //  return ResponseEntity.ok("Book borrowed successfully.");
       // } else {
           // return ResponseEntity.badRequest().body("Failed to borrow book.");
       // }


    //@PostMapping("/return")
   // public ResponseEntity<String> returnBook(@RequestParam String customerEmail, @RequestParam String eBookTitle) {
       // boolean success = borrowingHistoryService.returnEBook(customerEmail, eBookTitle);
       // if (success) {
           // return ResponseEntity.ok("Book returned successfully.");
        //} else {
          //  return ResponseEntity.badRequest().body("Failed to return book.");
      //  }
    //}
}

