package com.example.locallibrary1.dto.customer;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class SetCustomerRequest {
    @NotNull
    Set<UUID> setCustomers;
}
