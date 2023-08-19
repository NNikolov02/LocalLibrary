package com.example.locallibrary1.web;

import com.example.locallibrary1.dto.borrowinghistory.BorrowingHistoryApiPage;
import com.example.locallibrary1.dto.borrowinghistory.BorrowingHistoryCreateRequest;
import com.example.locallibrary1.dto.borrowinghistory.BorrowingHistoryResponse;
import com.example.locallibrary1.dto.customer.CustomerApiPage;
import com.example.locallibrary1.dto.customer.CustomerResponse;
import com.example.locallibrary1.mapping.BorrowingHistoryMapper;
import com.example.locallibrary1.service.BorrowingHistoryService;
import com.example.locallibrary1.validation.ObjectValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
            return new BorrowingHistoryApiPage<>(historyPage);
    }

    @PostMapping("/borrow")
    public ResponseEntity<String> borrowEBook(@RequestBody Map<String, String> requestMap) {
        String customerEmail = requestMap.get("customerEmail");
        String eBookTitle = requestMap.get("eBookTitle");

        if (customerEmail == null || eBookTitle == null) {
            return ResponseEntity.badRequest().body("Invalid request data.");
        }

        boolean success = borrowingHistoryService.borrowForEbooks(customerEmail, eBookTitle);
        if (success) {
            return ResponseEntity.ok("Book borrowed successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to borrow book.");
        }
    }

    @PostMapping("/return")
    public ResponseEntity<String> returnBook(@RequestParam String customerEmail, @RequestParam String eBookTitle) {
        boolean success = borrowingHistoryService.returnEBook(customerEmail, eBookTitle);
        if (success) {
            return ResponseEntity.ok("Book returned successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to return book.");
        }
    }
}

