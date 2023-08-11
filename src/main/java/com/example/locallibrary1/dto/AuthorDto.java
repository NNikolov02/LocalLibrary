package com.example.locallibrary1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AuthorDto {



    private String name;

    private String country;

    @JsonProperty("date_of_birth")
    private String dateOfBirth;
    private String url;

    @JsonProperty("date_of_death")
    private String dateOfDeath;
}
