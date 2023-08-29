package com.example.locallibrary1.repository;

import com.example.locallibrary1.model.Customer;
import com.example.locallibrary1.model.PaperBook;
import com.example.locallibrary1.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, UUID> {

     Boolean existsByToken(String token);
     VerificationToken findByCustomer(Customer customer);




}
