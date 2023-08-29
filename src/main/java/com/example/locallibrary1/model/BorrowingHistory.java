package com.example.locallibrary1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import com.example.locallibrary1.model.Customer;
import com.example.locallibrary1.model.EBook;
import com.example.locallibrary1.model.PaperBook;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BorrowingHistory {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @JsonProperty("id")
    private UUID id;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "borrowHistory_id_customer") // Specify a unique name for the join column
    @JsonIgnoreProperties("borrowHistory")
    private Set<Customer> customers;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "borrowHistory_id_ebook") // Specify a unique name for the join column
    @JsonIgnoreProperties("borrowHistory")
    private Set<EBook> ebooks;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "borrowHistory_id_paperBook") // Specify a unique name for the join column
    @JsonIgnoreProperties("borrowHistory")
    private Set<PaperBook> paperBooks;

    private LocalDate borrowDate;
    private LocalDate returnDate;
    private int postponementDays;
    private BigDecimal fine;


}

