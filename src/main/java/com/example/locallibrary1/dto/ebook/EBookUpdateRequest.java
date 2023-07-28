package com.example.locallibrary1.dto.ebook;

import com.example.locallibrary1.dto.AuthorDto;
import com.example.locallibrary1.dto.CustomerDto;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class EBookUpdateRequest {

    private String title;

    private String genre;

    private String summary;

    private String ISBN;

    private String link;


    private Set<AuthorDto> authors;


    private CustomerDto customer;
    private boolean isAccessed;
}
