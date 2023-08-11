package com.example.locallibrary1.dto.author;

import com.example.locallibrary1.dto.EBookDto;
import com.example.locallibrary1.dto.PaperBookDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class AuthorUpdateRequest {

    private String name;

    private String country;

    @JsonProperty("date_of_birth")
    private String dateOfBirth;

    @JsonProperty("date_of_death")
    private String dateOfDeath;

}
