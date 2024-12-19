package com.backend.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.dto.CustomerDTO;
import com.backend.dto.CustomerPageRequestDTO;
import com.backend.dto.CustomerProjectionDTO;
import com.backend.dto.UserLoanDTO;
import com.backend.exception.CommonExceptions;
import com.backend.exception.ExceptionMessages;
import com.backend.service.CustomerPageableService;
import com.backend.service.CustomerServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;



@RestController
@EnableCaching
@RequestMapping("/customer")

public class CustomerController {
    	  public static final Log logger = LogFactory.getLog(CustomerController.class);

    // Parameterized logger using SLF4J
    public static final Logger  paramLogger = LoggerFactory.getLogger( CustomerController.class);
    @Autowired
    CustomerServiceImpl customerService;

	@Autowired
	CustomerPageableService customerPageableService;

    @Operation(summary = "Fetch all customer records", description = "Retrieve a list of all customer records from the database.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved customer records"),
        @ApiResponse(responseCode = "404", description = "No customer records found")
    })
    @GetMapping("/allCustomerRecords")
    public List<CustomerDTO> getAllCustomerRecords() throws CommonExceptions {
       try{
		List<CustomerDTO> customerList = customerService.getAllCustomers();
        if(customerList.isEmpty() ) {
            throw new CommonExceptions(ExceptionMessages.CUSTOMER_NOT_FOUND);
        }
		}catch(Exception e){
			logger.error(ExceptionMessages.FAIL_MESSAGE);
			throw new CommonExceptions(ExceptionMessages.FAIL_MESSAGE, e);
		}
        return customerService.getAllCustomers();}

        @Operation(summary = "Get customer by mobile number", description = "Retrieve customer details using their mobile number.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Customer found"),
        @ApiResponse(responseCode = "404", description = "Customer not found")
    })
        @GetMapping("/customerRecord/{mobileNumber}")
    public ResponseEntity<Object>getCustomer(@PathVariable Long mobileNumber) throws CommonExceptions {
        try {
            logger.info("Fetching customer with mobile number: " + mobileNumber);
            CustomerDTO customer = customerService.getCustomer(mobileNumber);

            if (customer == null || customer.getPhoneNumber() == null) {
                return ResponseEntity.status(HttpStatus.OK)
                                 .body(Map.of("message", "No customer found"));

            } else {
                logger.info(ExceptionMessages.CUSTOMER_FOUND);

            // Return 200 with customer data
            return ResponseEntity.status(HttpStatus.FOUND).body(customer);
            }
        } catch (Exception e) {
            logger.error(ExceptionMessages.FAIL_MESSAGE, e);
            throw new CommonExceptions(ExceptionMessages.FAIL_MESSAGE, e);
        }
    }
    @Operation(summary = "Filter customers by name", description = "Retrieve customers with names similar to the provided string.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Customers found"),
        @ApiResponse(responseCode = "404", description = "No customers found")
    })
    @GetMapping("/customerName/{name}")
    public ResponseEntity<Object>getCustomer(@PathVariable String name) throws CommonExceptions {
        try {
            logger.info("Fetching customer with name: " + name);
             List<CustomerDTO> customer = customerService.filterbyNameLike(name);

            if (customer == null|| customer.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK)
                                 .body(Map.of("message", "No customer found"));

            } else {
                logger.info(ExceptionMessages.CUSTOMER_FOUND);

            // Return 200 with customer data
            return ResponseEntity.status(HttpStatus.FOUND).body(customer);
            }
        } catch (Exception e) {
            logger.error(ExceptionMessages.FAIL_MESSAGE, e);
            throw new CommonExceptions(ExceptionMessages.FAIL_MESSAGE, e);
        }
    }
    @Operation(summary = "Insert customer", description = "Add a new customer to the database.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Customer inserted successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/insertCustomers")
