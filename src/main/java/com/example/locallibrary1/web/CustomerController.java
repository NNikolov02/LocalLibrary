package com.example.locallibrary1.web;

import com.example.locallibrary1.dto.author.AuthorApiPage;
import com.example.locallibrary1.dto.author.AuthorResponse;
import com.example.locallibrary1.dto.customer.*;
import com.example.locallibrary1.dto.ebook.SetEBookRequest;
import com.example.locallibrary1.dto.paperbook.SetPaperBookRequest;
import com.example.locallibrary1.error.InvalidObjectException;
import com.example.locallibrary1.mapping.CustomerMapper;
import com.example.locallibrary1.model.Customer;
import com.example.locallibrary1.service.CustomerService;
import com.example.locallibrary1.validation.ObjectValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/library/customers")
@AllArgsConstructor
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private ObjectValidator validator;


    @GetMapping(name = "", produces = "application/json")
    public CustomerApiPage<CustomerResponse> getAllCustomers(
            @RequestParam(required = false, defaultValue = "1") Integer currPage) {


        Page<CustomerResponse> customerPage = customerService.fetchAll(currPage - 1, 10).map(customerMapper::responseFromModel);

        for (CustomerResponse response : customerPage){
            response.setUrl("http://localhost:8084/library/customers/email" + response.getEmail());
        }

        return new CustomerApiPage<>(customerPage);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse>findById(@PathVariable String customerId){

        Customer customer = customerService.findById(customerId);
        CustomerResponse customerResponse = customerMapper.responseFromModel(customer);

        customerResponse.setUrl("http://localhost:8084/library/customers/email" + customerResponse.getEmail());

        return ResponseEntity.ok().body(customerResponse);

    }

    @GetMapping("/email/{customerEmail}")
    public ResponseEntity<CustomerResponse>findByEmail(@PathVariable String customerEmail){

        Customer customer = customerService.findCustomer(customerEmail);
        CustomerResponse customerResponse = customerMapper.responseFromModel(customer);

        customerResponse.setUrl("http://localhost:8084/library/customers/email" + customerResponse.getEmail());

        return ResponseEntity.ok().body(customerResponse);
    }

    @DeleteMapping("/{customerEmail}")
    public void deleteByEmail(String email){
        customerService.deleteByEmail(email);
    }

    @PostMapping("")
    public ResponseEntity<CustomerResponse>createUser(@RequestBody CustomerCreateRequest customerDto){
        Map<String, String> validationErrors = validator.validate(customerDto);
        if (validationErrors.size() != 0) {
            throw new InvalidObjectException("Invalid Customer Create", validationErrors);
        }

        Customer create= customerMapper.modelFromCreateRequest(customerDto);

        Customer saved = customerService.save(create);

        CustomerResponse customerResponse = customerMapper.responseFromModel(saved);

        return ResponseEntity.status(201).body(customerResponse);

    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<CustomerResponse>updateUser(@PathVariable String customerId, CustomerUpdateRequest customerDto){
        Map<String, String> validationErrors = validator.validate(customerDto);
        if (validationErrors.size() != 0) {
            throw new InvalidObjectException("Invalid Customer Update", validationErrors);

        }
        Customer find = customerService.findById(customerId);
        customerMapper.updateModelFromDto(customerDto,find);

        Customer saved = customerService.save(find);

        CustomerResponse customerResponse = customerMapper.responseFromModel(saved);


        return ResponseEntity.status(202).body(customerResponse);

    }
    @PutMapping(value = "/{customerId}/paperBooks")
    public CustomerPaperBookResponse setAuthorsPaperBooks(@PathVariable String customerId, @RequestBody SetPaperBookRequest paperBooks) {

        Set<UUID> customersPaperBooks = customerService.setCustomersPaperBooks(customerId,paperBooks.getSetPaperBooks());

        CustomerPaperBookResponse result = CustomerPaperBookResponse.builder().CustomerPaperBookIds(customersPaperBooks).build();

        return result;
    }

    @PutMapping(value = "/{customerId}/eBooks")
    public CustomerEBookResponse setCustomersEbooks(@PathVariable String customerId, @RequestBody SetEBookRequest eBooks) {

        Set<UUID> customersEbooks = customerService.setCustomersEbooks(customerId,eBooks.getSetEBooks());

        CustomerEBookResponse result = CustomerEBookResponse.builder().CustomerEBookIds(customersEbooks).build();

        return result;
    }


}
