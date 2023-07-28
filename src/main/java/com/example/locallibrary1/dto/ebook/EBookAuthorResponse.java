package com.example.locallibrary1.dto.ebook;

import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class EBookAuthorResponse {
    private Set<UUID> EBookAuthorIds;

}
