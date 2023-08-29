package com.example.locallibrary1.dto.borrowinghistory;

import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class BorrowResponse {
    private String eBook;
    private String HistoryCustomers;
    private String paperBook;

}
