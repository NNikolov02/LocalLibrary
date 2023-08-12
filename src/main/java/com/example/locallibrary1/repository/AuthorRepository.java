package com.example.locallibrary1.repository;

import com.example.locallibrary1.model.Author;
import com.example.locallibrary1.model.Customer;
import com.example.locallibrary1.model.EBook;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AuthorRepository extends CrudRepository<Author, UUID> {

    Author searchAuthorByName(String name);
}