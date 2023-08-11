package com.example.locallibrary1.dto.ebook;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.Set;
import java.util.UUID;

@Data
public class SetEBookRequest {

    @NotNull
    Set<UUID> setEBooks;
}
