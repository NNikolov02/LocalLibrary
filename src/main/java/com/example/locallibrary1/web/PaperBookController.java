package com.example.locallibrary1.web;


import com.example.locallibrary1.dto.author.SetAuthorRequest;
import com.example.locallibrary1.dto.ebook.EBookAuthorResponse;
import com.example.locallibrary1.dto.paperbook.*;
import com.example.locallibrary1.error.InvalidObjectException;
import com.example.locallibrary1.mapping.PaperBookMapper;
import com.example.locallibrary1.model.PaperBook;
import com.example.locallibrary1.service.PaperBookService;
import com.example.locallibrary1.validation.ObjectValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/library/paperBooks")
@AllArgsConstructor
public class PaperBookController {

    @Autowired
    private PaperBookMapper paperBookMapper;

    @Autowired
    private PaperBookService paperBookService;

    @Autowired
    private ObjectValidator validator;



    @GetMapping(name = "", produces = "application/json")
    public PaperBookApiPage<PaperBookResponse> getAllPaperBooks(
            @RequestParam(required = false, defaultValue = "1") Integer currPage) {
        int totalNumber = 0;

        Page<PaperBookResponse> paperBookPage = paperBookService.fetchAll(currPage - 1, 10).map(paperBookMapper::responseFromModelOne);

        for (PaperBookResponse response : paperBookPage){
            response.setUrl("http://localhost:8084/library/paperBooks/" + response.getId());
            response.setTotalNumber(totalNumber);
            totalNumber++;
        }

        return new PaperBookApiPage<>(paperBookPage);
    }

    @GetMapping(value ="/title/{paperBookTitle}")
    public ResponseEntity<PaperBookResponse>findByTitle(@PathVariable String paperBookTitle){

        PaperBook paperBook = paperBookService.findTitle(paperBookTitle);
        if (paperBook == null) {
            return ResponseEntity.notFound().build();
        }

        PaperBookResponse paperBookResponse = paperBookMapper.responseFromModelOne(paperBook);
        paperBookResponse.setUrl("http://localhost:8084/library/paperBooks/" + paperBookResponse.getId());



        return ResponseEntity.ok().body(paperBookResponse);
    }

    @GetMapping(value ="/genre/{paperBookGenre}")
    public ResponseEntity<List<PaperBookResponse>> findByGenre(@PathVariable String paperBookGenre){
        List<PaperBook> paperBooks = paperBookService.findGenre(paperBookGenre);
        for(PaperBook paperBook:paperBooks){
            if (paperBook == null) {
                return ResponseEntity.notFound().build();
            }
        }
        List<PaperBookResponse> paperBookResponse = paperBookMapper.responseFromModelList(paperBooks);

        for (PaperBookResponse response : paperBookResponse){
            response.setUrl("http://localhost:8084/library/paperBooks/" + response.getId());
        }
        return ResponseEntity.ok().body(paperBookResponse);

    }

    @GetMapping(value ="/author/{paperBookAuthor}")
    public ResponseEntity<List<PaperBookResponse>> findByAuthor(@PathVariable String paperBookAuthor){

        List<PaperBook> paperBooks = paperBookService.findAuthor(paperBookAuthor);
        for(PaperBook paperBook:paperBooks){
            if (paperBook == null) {
                return ResponseEntity.notFound().build();
            }
        }
        List<PaperBookResponse> paperBookResponse = paperBookMapper.responseFromModelList(paperBooks);

        for (PaperBookResponse response : paperBookResponse){
            response.setUrl("http://localhost:8084/library/paperBooks/" + response.getId());
        }
        return ResponseEntity.ok().body(paperBookResponse);

    }
    @GetMapping(value ="{paperBookId}")
    public ResponseEntity<PaperBookResponse>findById(@PathVariable String paperBookId){

        PaperBook paperBook = paperBookService.findById(paperBookId);

        PaperBookResponse paperBookResponse = paperBookMapper.responseFromModelOne(paperBook);
        paperBookResponse.setUrl("http://localhost:8084/library/paperBooks/" + paperBookResponse.getId());



        return ResponseEntity.ok().body(paperBookResponse);
    }

    @DeleteMapping(value ="{paperBookId}")
    public void deleteById(@PathVariable String paperBookId){
        paperBookService.deleteById(paperBookId);
    }

    @PostMapping(value ="")
    public ResponseEntity<PaperBookResponse>createPaperBook(@RequestBody PaperBookCreateRequest paperBookDto){

        Map<String, String> validationErrors = validator.validate(paperBookDto);
        if (validationErrors.size() != 0) {
            throw new InvalidObjectException("Invalid PaperBook Create", validationErrors);
        }

        PaperBook create = paperBookMapper.modelFromCreateRequest(paperBookDto);

        PaperBook save = paperBookService.save(create);

        PaperBookResponse paperBookResponse = paperBookMapper.responseFromModelOne(save);

        return ResponseEntity.status(201).body(paperBookResponse);

    }

    @PatchMapping(value ="{paperBookId}")
    public ResponseEntity<PaperBookResponse>updatePaperBook(@PathVariable String paperBookId,@RequestBody PaperBookUpdateReqeust paperBookDto){
        Map<String, String> validationErrors = validator.validate(paperBookDto);
        if (validationErrors.size() != 0) {
            throw new InvalidObjectException("Invalid PaperBook Update", validationErrors);
        }
        PaperBook paperBook = paperBookService.findById(paperBookId);
        paperBookMapper.updateModelFromDto(paperBookDto,paperBook);

        PaperBook save = paperBookService.save(paperBook);

        PaperBookResponse paperBookResponse = paperBookMapper.responseFromModelOne(save);

        return  ResponseEntity.status(200).body(paperBookResponse);

    }

    @PutMapping(value = "/{paperBookId}/authors")
    public PaperBookAuthorResponse setAllPaperBooksAuthors(@PathVariable String paperBookId, @RequestBody SetAuthorRequest authors) {

        Set<UUID>paperBooksAuthors = paperBookService.setPaperBookAuthor(paperBookId,authors.getSetAuthors());

        PaperBookAuthorResponse result =  PaperBookAuthorResponse.builder().PaperBookAuthorIds(paperBooksAuthors).build();

        return result;
    }








}
