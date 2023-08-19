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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "eBook_id_author") // Specify a unique name for the join column
    @JsonIgnoreProperties("eBook")
    private Set<Author> authors;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @OneToOne(mappedBy = "EBook")
    @JsonIgnoreProperties("EBook")
    private BorrowingHistory borrowHistory;

}
