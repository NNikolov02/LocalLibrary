package com.example.locallibrary1.dto.borrowinghistory;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReturnResponse {
    private  String eBooks;
    private String paperBooks;


}
