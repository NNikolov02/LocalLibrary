package com.example.locallibrary1.mapping;


import com.example.locallibrary1.dto.customer.CustomerCreateRequest;
import com.example.locallibrary1.dto.customer.CustomerResponse;
import com.example.locallibrary1.dto.customer.CustomerUpdateRequest;
import com.example.locallibrary1.dto.paperbook.PaperBookCreateRequest;
import com.example.locallibrary1.dto.paperbook.PaperBookResponse;
import com.example.locallibrary1.dto.paperbook.PaperBookUpdateReqeust;
import com.example.locallibrary1.model.Customer;
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
public interface PaperBookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authors",  ignore = true)
    @Mapping(target = "customer", ignore = true)
    PaperBook modelFromCreateRequest(PaperBookCreateRequest paperBookCreateDto);

    PaperBookResponse responseFromModelOne(PaperBook paperBook);
    List<PaperBookResponse> responseFromModelList(List<PaperBook> paperBooks);

    @Mapping(target = "authors",  ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "title",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "genre",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "summary", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "ISBN", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "numberOfCopies", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "totalNumber", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateModelFromDto(PaperBookUpdateReqeust paperBookUpdateDto, @MappingTarget PaperBook paperBook);
    List<PaperBookResponse> listOfModelToListOfDto(List<PaperBook> paperBooks);

    List<PaperBookResponse> listOfModelToListOfDto(Iterable<PaperBook> all);

}