public ResponseEntity<Object> insertCustomers(
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Customer details to be added",
        required = true,
        content = @Content(
            schema = @Schema(implementation = CustomerDTO.class)
        )
    ) @RequestBody CustomerDTO customers) {
    try {
        logger.info("Inserting customers: " + customers);

        customerService.insertCustomer(customers);

        logger.info(ExceptionMessages.INSERT_SUCCESS);

        return ResponseEntity.status(HttpStatus.OK).body(customers);
    } catch (Exception e) {
        logger.error(ExceptionMessages.FAIL_MESSAGE, e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(Map.of("error", ExceptionMessages.FAIL_MESSAGE));
    }
}

@Operation(summary = "Update customer details", description = "Update an existing customer's details.")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Customer updated successfully"),
    @ApiResponse(responseCode = "201", description = "Customer not found for updating")
})
@PutMapping("/updatecustomer")
    public ResponseEntity<Object> updateCustomer(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Customer details to be updated",
        required = true,
        content = @Content(
            schema = @Schema(implementation = CustomerDTO.class)
        )
    ) @RequestBody CustomerDTO customer) {
        try {
            Long phoneNumber = customer.getPhoneNumber();
            String name = customer.getName();
            logger.info("Updating customer with phone number: " + phoneNumber + " to name: " + name);

            CustomerDTO updatedCustomer = customerService.updateCustomer(phoneNumber, name);

            if (updatedCustomer == null) {
                // Record not found for updating
                logger.warn("No customer found with phone number: " + phoneNumber);
                return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "201",
                    "message", "No customer found to update"
                ));
            }

            // Successfully updated
            logger.info("Customer updated successfully: " + updatedCustomer);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "status", "200",
                "message", "Customer updated successfully",
                "updatedCustomer", updatedCustomer
            ));

        } catch (Exception e) {
            logger.error("Failed to update customer", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "status", "500",
                "message", "An error occurred while updating customer",
                "error", e.getMessage()
            ));
        }
    }

    @Operation(summary = "Delete customer", description = "Delete a customer by their phone number.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Customer deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Error during customer deletion")
    })
   @SecurityRequirement(name = "basicAuth")  // Use this to apply the security scheme
    @DeleteMapping("/deleteCustomer/{phoneNumber}")
     public ResponseEntity<Map<String, Object>> deleteCustomer(@PathVariable long phoneNumber) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Call service to delete customer
            customerService.deleteCustomer(phoneNumber);
            
            // Return success message if customer is deleted
            response.put("status", 200);
            response.put("message", "Customer deleted successfully");
            response.put("data", null);
            
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            logger.error("Error while deleting customer", e);
            
            // Return error message with custom status
            response.put("status", 400);
            response.put("message", "Error while deleting customer: Customer not found or deletion failed.");
            response.put("data", null);
            
            return ResponseEntity.status(400).body(response);
        }
    }

    @Operation(
        summary = "Customer Pages",
        description = "Fetch customers by page number, size, and optional plan ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Customer data retrieved successfully"),
        @ApiResponse(responseCode = "204", description = "No data found for the given parameters"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
	@GetMapping("/customerPages")
	//	The @ModelAttribute annotation is commonly used to bind form data (from HTTP request parameters) to a model object automatically.
    public ResponseEntity<Object> getCustomerPages(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Customer details to be fetched",
            required = true,
            content = @Content(
                schema = @Schema(implementation =   CustomerPageRequestDTO.class)
            )
        )    
    @Valid @ModelAttribute CustomerPageRequestDTO request) throws CommonExceptions {
		int page = request.getPage();
		int size = request.getSize();
		String planId = request.getPlanId();
        Page<CustomerProjectionDTO> customerPage = customerPageableService.filterByPlanIdPages(planId, page, size);

        if (!customerPage.isEmpty()) {
            return ResponseEntity.ok(customerPage);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No data found for the given parameters.");
        }
    }

	public List<UserLoanDTO> getUserLoans() throws CommonExceptions {
		try {
			return customerService.getUserLoanDetails();
		} catch (CommonExceptions e) {
			// Log and rethrow the exception if necessary
			paramLogger.error("Error fetching user loan details from service: {}", e.getMessage(), e);
			throw e;  // Rethrow the exception to be handled by the caller
		}
	}


	// public void filters() throws CommonExceptions {
	// 	try{
	// 		logger.info("/n Filtering customer by name like");
	// 	customerService.filterbyNameLike();
	// 	}catch(Exception e){
	// 		logger.error(ExceptionMessages.FAIL_MESSAGE);
	// 		throw new CommonExceptions(ExceptionMessages.FAIL_MESSAGE, e);
	// 	}
	// 	try{
	// 		logger.info("/n Filtering customer Age");

	// 	customerService.filterbyage();
	// 	}catch(Exception e){
	// 		logger.error(ExceptionMessages.FAIL_MESSAGE);
	// 		throw new CommonExceptions(ExceptionMessages.FAIL_MESSAGE, e);
	// 	}
	// 	try{
	// 		logger.info("/n Filtering customer by Age and order asc");
	// 	customerService.filterbyAgeOrderByPlanIdDesc();
	// 	}catch(Exception e){
	// 		logger.error(ExceptionMessages.FAIL_MESSAGE);
	// 		throw new CommonExceptions(ExceptionMessages.FAIL_MESSAGE, e);
	// 	}
	// 	try{
	// 		logger.info("/n Filtering customer by Age and Gender");
	// 	customerService.filterbyageandgender();
	// 	}catch(Exception e){
	// 		logger.error(ExceptionMessages.FAIL_MESSAGE);
	// 		throw new CommonExceptions(ExceptionMessages.FAIL_MESSAGE, e);
	// 	}
	// 	try{
	// 		logger.info("/n Filtering customer by Gender");
	// 	customerService.filterbygender();
	// 	}catch(Exception e){
	// 		logger.error(ExceptionMessages.FAIL_MESSAGE);
	// 		throw new CommonExceptions(ExceptionMessages.FAIL_MESSAGE, e);
	// 	}
	// 	try{
	// 		logger.info("/n Filtering customer by name like");
	// 	customerService.filterbyNameLike();
	// 	}catch(Exception e){
	// 		logger.error(ExceptionMessages.FAIL_MESSAGE);
	// 		throw new CommonExceptions(ExceptionMessages.FAIL_MESSAGE, e);
	// 	}

	// 	try{
	// 		logger.info("/n Filtering customer by name like");
	// 	customerService.filterbyAgeRangeAndGender();
	// 	}catch(Exception e){
	// 		logger.error(ExceptionMessages.FAIL_MESSAGE);
	// 		throw new CommonExceptions(ExceptionMessages.FAIL_MESSAGE, e);
	// 	}
	// }
	// public void Projection() throws CommonExceptions {
	// 	try{
	// 	List<CustomerProjectionDTO> customer = customerService.filterbyPlanId();
	// 	logger.info(customer);
	// 	if(customer == null){
	// 		throw new CommonExceptions(ExceptionMessages.CUSTOMER_NOT_FOUND);
	// 	}
	// 	else{
	// 		System.out.println(customer);
	// 		logger.info(ExceptionMessages.CUSTOMER_FOUND);
	// 	}
	// }catch(Exception e){
	// 	logger.error(ExceptionMessages.FAIL_MESSAGE);
	// 	throw new CommonExceptions(ExceptionMessages.FAIL_MESSAGE, e);
	// }
	// }


}
