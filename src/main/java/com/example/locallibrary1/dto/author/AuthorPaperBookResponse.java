package com.example.locallibrary1.dto.author;

import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class AuthorPaperBookResponse {

    private Set<UUID> AuthorPaperBookIds;
}
