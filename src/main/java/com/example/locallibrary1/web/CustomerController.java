package com.example.locallibrary1.web;

import com.example.locallibrary1.dto.CustomerDto1;
import com.example.locallibrary1.dto.customer.*;
import com.example.locallibrary1.dto.ebook.SetEBookRequest;
import com.example.locallibrary1.dto.paperbook.SetPaperBookRequest;
import com.example.locallibrary1.error.CustomerAlreadyExistException;
import com.example.locallibrary1.error.InvalidObjectException;
import com.example.locallibrary1.mapping.CustomerMapper;
import com.example.locallibrary1.model.Customer;
import com.example.locallibrary1.repository.VerificationTokenRepository;
import com.example.locallibrary1.service.CustomerService;
import com.example.locallibrary1.validation.ObjectValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private VerificationTokenRepository repository;


    @GetMapping(name = "", produces = "application/json")
    public CustomerApiPage<CustomerResponse> getAllCustomers(
            @RequestParam(required = false, defaultValue = "1") Integer currPage) {


        Page<CustomerResponse> customerPage = customerService.fetchAll(currPage - 1, 10).map(customerMapper::responseFromModel);

        for (CustomerResponse response : customerPage){
            response.setUrl("http://localhost:8084/library/customers/" + response.getEmail());
        }

        return new CustomerApiPage<>(customerPage);
    }
    //@PostMapping("/registration")
    //public ModelAndView registerCustomerAccount(
           // @ModelAttribute("customer") @Valid CustomerCreateRequest customerDto,
           // HttpServletRequest request, Errors errors) {
       // try {
            //Customer registered = customerService.registerNewCustomerAccount(customerDto);

           // String appUrl = request.getContextPath();
            //eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered,
                    //request.getLocale(), appUrl));

        //} catch (RuntimeException ex) {
            // More handling
        //}
        //return new ModelAndView("successRegister", "customer", customerDto);
    //}
    @GetMapping("/registration-success")
    public String registrationSuccess(Model model,@RequestParam ("token") String token) {
        if(repository.existsByToken(token)){
            return "successRegister";
        }
        //String verificationUrl = "http://localhost:8084/library/customers/verify-account";
        //model.addAttribute("verificationUrl", verificationUrl);
        return "not";
    }
    @GetMapping("/successRegister")
    public String showSuccessRegisterPage() {
        return "successRegister"; // This should match the template name
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

        customerResponse.setUrl("http://localhost:8084/library/customers/" + customerResponse.getEmail());

        return ResponseEntity.ok().body(customerResponse);
    }

    @DeleteMapping("/{customerEmail}")
    public void deleteByEmail(String email){
        customerService.deleteByEmail(email);
    }

    @PostMapping("/registration")
    public ResponseEntity<CustomerResponse> createUserAndRegister(
            @RequestBody @Valid CustomerCreateRequest customerDto,
            HttpServletRequest request, Errors errors) {
        Map<String, String> validationErrors = validator.validate(customerDto);
        if (validationErrors.size() != 0) {
            throw new InvalidObjectException("Invalid Customer Create", validationErrors);
        }

        Customer create = customerMapper.modelFromCreateRequest(customerDto);
        Customer saved = customerService.save(create);

        Customer registered = customerService.registerNewCustomerAccount(customerDto);

        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered,
                request.getLocale(), appUrl));

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
