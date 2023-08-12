package com.example.locallibrary1.dto.customer;

import com.example.locallibrary1.dto.EBookDto;
import com.example.locallibrary1.dto.PaperBookDto;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CustomerUpdateRequest {

    private String name;

    private String age;
    private String gender;
    private String address;
    private String city;
    private String country;
    private String email;





}
