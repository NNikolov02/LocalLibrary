package com.example.locallibrary1.repository;

import com.example.locallibrary1.model.Author;
import com.example.locallibrary1.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthorRepository extends CrudRepository<Author, UUID> {

}