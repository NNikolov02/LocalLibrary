package com.example.locallibrary1.dto.paperbook;

import com.example.locallibrary1.dto.AuthorDto;
import com.example.locallibrary1.dto.CustomerDto;
import com.example.locallibrary1.model.Author;
import com.example.locallibrary1.model.Customer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class PaperBookResponse {


    private UUID id;

    private String title;

    private String genre;

    private String summary;

    private String ISBN;

    private  Integer numberOfCopies;

    private Integer totalNumber;
    private boolean isBorrowed;


    private Set<AuthorDto> authors;


    private CustomerDto customer;



}
