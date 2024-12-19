package com.backend.service;
import java.util.List;

import org.springframework.data.domain.Page;

import com.backend.dto.CustomerDTO;
import com.backend.dto.CustomerProjectionDTO;
import com.backend.dto.UserLoanDTO;
import com.backend.exception.CommonExceptions;

public interface CustomerService{
    public CustomerDTO getCustomer(Long phoneNumber) throws CommonExceptions;
    public void insertCustomer(CustomerDTO customerDTO) throws CommonExceptions;
    public List<CustomerDTO> getAllCustomers() throws CommonExceptions;
    public CustomerDTO updateCustomer(long phoneNumber, String name) throws CommonExceptions ;
    public void deleteCustomer(long phoneNumber) throws CommonExceptions;
    public List<CustomerDTO> filterbyageandgender() throws CommonExceptions;
    public List<CustomerDTO> filterbyage() throws CommonExceptions;
    public List<CustomerDTO> filterbygender() throws CommonExceptions;
    public List<CustomerDTO> filterbyAddressNull() throws CommonExceptions;
    public List<CustomerDTO> filterbyNameLike(String name) throws CommonExceptions;
    public List<CustomerDTO> filterbyAgeOrderByPlanIdDesc() throws CommonExceptions;

    public List<CustomerDTO> filterbyAgeRangeAndGender() throws CommonExceptions;

    public List<CustomerProjectionDTO> filterByNameandNumber() throws CommonExceptions;
    public List<CustomerProjectionDTO> filterbyPlanId() throws CommonExceptions;
    public Page<CustomerProjectionDTO> filterByPlanIdPages(String planId, int page, int size) throws CommonExceptions ;
    public List<UserLoanDTO> getUserLoanDetails() throws CommonExceptions;
    



    

}
