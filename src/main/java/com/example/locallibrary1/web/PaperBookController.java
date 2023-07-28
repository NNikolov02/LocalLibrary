package com.example.locallibrary1.web;


import com.example.locallibrary1.dto.author.SetAuthorRequest;
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


    private final Integer Page_Size = 10;

    @GetMapping(name = "", produces = "application/json")
    public PaperBookApiPage<PaperBookResponse> getAllPaperBooks(
            @RequestParam(required = false, defaultValue = "0") Integer currPage) {

        // Update the method to use Page<PaperBookResponse> instead of Page<PaperBook>
        Page<PaperBookResponse> paperBooksPage = paperBookService.fetchAll(currPage, Page_Size)
                .map(paperBookMapper::responseFromModelOne);

        return new PaperBookApiPage<>(paperBooksPage);
    }

    @GetMapping(value ="/title/{paperBookTitle}")
    public ResponseEntity<PaperBookResponse>findByTitle(@PathVariable String paperBookTitle){

        PaperBook paperBook = paperBookService.findTitle(paperBookTitle);
        if (paperBook == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(paperBookMapper.responseFromModelOne(paperBook));
    }

    @GetMapping(value ="/genre/{paperBookGenre}")
    public ResponseEntity<List<PaperBookResponse>> findByGenre(@PathVariable String paperBookGenre){
        List<PaperBook> paperBooks = paperBookService.findGenre(paperBookGenre);
        for(PaperBook paperBook:paperBooks){
            if (paperBook == null) {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(paperBookMapper.responseFromModelList(paperBooks));

    }

    //@GetMapping(value ="{paperBookAuthor}")
   // public ResponseEntity<List<PaperBookResponse>> findByAuthor(@PathVariable String paperBookAuthor){

       // List<PaperBook> paperBooks = paperBookService.findAuthor(paperBookAuthor);
        //for(PaperBook paperBook:paperBooks){
           // if (paperBook == null) {
              //  return ResponseEntity.notFound().build();
            //}
        //}
       // return ResponseEntity.ok(paperBookMapper.responseFromModelList(paperBooks));

   // }
    @GetMapping(value ="{paperBookId}")
    public ResponseEntity<PaperBookResponse>findById(@PathVariable String paperBookId){

        PaperBook paperBook = paperBookService.findById(paperBookId);

        return ResponseEntity.ok(paperBookMapper.responseFromModelOne(paperBook));
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

        Set<UUID> paperBooksAuthors = paperBookService.setPaperBookAuthor(paperBookId,authors.getSetAuthors());

        PaperBookAuthorResponse result = PaperBookAuthorResponse.builder().PaperBookAuthorIds(paperBooksAuthors).build();

        return result;
    }






}
