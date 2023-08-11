package com.example.locallibrary1.service;

import com.example.locallibrary1.error.NotFoundObjectException;
import com.example.locallibrary1.model.Author;
import com.example.locallibrary1.model.EBook;
import com.example.locallibrary1.model.PaperBook;
import com.example.locallibrary1.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Component
@Service
public class EbookService {

    @Autowired
    private EBookRepository repo;

    @Autowired
    private EBookPagingRepository pagingRepo;

    @Autowired
    private AuthorRepository authorRepo;

    public Page<EBook> fetchAll(int currentPage, int pageSize) {
        return pagingRepo.findAll(PageRequest.of(currentPage, pageSize));
    }

    public EBook findById(String eBookId) {
        return repo.findById(UUID.fromString(eBookId)).orElseThrow(() -> {
            throw new NotFoundObjectException("EBook Not Found", EBook.class.getName(), eBookId);
        });
    }


    public EBook save(EBook eBook) {
        return repo.save(eBook);
    }

    public EBook findTitle(String title) {
        return repo.findByTitle(title);
    }

    public List<EBook> findGenre(String genre) {
        return repo.findByGenre(genre);
    }
    public List<EBook> findAuthors(String author) {
        return repo.findByAuthorsName(author);
    }

    public void deleteById(String eBookId){
        repo.deleteById(UUID.fromString(eBookId));
    }

    public Set<UUID> setEbookAuthor(String eBookId, Set<UUID> eBookAuthorIds) {
        EBook eBook = repo.findById(UUID.fromString(eBookId)).orElseThrow(() -> {
            throw new NotFoundObjectException("EBook Not Found", EBook.class.getName(), eBookId);
        });

        List<Author> allEbookAuthors =
                (List<Author>) authorRepo.findAllById(eBookAuthorIds);

        eBook.setAuthors(new HashSet<>(allEbookAuthors));
        EBook savedEbook = repo.save(eBook);

        Set<UUID> allEbookAuthorIds = new HashSet<>();
        for (Author author : savedEbook.getAuthors()) {
            allEbookAuthorIds.add(author.getId());
        }

        return allEbookAuthorIds;
    }
}
