package com.example.locallibrary1.dto.author;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class SetAuthorRequest {

    @NotNull
    Set<UUID> setAuthors;
}
