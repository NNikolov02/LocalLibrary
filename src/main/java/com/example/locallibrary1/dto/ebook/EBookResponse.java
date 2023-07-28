package com.example.locallibrary1.dto.ebook;

import com.example.locallibrary1.dto.AuthorDto;
import com.example.locallibrary1.dto.CustomerDto;
import com.example.locallibrary1.model.Author;
import com.example.locallibrary1.model.Customer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class EBookResponse {

    private UUID id;

    private String title;

    private String genre;

    private String summary;

    private String ISBN;

    private String link;
    private boolean isAccessed;


    private Set<AuthorDto> authors;


    private CustomerDto customer;
}
