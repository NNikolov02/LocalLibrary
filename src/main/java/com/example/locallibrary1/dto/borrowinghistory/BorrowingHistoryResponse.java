package com.example.locallibrary1.dto.borrowinghistory;

import com.example.locallibrary1.dto.EBookDto;
import com.example.locallibrary1.model.Customer;
import com.example.locallibrary1.model.EBook;
import com.example.locallibrary1.model.PaperBook;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class BorrowingHistoryResponse {


    private UUID id;

    private Set<String> customers;

    private Set<String> ebooks;

    private Set<String> paperBooks;

    private LocalDate borrowDate;
    private LocalDate returnDate;
    private Long postponementDays;
}
