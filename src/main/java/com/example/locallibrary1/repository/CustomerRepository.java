package com.example.locallibrary1.repository;

import com.example.locallibrary1.model.Customer;
import com.example.locallibrary1.model.EBook;
import com.example.locallibrary1.model.PaperBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, UUID> {

    Customer findCustomerByEmail(String email);
    void deleteAllByEmail(String email);
    //default ArrayList<Customer> borrow(PaperBook paperBook){

       // if (!paperBook.isBorrowed())// {
            //if (car.isAvailable()) {
               // car.setAvailable(false); // Set the car as unavailable when rented
               // car.setRenter(customer); // Set the renter to the customer who rented the car
                //customer.setRented(true);
              //  System.out.println("Car rented successfully!");
           // } else {
              //  System.out.println("The car is not available for rent.");
           // }
      //  } else {
            //System.out.println("Customer has already rented a car!");
      //  }

    //}
}