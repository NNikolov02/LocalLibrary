package com.example.locallibrary1.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class BorrowRequest {
    @NotNull
    Set<UUID> setCustomers;
    @NotNull
    Set<UUID> setEBooks;
    @NotNull
    Set<UUID>setPaperBooks;
}
