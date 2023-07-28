package com.example.locallibrary1.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class EBookDto {


    private String title;

    private String genre;

    private String summary;

    private String ISBN;

    private String link;
    private boolean isAccessed;
}
