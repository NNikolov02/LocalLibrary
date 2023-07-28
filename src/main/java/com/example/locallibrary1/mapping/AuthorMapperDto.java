package com.example.locallibrary1.mapping;

import com.example.locallibrary1.dto.AuthorDto;
import com.example.locallibrary1.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface  AuthorMapperDto {

    AuthorDto modelRoDto(Author author);

    Author dtoModel(Author authorDto);
}
