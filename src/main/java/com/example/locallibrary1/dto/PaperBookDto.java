package com.example.locallibrary1.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaperBookDto {


    private String title;

    private String genre;

    private String summary;

    private String ISBN;

    private  Integer numberOfCopies;

    private Integer totalNumber;
    private boolean isBorrowed;
}
