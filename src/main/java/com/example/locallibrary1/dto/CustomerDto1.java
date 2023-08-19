package com.example.locallibrary1.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDto1 {
    private String name;
    private String email;

    private String country;


}
