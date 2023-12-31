package com.example.locallibrary1.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @JsonProperty("id")
    private UUID id;

    private String name;

    private String country;

    @JsonProperty("date_of_birth")
    private String dateOfBirth;

    @JsonProperty("date_of_death")
    private String dateOfDeath;

    private String url;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eBook_id")
    private EBook EBook;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paperBook_id")
    private PaperBook paperBooks;



}
