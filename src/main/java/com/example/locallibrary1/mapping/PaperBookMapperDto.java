package com.example.locallibrary1.mapping;

import com.example.locallibrary1.dto.AuthorDto;
import com.example.locallibrary1.dto.PaperBookDto;
import com.example.locallibrary1.model.Author;
import com.example.locallibrary1.model.PaperBook;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface  PaperBookMapperDto {

    PaperBookDto modelRoDto(PaperBook paperBook);

    PaperBook dtoModel(PaperBook paperBookDto);
}