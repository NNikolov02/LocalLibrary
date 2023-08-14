package com.example.locallibrary1.mapping;


import com.example.locallibrary1.dto.author.AuthorCreateRequest;
import com.example.locallibrary1.dto.author.AuthorResponse;
import com.example.locallibrary1.dto.author.AuthorUpdateRequest;
import com.example.locallibrary1.dto.customer.CustomerCreateRequest;
import com.example.locallibrary1.dto.customer.CustomerResponse;
import com.example.locallibrary1.dto.customer.CustomerUpdateRequest;
import com.example.locallibrary1.model.Author;
import com.example.locallibrary1.model.Customer;
import com.example.locallibrary1.model.EBook;
import com.example.locallibrary1.model.PaperBook;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

@Component
@Mapper(uses = {EBookMapperDto.class, PaperBookMapperDto.class})
public interface CustomerMapper {


    Customer modelFromCreateRequest(CustomerCreateRequest customerCreateDto);

    CustomerResponse responseFromModel(Customer customer);


    @Mapping(target = "name",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "age",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "gender", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "country", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "address", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "city", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "email", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateModelFromDto(CustomerUpdateRequest customerUpdateDto, @MappingTarget Customer customer);

    public static String eBooksUrlFromCustomer(EBook eBook){


        if(eBook != null){

            return "http://localhost:8084/library/eBooks/" + eBook.getId();

        }

        return null;
    }

    public static String paperBooksUrlFromCustomer(PaperBook paperBook) {


        if (paperBook != null) {

            return "http://localhost:8084/library/paperBook/" + paperBook.getId();

        }

        return null;
    }



}
