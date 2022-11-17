package com.question.PracticeQuestion.service;

import com.question.PracticeQuestion.domain.Customer;

import java.util.Map;

public interface SecurityTokenGenerator {
    Map<String,String> generateToken(Customer customer);
}