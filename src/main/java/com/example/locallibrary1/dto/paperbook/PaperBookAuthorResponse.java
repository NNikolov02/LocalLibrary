package com.example.locallibrary1.dto.paperbook;

import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class PaperBookAuthorResponse {

    private Set<UUID> PaperBookAuthorIds;
}
