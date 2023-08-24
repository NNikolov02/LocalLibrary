package com.example.locallibrary1.mapping;

import com.example.locallibrary1.dto.borrowinghistory.BorrowingHistoryCreateRequest;
import com.example.locallibrary1.dto.borrowinghistory.BorrowingHistoryResponse;
import com.example.locallibrary1.dto.customer.CustomerCreateRequest;
import com.example.locallibrary1.dto.customer.CustomerResponse;
import com.example.locallibrary1.dto.customer.CustomerUpdateRequest;
import com.example.locallibrary1.model.BorrowingHistory;
import com.example.locallibrary1.model.Customer;
import com.example.locallibrary1.model.EBook;
import com.example.locallibrary1.model.PaperBook;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Mapper(uses = {EBookMapperDto.class, PaperBookMapperDto.class,CustomerMapperDto.class})
public interface BorrowingHistoryMapper {





    BorrowingHistory modelFromCreateRequest(BorrowingHistoryCreateRequest borrowCreateDto);


    BorrowingHistoryResponse responseFromModel(BorrowingHistory borrowingHistory);




    //void updateModelFromDto(CustomerUpdateRequest customerUpdateDto, @MappingTarget Customer customer);

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
    public static String customersUrlFromCustomer(Customer customer) {


        if (customer != null) {

            return "http://localhost:8084/library/customers/" + customer.getEmail();

        }

        return null;
    }


}
