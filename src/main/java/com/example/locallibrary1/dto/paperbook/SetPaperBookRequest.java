package com.example.locallibrary1.dto.paperbook;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class SetPaperBookRequest {

    @NotNull
    Set<UUID> setPaperBooks;
}
