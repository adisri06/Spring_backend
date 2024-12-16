package com.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.backend.domain.Customer;
import com.backend.dto.CustomerProjectionDTO;

public interface CustomerPageableRepository extends PagingAndSortingRepository<Customer, Long> {

    @Query("SELECT new com.backend.dto.CustomerProjectionDTO(c.phoneNumber, c.name, c.planId) from Customer c where c.planId = :planId")
    Page<CustomerProjectionDTO> findByplanIDProjection(@Param("planId") String planId, Pageable pageable);

}
