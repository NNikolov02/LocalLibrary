package com.example.locallibrary1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class PaperBook {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @JsonProperty("id")
    private UUID id;

    private String title;

    private String genre;

    private String summary;

    private String ISBN;

    private  Integer numberOfCopies;
    private String url;



    private Integer totalNumber;
    private boolean isBorrowed;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "paperbook_author",
            joinColumns = @JoinColumn(name = "paperbook_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    @JsonIgnoreProperties("paperbook")
    private Set<Author> authors;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;



}
