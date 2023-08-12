package com.example.locallibrary1.dto.author;

import com.example.locallibrary1.dto.EBookDto;
import com.example.locallibrary1.dto.PaperBookDto;
import com.example.locallibrary1.model.EBook;
import com.example.locallibrary1.model.PaperBook;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToMany;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class AuthorResponse {

    private UUID id;

    private String name;

    private String country;

    @JsonProperty("date_of_birth")
    private String dateOfBirth;
    private String url;
    @JsonProperty("date_of_death")
    private String dateOfDeath;




}
