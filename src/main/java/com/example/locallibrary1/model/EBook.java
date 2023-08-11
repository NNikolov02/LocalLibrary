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
public class EBook {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @JsonProperty("id")
    private UUID id;

    private String title;

    private String genre;

    private String summary;

    private String ISBN;

    private String linkForReading;
    private String linkForDownloading;
    private String url;
    private  Integer numberOfCopies;

    private Integer totalNumber;

    private boolean isAccessed;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "EBook_author",
            joinColumns = @JoinColumn(name = "EBook_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    @JsonIgnoreProperties("EBook")
    private Set<Author> authors;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
