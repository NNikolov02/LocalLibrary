package com.example.locallibrary1.dto.borrowinghistory;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class ReturnRequest {

    @NotNull
    String setEBooks;
    @NotNull
    String setPaperBooks;
    @NotNull
    String setCustomerName;
}
