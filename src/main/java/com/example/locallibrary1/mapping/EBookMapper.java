package com.example.locallibrary1.mapping;


import com.example.locallibrary1.dto.ebook.EBookCreateRequest;
import com.example.locallibrary1.dto.ebook.EBookResponse;
import com.example.locallibrary1.dto.ebook.EBookUpdateRequest;
import com.example.locallibrary1.dto.paperbook.PaperBookCreateRequest;
import com.example.locallibrary1.dto.paperbook.PaperBookResponse;
import com.example.locallibrary1.dto.paperbook.PaperBookUpdateReqeust;
import com.example.locallibrary1.model.Author;
import com.example.locallibrary1.model.EBook;
import com.example.locallibrary1.model.PaperBook;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Mapper(uses = {AuthorMapperDto.class, CustomerMapperDto.class})
public interface EBookMapper {


    EBook modelFromCreateRequest(EBookCreateRequest eBookCreateDto);

    EBookResponse responseFromModelOne(EBook eBook);
    List< EBookResponse> responseFromModelList(List<EBook> paperBooks);


    @Mapping(target = "title",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "genre",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "summary", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "ISBN", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "linkForReading", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "linkForDownloading", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateModelFromDto(EBookUpdateRequest eBookUpdateDto, @MappingTarget EBook eBook);


    public static String authorUrlFromEbook(Author author){


        if(author != null){

            return "http://localhost:8084/library/authors/" + author.getId();

        }

        return null;
    }

}
