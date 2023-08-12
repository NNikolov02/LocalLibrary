package com.example.locallibrary1.dto.customer;

import com.example.locallibrary1.dto.EBookDto;
import com.example.locallibrary1.dto.PaperBookDto;
import com.example.locallibrary1.model.EBook;
import com.example.locallibrary1.model.PaperBook;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class CustomerResponse {

    private UUID id;

    private String name;

    private String age;
    private String gender;
    private String address;
    private String city;
    private String country;
    private String email;
    private  String url;




}
