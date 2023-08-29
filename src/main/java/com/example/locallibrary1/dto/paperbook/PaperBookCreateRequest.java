package com.example.locallibrary1.dto.paperbook;

import com.example.locallibrary1.dto.AuthorDto;
import com.example.locallibrary1.dto.CustomerDto;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class PaperBookCreateRequest {

    private String title;

    private String genre;

    private String summary;

    private String ISBN;


    private  Integer numberOfCopies;


    private Integer totalNumber;
    private boolean isBorrowed;


}
