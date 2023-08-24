package com.example.locallibrary1.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class ReturnRequest {

    @NotNull
    Set<UUID> setEBooks;
    @NotNull
    Set<UUID>setPaperBooks;
}
