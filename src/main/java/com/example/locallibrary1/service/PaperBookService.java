package com.example.locallibrary1.service;

import com.example.locallibrary1.error.NotFoundObjectException;
import com.example.locallibrary1.model.Author;
import com.example.locallibrary1.model.EBook;
import com.example.locallibrary1.model.PaperBook;
import com.example.locallibrary1.repository.AuthorRepository;
import com.example.locallibrary1.repository.PaperBookPagingRepository;
import com.example.locallibrary1.repository.PaperBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Component
@Service
public class PaperBookService {

    @Autowired
    private PaperBookRepository repo;

    @Autowired
    private PaperBookPagingRepository pagingRepo;

    @Autowired
    private AuthorRepository authorRepo;

    public Page<PaperBook> fetchAll(int currentPage, int pageSize) {
        return pagingRepo.findAll(PageRequest.of(currentPage, pageSize));
    }

    public PaperBook findById(String paperBookId) {
        return repo.findById(UUID.fromString(paperBookId)).orElseThrow(() -> {
            throw new NotFoundObjectException("PaperBook Not Found", PaperBook.class.getName(), paperBookId);
        });
    }


    public PaperBook save(PaperBook paperBook) {
        paperBook = repo.save(paperBook);



        return paperBook;
    }

    public PaperBook findTitle(String title) {
        return repo.findByTitle(title);
    }

    public List<PaperBook> findGenre(String genre) {
        return repo.findByGenre(genre);
    }
    public List<PaperBook> findAuthor(String author) {
        return repo.findByAuthorsName(author);
    }

    public void deleteById(String paperBookId){
        repo.deleteById(UUID.fromString(paperBookId));
    }

    public Set<UUID> setPaperBookAuthor(String paperBookId, Set<UUID> paperBookAuthorIds) {
        PaperBook paperBook = repo.findById(UUID.fromString(paperBookId)).orElseThrow(() -> {
            throw new NotFoundObjectException("PaperBook Not Found", EBook.class.getName(), paperBookId);
        });

        List<Author> allEbookAuthors =
                (List<Author>) authorRepo.findAllById(paperBookAuthorIds);

        paperBook.setAuthors(new HashSet<>(allEbookAuthors));
        PaperBook savedpaperBook= repo.save(paperBook);

        Set<UUID> allPaperBookAuthorIds = new HashSet<>();
        for (Author author : savedpaperBook.getAuthors()) {
            allPaperBookAuthorIds.add(author.getId());
        }

        return allPaperBookAuthorIds;
    }




}

