package com.example.locallibrary1.dto.borrowinghistory;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class BorrowRequest {
    @NotNull
    String setCustomers;
    @NotNull
    String setEBooks;
    @NotNull
    String setPaperBooks;
}
