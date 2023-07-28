package com.example.locallibrary1.dto.ebook;

import com.example.locallibrary1.dto.AuthorDto;
import com.example.locallibrary1.dto.CustomerDto;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class EBookCreateRequest {

    private String title;

    private String genre;

    private String summary;

    private String ISBN;

    private String link;
    private boolean isAccessed;


    private Set<AuthorDto> authors;


    private CustomerDto customer;
}
