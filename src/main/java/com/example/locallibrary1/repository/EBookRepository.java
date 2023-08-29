package com.example.locallibrary1.repository;

import com.example.locallibrary1.model.EBook;
import com.example.locallibrary1.model.PaperBook;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface EBookRepository extends CrudRepository<EBook, UUID> {
    EBook findByTitle(String title);
    List<EBook> findAllByTitle(String title);
    List<EBook> findByGenre(String genre);


    @Query("SELECT e FROM EBook e JOIN e.authors a WHERE a.name = :authorName")
    List<EBook> findByAuthorsName(@Param("authorName") String authorName);
}
