package com.example.locallibrary1.mapping;

import com.example.locallibrary1.dto.AuthorDto;
import com.example.locallibrary1.dto.CustomerDto;
import com.example.locallibrary1.model.Author;
import com.example.locallibrary1.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface  CustomerMapperDto {

    CustomerDto modelRoDto(Customer customer);

    Customer dtoModel(Customer customerDto);
}