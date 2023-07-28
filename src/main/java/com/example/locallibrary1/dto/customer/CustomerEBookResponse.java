package com.example.locallibrary1.dto.customer;

import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class CustomerEBookResponse {

    private Set<UUID> CustomerEBookIds;
}
