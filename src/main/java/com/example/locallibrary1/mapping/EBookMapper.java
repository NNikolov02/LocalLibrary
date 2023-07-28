package com.example.locallibrary1.mapping;


import com.example.locallibrary1.dto.ebook.EBookCreateRequest;
import com.example.locallibrary1.dto.ebook.EBookResponse;
import com.example.locallibrary1.dto.ebook.EBookUpdateRequest;
import com.example.locallibrary1.dto.paperbook.PaperBookCreateRequest;
import com.example.locallibrary1.dto.paperbook.PaperBookResponse;
import com.example.locallibrary1.dto.paperbook.PaperBookUpdateReqeust;
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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authors",  ignore = true)
    @Mapping(target = "customer", ignore = true)
    EBook modelFromCreateRequest(EBookCreateRequest eBookCreateDto);

    EBookResponse responseFromModelOne(EBook eBook);
    List< EBookResponse> responseFromModelList(List<EBook> paperBooks);

    @Mapping(target = "authors",  ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "title",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "genre",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "summary", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "ISBN", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "link", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateModelFromDto(EBookUpdateRequest eBookUpdateDto, @MappingTarget EBook eBook);

    List<EBookResponse> listOfModelToListOfDto(List<EBook> eBooks);

    List<EBookResponse> listOfModelToListOfDto(Iterable<EBook> all);
}
