package com.question.PracticeQuestion.service;

import com.question.PracticeQuestion.domain.Customer;
import com.question.PracticeQuestion.exception.CustomerAlreadyExistsException;
import com.question.PracticeQuestion.exception.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {
    Customer addCustomer(Customer customer) throws CustomerAlreadyExistsException;
    List<Customer> getAllCustomers();
    Customer findByCustomerNameAndCustomerPassword(String customerName,String customerPassword) throws CustomerNotFoundException;
    boolean deleteCustomer(int customerId) throws CustomerNotFoundException;
}
