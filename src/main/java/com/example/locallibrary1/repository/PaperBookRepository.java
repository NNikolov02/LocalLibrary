package com.example.locallibrary1.repository;

import com.example.locallibrary1.model.PaperBook;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;
import java.util.List;


@Repository
public interface PaperBookRepository extends JpaRepository<PaperBook, UUID> {
    PaperBook findByTitle(String title);
    List<PaperBook> findByGenre(String genre);

    //ArrayList<PaperBook> findByAuthors(String author);
}
