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
    private  Integer numberOfCopies;

    private Integer totalNumber;
    private String url;


    private String linkForReading;
    private String linkForDownloading;
    private boolean isAccessed;
}
