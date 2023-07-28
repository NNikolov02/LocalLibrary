package com.example.locallibrary1.web;


import com.example.locallibrary1.dto.author.SetAuthorRequest;
import com.example.locallibrary1.dto.ebook.*;
import com.example.locallibrary1.dto.paperbook.*;
import com.example.locallibrary1.error.InvalidObjectException;
import com.example.locallibrary1.mapping.EBookMapper;
import com.example.locallibrary1.model.EBook;
import com.example.locallibrary1.model.PaperBook;
import com.example.locallibrary1.service.EbookService;
import com.example.locallibrary1.validation.ObjectValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/library/eBooks")
@AllArgsConstructor
public class EBookController {



        @Autowired
        private EBookMapper eBookMapper;

        @Autowired
        private EbookService ebookService;

        @Autowired
        private ObjectValidator validator;


        private final Integer Page_Size = 10;

        @GetMapping(name = "", produces = "application/json")
        public EBookApiPage<EBookResponse> getAllPaperBooks(
                @RequestParam(required = false, defaultValue = "0") Integer currPage) {


            Page<EBookResponse> eBooksPage =ebookService.fetchAll(currPage, Page_Size)
                    .map(eBookMapper::responseFromModelOne);

            return new EBookApiPage<>(eBooksPage);
        }

        @GetMapping(value ="/{eBookTitle}")
        public ResponseEntity<EBookResponse>findByTitle(@PathVariable String eBookTitle){

            EBook eBook = ebookService.findTitle(eBookTitle);
            if (eBook == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(eBookMapper.responseFromModelOne(eBook));
        }
////////////
        @GetMapping(value ="{eBookGenre}")
        public ResponseEntity<List<EBookResponse>> findByGenre(@PathVariable String eBookGenre){
           List<EBook> eBooks = ebookService.findGenre(eBookGenre);
            for(EBook eBook:eBooks){
                if (eBook == null) {
                    return ResponseEntity.notFound().build();
                }
            }
            return ResponseEntity.ok(eBookMapper.responseFromModelList(eBooks));

        }

        //@GetMapping(value ="{eBookAuthor}")
        //public ResponseEntity<List<EBookResponse>> findByAuthor(@PathVariable String eBookAuthor){

            //List<EBook> eBooks =ebookService.findAuthors(eBookAuthor);
            //for(EBook eBook:eBooks){
               // if (eBook == null) {
                    //return ResponseEntity.notFound().build();
               // }
           // }
          //  return ResponseEntity.ok(eBookMapper.responseFromModelList(eBooks));

       // }
        @GetMapping(value ="{eBookId}")
        public ResponseEntity<EBookResponse>findById(@PathVariable String eBookId){

            EBook eBook =ebookService.findById(eBookId);

            return ResponseEntity.ok(eBookMapper.responseFromModelOne(eBook));
        }

        @DeleteMapping(value ="{eBookId}")
        public void deleteById(@PathVariable String eBookId){
            ebookService.deleteById(eBookId);
        }

        @PostMapping(value ="")
        public ResponseEntity<EBookResponse>createEBook(@RequestBody EBookCreateRequest eBookDto){

            Map<String, String> validationErrors = validator.validate( eBookDto);
            if (validationErrors.size() != 0) {
                throw new InvalidObjectException("Invalid eBook Create", validationErrors);
            }

            EBook create = eBookMapper.modelFromCreateRequest( eBookDto);

            EBook save = ebookService.save(create);

            EBookResponse eBookResponse = eBookMapper.responseFromModelOne(save);

            return ResponseEntity.status(201).body(eBookResponse);

        }

        @PatchMapping(value ="{eBookId}")
        public ResponseEntity<EBookResponse>updateEBook(@PathVariable String eBookId,@RequestBody EBookUpdateRequest eBookDto){
            Map<String, String> validationErrors = validator.validate(eBookDto);
            if (validationErrors.size() != 0) {
                throw new InvalidObjectException("Invalid eBook Update", validationErrors);
            }
            EBook eBook = ebookService.findById(eBookId);
            eBookMapper.updateModelFromDto( eBookDto,eBook);

            EBook save = ebookService.save(eBook);

            EBookResponse eBookResponse = eBookMapper.responseFromModelOne(save);

            return  ResponseEntity.status(200).body(eBookResponse);

        }

        @PutMapping(value = "/{eBookId}/authors")
        public EBookAuthorResponse setAllEBooksAuthors(@PathVariable String eBookId, @RequestBody SetAuthorRequest authors) {

            Set<UUID>eBooksAuthors = ebookService.setEbookAuthor(eBookId,authors.getSetAuthors());

            EBookAuthorResponse result =  EBookAuthorResponse.builder().EBookAuthorIds(eBooksAuthors).build();

            return result;
        }






    }
