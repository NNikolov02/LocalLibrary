package com.example.locallibrary1.mapping;


import com.example.locallibrary1.dto.author.AuthorCreateRequest;
import com.example.locallibrary1.dto.author.AuthorResponse;
import com.example.locallibrary1.dto.author.AuthorUpdateRequest;
import com.example.locallibrary1.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

@Component
@Mapper(uses = {EBookMapperDto.class, PaperBookMapperDto.class})
public interface AuthorMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "EBooks",  ignore = true)
    @Mapping(target = "paperBooks", ignore = true)
    Author modelFromCreateRequest(AuthorCreateRequest authorCreateDto);

    AuthorResponse responseFromModel(Author author);
    @Mapping(target = "EBooks",  ignore = true)
    @Mapping(target = "paperBooks", ignore = true)
    @Mapping(target = "name",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "country",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "dateOfBirth", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "dateOfDeath", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateModelFromDto(AuthorUpdateRequest authorUpdateDto, @MappingTarget Author author);
}
