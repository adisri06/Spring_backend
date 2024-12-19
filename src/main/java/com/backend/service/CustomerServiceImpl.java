package com.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.backend.ReactBackendAppApplication;
import com.backend.domain.Customer;
import com.backend.dto.CustomerDTO;
import com.backend.dto.CustomerProjectionDTO;
import com.backend.dto.UserLoanDTO;
import com.backend.exception.CommonExceptions;
import com.backend.exception.ExceptionMessages;
import com.backend.repository.CustomerRepo;
import com.backend.repository.LoanRepository;

import jakarta.transaction.Transactional;

@Service("customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService {
    	public static final Log logger = LogFactory.getLog(ReactBackendAppApplication.class);


    @Autowired
    CustomerRepo customerRepo;
    @Autowired
	CustomerPageableService customerPageableService;

     @Autowired
    private LoanRepository loanRepository;


    @Override
    public List<UserLoanDTO> getUserLoanDetails() throws CommonExceptions {
        List<UserLoanDTO> loanDto = loanRepository.fetchUserLoanDetails();
        logger.info("values are    "+loanDto);
        // Handle case where the repository returns null or an empty list
        if (loanDto == null || loanDto.isEmpty()) {
            throw new CommonExceptions(ExceptionMessages.USER_LOAN_NOT_FOUND);
        } else {
            logger.info("User loan details fetched successfully");
            logger.info( loanDto);  // Log the actual loan details
            return loanDto;
        }
    }
    @Override
    public CustomerDTO getCustomer(Long phoneNumber) throws CommonExceptions {
    
    // Check if customer exists
        Optional<Customer> customer = customerRepo.findById(phoneNumber);
    Customer custEntity = new Customer();
    if (customer.isPresent()) {
        custEntity = customer.get();
    }
    return Customer.prepareCustomerDTO(custEntity);
    }
    @Override
        public void insertCustomer(CustomerDTO customerDTO) {
            try {
                // Check if customer exists
                if (getCustomer(customerDTO.getPhoneNumber()) != null) {
                    throw new CommonExceptions("Customer already exists");
                }
        
                // Insert the customer if not already present
                customerRepo.save(CustomerDTO.prepareCustomerEntity(customerDTO));
                logger.info("Customer inserted successfully: " + customerDTO.getName());
            } catch (CommonExceptions e) {
                logger.error("Error: " + e.getMessage());
            } catch (Exception e) {
                // General exception handling
                logger.error("An unexpected error occurred while inserting the customer: ", e);
            }
        }
    

    @Override
    public List<CustomerDTO> getAllCustomers() throws CommonExceptions {
        List<CustomerDTO> customerDTOList = new ArrayList<CustomerDTO>();

        Iterable<Customer> customerList = customerRepo.findAll();
        for(Customer customer : customerList) {
            customerDTOList.add(Customer.prepareCustomerDTO(customer));
        }
        return customerDTOList;
    }

    @Override
public CustomerDTO updateCustomer(long phoneNumber, String name) throws CommonExceptions {
    try {
        Optional<Customer> customerOptional = customerRepo.findById(phoneNumber);

        if (customerOptional.isPresent()) {
            // Customer found, update the details
            Customer customerEntity = customerOptional.get();
            customerEntity.setName(name);
            Customer updatedEntity = customerRepo.save(customerEntity);

            logger.info("Customer updated successfully");
            
            // Convert entity to DTO and return
            return new CustomerDTO(
                updatedEntity.getPhoneNumber(),
                updatedEntity.getName(),
                updatedEntity.getAge(),
                updatedEntity.getGender(),
                updatedEntity.getAddress(),
                updatedEntity.getPlanId()
            );
        } else {
            // Customer not found
            logger.warn("No customer found with phone number: " + phoneNumber);
            return null;
        }
    } catch (Exception e) {
        logger.error("Error occurred while updating customer", e);
        throw new CommonExceptions(ExceptionMessages.FAIL_MESSAGE, e);
    }
}
@Override
public void deleteCustomer(long phoneNumber) throws CommonExceptions {
    try {
        // Try to find the customer in the repository
        Optional<Customer> customer = customerRepo.findById(phoneNumber);
        
        if (customer.isPresent()) {
            // If customer exists, delete the customer
            Customer custentity = customer.get();
            customerRepo.delete(custentity);
            logger.info("Customer with phone number " + phoneNumber + " deleted successfully");
        } else {
            // If no customer is found, log and throw an exception
            logger.warn("Customer with phone number " + phoneNumber + " not found for deletion");
            throw new CommonExceptions(ExceptionMessages.CUSTOMER_NOT_FOUND);
        }
    } catch (Exception e) {
        // Catch any unexpected errors, log and rethrow as a custom exception
        logger.error("Error occurred while deleting customer with phone number " + phoneNumber, e);
        throw new CommonExceptions("Error occurred while deleting customer: " + e.getMessage(), e);
    }
}

    @Override
        public List<CustomerDTO> filterbyageandgender() throws CommonExceptions{   
            List<CustomerDTO> customerDTOList = new ArrayList<CustomerDTO>();
                 try{
                    List<Customer> customerList = customerRepo.findByAgeAndGender(20, 'M');
                    logger.info("Customer filtered successfully");
                    for(Customer customer : customerList) {
                        customerDTOList.add(Customer.prepareCustomerDTO(customer));
                    }
                 }catch(Exception e){
                    logger.error(ExceptionMessages.FAIL_MESSAGE);
                    throw new CommonExceptions(ExceptionMessages.FAIL_MESSAGE, e);
                 }
                 return customerDTOList;

        }

        @Override
        public List<CustomerDTO> filterbyage() throws CommonExceptions{   
            List<CustomerDTO> customerDTOList = new ArrayList<CustomerDTO>();
                 try{
                    List<Customer> customerList = customerRepo.findByAge(20);
                    logger.info("Customer filtered successfully");
                    for(Customer customer : customerList) {
                        customerDTOList.add(Customer.prepareCustomerDTO(customer));
                    }
                 }catch(Exception e){
                    logger.error(ExceptionMessages.FAIL_MESSAGE);
                    throw new CommonExceptions(ExceptionMessages.FAIL_MESSAGE, e);
                 }
                 return customerDTOList;

        }
        @Override
        public List<CustomerDTO> filterbygender() throws CommonExceptions{   
            List<CustomerDTO> customerDTOList = new ArrayList<CustomerDTO>();
                 try{
                    List<Customer> customerList = customerRepo.findByGender('M');
                    logger.info("Customer filtered successfully");
                    for(Customer customer : customerList) {
                        customerDTOList.add(Customer.prepareCustomerDTO(customer));
                    }
                 }catch(Exception e){
                    logger.error(ExceptionMessages.FAIL_MESSAGE);
                    throw new CommonExceptions(ExceptionMessages.FAIL_MESSAGE, e);
                 }
                 return customerDTOList;

        }
        @Override
        public List<CustomerDTO> filterbyAddressNull() throws CommonExceptions{   
            List<CustomerDTO> customerDTOList = new ArrayList<CustomerDTO>();
                 try{
                    List<Customer> customerList = customerRepo.findByAddressNull();
                    logger.info("Customer filtered successfully");
                    for(Customer customer : customerList) {
                        customerDTOList.add(Customer.prepareCustomerDTO(customer));
                    }
                 }catch(Exception e){
                    logger.error(ExceptionMessages.FAIL_MESSAGE);
                    throw new CommonExceptions(ExceptionMessages.FAIL_MESSAGE, e);
                 }
                 return customerDTOList;

        }
        @Override
        public List<CustomerDTO> filterbyNameLike(String name) throws CommonExceptions{   
            List<CustomerDTO> customerDTOList = new ArrayList<CustomerDTO>();
                 try{
                    List<Customer> customerList = customerRepo.findByNameLike(name);
                    logger.info("Customer filtered successfully");
                    for(Customer customer : customerList) {
                        customerDTOList.add(Customer.prepareCustomerDTO(customer));
                    }
                 }catch(Exception e){
                    logger.error(ExceptionMessages.FAIL_MESSAGE);
                    throw new CommonExceptions(ExceptionMessages.FAIL_MESSAGE, e);
                 }
                 return customerDTOList;

        }
        @Override
        public List<CustomerDTO> filterbyAgeOrderByPlanIdDesc() throws CommonExceptions{   
            List<CustomerDTO> customerDTOList = new ArrayList<CustomerDTO>();
                 try{
                    List<Customer> customerList = customerRepo.findByAgeOrderByPlanIdDesc(11);
                    logger.info("Customer filtered successfully");
                    for(Customer customer : customerList) {
                        customerDTOList.add(Customer.prepareCustomerDTO(customer));
                    }
                 }catch(Exception e){
                    logger.error(ExceptionMessages.FAIL_MESSAGE);
                    throw new CommonExceptions(ExceptionMessages.FAIL_MESSAGE, e);
                 }
                 return customerDTOList;

        }

        @Override
        public List<CustomerDTO> filterbyAgeRangeAndGender() throws CommonExceptions{   
            List<CustomerDTO> customerDTOList = new ArrayList<CustomerDTO>();
                 try{
                    List<Customer> customerList = customerRepo.findByAgeRangeAndGender(20,40,'M');
                    logger.info("Customer filtered successfully");
                    for(Customer customer : customerList) {
                        customerDTOList.add(Customer.prepareCustomerDTO(customer));
                    }
                 }catch(Exception e){
                    logger.error(ExceptionMessages.FAIL_MESSAGE);
                    throw new CommonExceptions(ExceptionMessages.FAIL_MESSAGE, e);
                 }
                 return customerDTOList;

        }

        @Override
        public List<CustomerProjectionDTO> filterbyPlanId() throws CommonExceptions{   
            List<CustomerProjectionDTO> customerDTOList = new ArrayList<CustomerProjectionDTO>();
                 try{
                    long phoneNumber = 8587848705L;
                    String Name = "Priya";
                    String planId = "1";

                    List<CustomerProjectionDTO> customerList1 = customerRepo.findByNameandNumber(Name,phoneNumber);
                    logger.info("\n Filter by Name and Number record: "+ customerList1.toString()+ "\n");
                    List<CustomerProjectionDTO> customerList = customerRepo.findByplanIDProjection(planId);
                    for(CustomerProjectionDTO customer : customerList) {
                        logger.info(customer + "\n");
                    }
                    logger.info("Customer filtered successfully");
                 }catch(Exception e){
                    logger.error(ExceptionMessages.FAIL_MESSAGE);
                    throw new CommonExceptions(ExceptionMessages.FAIL_MESSAGE, e);
                 }
                 return customerDTOList;

        }

    @Override
    public List<CustomerProjectionDTO> filterByNameandNumber() throws CommonExceptions {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Page<CustomerProjectionDTO> filterByPlanIdPages(String planId, int page, int size) throws CommonExceptions {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
