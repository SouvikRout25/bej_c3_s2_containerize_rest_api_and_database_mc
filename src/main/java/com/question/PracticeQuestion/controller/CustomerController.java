package com.question.PracticeQuestion.controller;

import com.question.PracticeQuestion.domain.Customer;
import com.question.PracticeQuestion.exception.CustomerAlreadyExistsException;
import com.question.PracticeQuestion.exception.CustomerNotFoundException;
import com.question.PracticeQuestion.service.CustomerService;
import com.question.PracticeQuestion.service.SecurityTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CustomerController {
    private CustomerService customerService;
    private SecurityTokenGenerator securityTokenGenerator;
    @Autowired
    public CustomerController(CustomerService customerService, SecurityTokenGenerator securityTokenGenerator) {
        this.customerService = customerService;
        this.securityTokenGenerator = securityTokenGenerator;
    }
    @PostMapping("/register")
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) throws CustomerAlreadyExistsException {
        ResponseEntity responseEntity = null;
        try{
            Customer customer1 = customerService.addCustomer(customer);
            responseEntity = new ResponseEntity<>(customer1, HttpStatus.CREATED);
        }catch (CustomerAlreadyExistsException e){
            throw new CustomerAlreadyExistsException();
        }catch (Exception e){
            responseEntity=new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginCustomer(@RequestBody Customer customer) throws CustomerAlreadyExistsException {
        Map<String,String> map = null;
        try{
            Customer customer1 = customerService.findByCustomerNameAndCustomerPassword(customer.getCustomerName(), customer.getCustomerPassword());
            if(customer1.getCustomerName().equals(customer.getCustomerName())){
                map= securityTokenGenerator.generateToken(customer);
            }
            return new ResponseEntity<>(map,HttpStatus.OK);
        }catch (CustomerNotFoundException e){
            throw new CustomerAlreadyExistsException();
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Try after sometime!!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/custdata/customers")
    public ResponseEntity<?> getAllCustomers(){
        List<Customer> customerList = customerService.getAllCustomers();
        return new ResponseEntity<>(customerList,HttpStatus.OK);
    }
    @DeleteMapping("/custdata/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable int customerId) throws CustomerNotFoundException {
        ResponseEntity responseEntity = null;
        try{
            customerService.deleteCustomer(customerId);
            responseEntity = new ResponseEntity<>("successfully deleted one record",HttpStatus.OK);
        }catch (CustomerNotFoundException e){
            throw new CustomerNotFoundException();
        }catch (Exception e){
            responseEntity=new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}