package com.example.locallibrary1.web;

import com.example.locallibrary1.dto.author.*;
import com.example.locallibrary1.dto.ebook.EBookApiPage;
import com.example.locallibrary1.dto.ebook.EBookResponse;
import com.example.locallibrary1.dto.ebook.SetEBookRequest;
import com.example.locallibrary1.dto.paperbook.PaperBookAuthorResponse;
import com.example.locallibrary1.dto.paperbook.SetPaperBookRequest;
import com.example.locallibrary1.error.InvalidObjectException;
import com.example.locallibrary1.mapping.AuthorMapper;
import com.example.locallibrary1.model.Author;
import com.example.locallibrary1.repository.AuthorPagingRepository;
import com.example.locallibrary1.repository.AuthorRepository;
import com.example.locallibrary1.service.AuthorService;
import com.example.locallibrary1.validation.ObjectValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/library/authors")
@AllArgsConstructor
public class AuthorController {

    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private ObjectValidator validator;


    private final Integer Page_Size = 10;

    @GetMapping(name = "", produces = "application/json")
    public AuthorApiPage<AuthorResponse> getAllAuthors(
            @RequestParam(required = false, defaultValue = "1") Integer currPage) {


        Page<AuthorResponse> authorPage = authorService.fetchAll(currPage - 1, 10).map(authorMapper::responseFromModel);

        for (AuthorResponse response : authorPage){
            response.setUrl("http://localhost:8084/library/authors/name/" + response.getName());
        }

        return new AuthorApiPage<>(authorPage);
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<AuthorResponse> findAuthor(@PathVariable String authorId){
        Author author = authorService.findById(authorId);

        AuthorResponse authorResponse = authorMapper.responseFromModel(author);
        authorResponse.setUrl("http://localhost:8084/library/authors/name/" + authorResponse.getName());

        return ResponseEntity.ok().body(authorResponse);
    }
    @GetMapping("/name/{authorName}")
    public ResponseEntity<AuthorResponse>searchByAuthorsName(@PathVariable String authorName){
        Author author = authorService.searchAuthorName(authorName);
        if (author == null) {
            return ResponseEntity.notFound().build();
        }
        AuthorResponse authorResponse = authorMapper.responseFromModel(author);
        authorResponse.setUrl("http://localhost:8084/library/authors/name/" + authorResponse.getName());

        return ResponseEntity.ok().body(authorResponse);


    }
    @DeleteMapping("/{authorId}")
    public void deleteById(@PathVariable String authorId){
        authorService.deleteById(authorId);
    }

    @PostMapping("")
    public ResponseEntity<AuthorResponse>createAuthor(@RequestBody AuthorCreateRequest authorDto){

        Map<String, String> validationErrors = validator.validate( authorDto);
        if (validationErrors.size() != 0) {
            throw new InvalidObjectException("Invalid Author Create", validationErrors);
        }

        Author create = authorMapper.modelFromCreateRequest(authorDto);
        Author saved = authorService.save(create);

        AuthorResponse authorResponse = authorMapper.responseFromModel(saved);

        return ResponseEntity.status(201).body(authorResponse);
    }

    @PatchMapping(value ="{authorId}")
    public ResponseEntity<AuthorResponse>updateAuthor(@PathVariable String authorId, @RequestBody AuthorUpdateRequest authorDto){
        Map<String, String> validationErrors = validator.validate( authorDto);
        if (validationErrors.size() != 0) {
            throw new InvalidObjectException("Invalid Author Update", validationErrors);
        }

        Author findAuthor = authorService.findById(authorId);
        authorMapper.updateModelFromDto(authorDto,findAuthor);
        Author saved = authorService.save(findAuthor);

        AuthorResponse authorResponse = authorMapper.responseFromModel(saved);

        return  ResponseEntity.status(200).body(authorResponse);

    }

    //@PutMapping(value = "/{authorId}/paperBooks")
   // public AuthorPaperBookResponse setAuthorsPaperBooks(@PathVariable String authorId, @RequestBody SetPaperBookRequest paperBooks) {

       // Set<UUID> authorsPaperBooks = authorService.setAuthorsPaperBooks(authorId,paperBooks.getSetPaperBooks());

        //AuthorPaperBookResponse result = AuthorPaperBookResponse.builder().AuthorPaperBookIds(authorsPaperBooks).build();

       // return result;
    //}

    //@PutMapping(value = "/{authorId}/eBooks")
    //public AuthorEBookResponse setAuthorsEbooks(@PathVariable String authorId, @RequestBody SetEBookRequest eBooks) {

        //Set<UUID> authorsEbooks = authorService.setAuthorsEbooks(authorId,eBooks.getSetEBooks());

       // AuthorEBookResponse result = AuthorEBookResponse.builder().AuthorEBookIds(authorsEbooks).build();

       // return result;
    //}


}
