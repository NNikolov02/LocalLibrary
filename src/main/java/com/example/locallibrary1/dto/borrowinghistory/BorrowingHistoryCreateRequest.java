package com.example.locallibrary1.dto.borrowinghistory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor  // Add this annotation to generate a no-args constructor
@AllArgsConstructor  // Add this annotation to generate a constructor with all arguments
public class BorrowingHistoryCreateRequest {
    private Long postponementDays;
}
