package com.example.locallibrary1.service;


import com.example.locallibrary1.error.NotFoundObjectException;
import com.example.locallibrary1.model.Author;
import com.example.locallibrary1.model.EBook;
import com.example.locallibrary1.model.PaperBook;
import com.example.locallibrary1.repository.AuthorPagingRepository;
import com.example.locallibrary1.repository.AuthorRepository;
import com.example.locallibrary1.repository.EBookRepository;
import com.example.locallibrary1.repository.PaperBookRepository;
import com.sun.source.doctree.AuthorTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
@Service
public class AuthorService {

    @Autowired
    private AuthorRepository repo;

    @Autowired
    private AuthorPagingRepository pagingRepo;

    @Autowired
    private EBookRepository eBookRepo;

    @Autowired
    private PaperBookRepository paperBookRepo;

    public Author save(Author author){
        return repo.save(author);
    }


    public Page<Author> fetchAll(int currentPage, int pageSize) {
        return pagingRepo.findAll(PageRequest.of(currentPage, pageSize));
    }

    public Author findById(String authorId) {
        return repo.findById(UUID.fromString(authorId)).orElseThrow(() -> {
            throw new NotFoundObjectException("Author Not Found", Author.class.getName(), authorId);
        });
    }

    public void deleteById(String authorId){
        repo.deleteById(UUID.fromString(authorId));
    }


    public Set<UUID> setAuthorsEbooks(String authorId, Set<UUID> authorEbookIds) {
        Author author = repo.findById(UUID.fromString(authorId)).orElseThrow(() -> {
            throw new NotFoundObjectException("Author Not Found", EBook.class.getName(), authorId);
        });

        List<EBook> allAuthorEbooks =
                (List<EBook>) eBookRepo.findAllById(authorEbookIds);

        author.setEBooks(new HashSet<>(allAuthorEbooks));
        Author savedAuthor = repo.save(author);

        Set<UUID> allEbookAuthorIds = new HashSet<>();
        for (EBook eBook : savedAuthor.getEBooks()) {
            allEbookAuthorIds.add(eBook.getId());
        }

        return allEbookAuthorIds;
    }

    public Set<UUID> setAuthorsPaperBooks(String authorId, Set<UUID> authorPaperBooksIds) {
        Author author = repo.findById(UUID.fromString(authorId)).orElseThrow(() -> {
            throw new NotFoundObjectException("Author Not Found", EBook.class.getName(), authorId);
        });

        List<PaperBook> allAuthorPaperBooks =
                (List<PaperBook>) paperBookRepo.findAllById(authorPaperBooksIds);

        author.setPaperBooks(new HashSet<>(allAuthorPaperBooks));
        Author savedAuthor = repo.save(author);

        Set<UUID> allPaperBooksAuthorIds = new HashSet<>();
        for (PaperBook paperBook : savedAuthor.getPaperBooks()) {
            allPaperBooksAuthorIds.add(paperBook.getId());
        }

        return allPaperBooksAuthorIds;
    }
}
