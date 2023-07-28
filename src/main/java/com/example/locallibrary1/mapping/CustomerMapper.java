package com.example.locallibrary1.mapping;


import com.example.locallibrary1.dto.author.AuthorCreateRequest;
import com.example.locallibrary1.dto.author.AuthorResponse;
import com.example.locallibrary1.dto.author.AuthorUpdateRequest;
import com.example.locallibrary1.dto.customer.CustomerCreateRequest;
import com.example.locallibrary1.dto.customer.CustomerResponse;
import com.example.locallibrary1.dto.customer.CustomerUpdateRequest;
import com.example.locallibrary1.model.Author;
import com.example.locallibrary1.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

@Component
@Mapper(uses = {EBookMapperDto.class, PaperBookMapperDto.class})
public interface CustomerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "paperBooks", ignore = true)
    Customer modelFromCreateRequest(CustomerCreateRequest customerCreateDto);

    CustomerResponse responseFromModel(Customer customer);

    @Mapping(target = "paperBooks", ignore = true)
    @Mapping(target = "name",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "age",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "gender", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "country", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "address", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "city", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "email", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateModelFromDto(CustomerUpdateRequest customerUpdateDto, @MappingTarget Customer customer);
}
