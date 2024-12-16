package com.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.backend.domain.Customer;
import com.backend.dto.CustomerProjectionDTO;

public interface CustomerRepo extends CrudRepository<Customer, Long> {

    List<Customer> findByAge(Integer age);
    List<Customer> findByGender(Character gender);
    List<Customer> findByAgeAndGender(Integer age, Character gender);
    List<Customer> findByAgeBetween(Integer age1, Integer age2);
    List<Customer> findByAddressNull();
    List<Customer> findByNameLike(String name);
    List<Customer> findByAgeOrderByPlanIdDesc(Integer age);

    @Query("SELECT c from Customer c where c.age BETWEEN :minage AND :maxage AND c.gender = :gender")
    List<Customer> findByAgeRangeAndGender(@Param("minage") Integer minage, @Param("maxage") Integer maxage, @Param("gender")Character gender);

    @Query("SELECT new com.backend.dto.CustomerProjectionDTO(c.phoneNumber, c.name, c.planId) from Customer c where c.name = :name AND c.phoneNumber = :phoneNumber")
    List<CustomerProjectionDTO> findByNameandNumber(@Param("name") String name,@Param("phoneNumber") Long phoneNumber);

    @Query("SELECT new com.backend.dto.CustomerProjectionDTO(c.phoneNumber, c.name, c.planId) from Customer c where c.planId = :planId")
    List<CustomerProjectionDTO> findByplanIDProjection(@Param("planId") String planId);
    

}
