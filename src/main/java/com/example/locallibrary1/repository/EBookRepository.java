package com.example.locallibrary1.repository;

import com.example.locallibrary1.model.EBook;
import com.example.locallibrary1.model.PaperBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public interface EBookRepository extends CrudRepository<EBook, UUID> {
    EBook findByTitle(String title);
    ArrayList<EBook> findByGenre(String genre);


    ArrayList<EBook> findByAuthorsEBooks(String author);
}
