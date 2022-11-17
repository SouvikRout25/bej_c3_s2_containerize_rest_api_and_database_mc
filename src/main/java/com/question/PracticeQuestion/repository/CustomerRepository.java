package com.question.PracticeQuestion.repository;

import com.question.PracticeQuestion.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Customer findByCustomerNameAndCustomerPassword(String customerName,String customerPassword);
}