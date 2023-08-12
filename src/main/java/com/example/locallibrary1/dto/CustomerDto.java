package com.example.locallibrary1.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CustomerDto {



    private String name;

    private String age;
    private String gender;
    private String address;
    private String city;
    private String country;
    private String email;
    private String url;

}
