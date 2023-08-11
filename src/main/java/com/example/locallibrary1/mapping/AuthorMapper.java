package com.example.locallibrary1.mapping;


import com.example.locallibrary1.dto.author.AuthorCreateRequest;
import com.example.locallibrary1.dto.author.AuthorResponse;
import com.example.locallibrary1.dto.author.AuthorUpdateRequest;
import com.example.locallibrary1.model.Author;
import com.example.locallibrary1.model.EBook;
import com.example.locallibrary1.model.PaperBook;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

@Component
@Mapper(uses = {EBookMapperDto.class, PaperBookMapperDto.class})
public interface AuthorMapper {

    Author modelFromCreateRequest(AuthorCreateRequest authorCreateDto);

    AuthorResponse responseFromModel(Author author);

    @Mapping(target = "name",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "country",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "dateOfBirth", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "dateOfDeath", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateModelFromDto(AuthorUpdateRequest authorUpdateDto, @MappingTarget Author author);



    public static String eBooksUrlFromAuthor(EBook eBook){


        if(eBook != null){

            return "http://localhost:8084/library/eBooks/" + eBook.getId();

        }

        return null;
    }

    public static String paperBooksUrlFromAuthor(PaperBook paperBook){


        if(paperBook != null){

            return "http://localhost:8084/library/paperBook/" + paperBook.getId();

        }

        return null;
    }
}
