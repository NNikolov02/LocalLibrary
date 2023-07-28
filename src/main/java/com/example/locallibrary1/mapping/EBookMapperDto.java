package com.example.locallibrary1.mapping;

import com.example.locallibrary1.dto.AuthorDto;
import com.example.locallibrary1.dto.EBookDto;
import com.example.locallibrary1.model.Author;
import com.example.locallibrary1.model.EBook;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface  EBookMapperDto {

    EBookDto modelRoDto(EBook eBook);

    EBook dtoModel(EBook eBookDto);
}