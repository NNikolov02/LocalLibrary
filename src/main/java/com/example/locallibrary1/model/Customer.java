package com.example.locallibrary1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @JsonProperty("id")
    private UUID id;

    private String name;

    private String age;
    private String gender;
    private String address;
    private String city;
    private String country;
    private String email;
    private String url;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id_paperbook") // Specify a unique name for the join column
    @JsonIgnoreProperties("customer")
    private Set<PaperBook> paperBooks;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id_ebook") // Specify a unique name for the join column
    @JsonIgnoreProperties("customer")
    private Set<EBook> eBooks;
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "verification_token_id")
    @JsonIgnoreProperties("customer")
    private VerificationToken verificationToken;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrowHistory_id")
    private BorrowingHistory borrowHistory;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;


        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());


        return result;
    }
}
