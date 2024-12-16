package com.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.domain.Loan;
import com.backend.dto.UserLoanDTO;


@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    // Custom query example
    @Query("SELECT new com.backend.dto.UserLoanDTO(u.userid, u.name, u.email, a.addressId, a.street, a.city, l.loanId, l.loanType, l.amount) " +
    "FROM User u " +
    "INNER JOIN u.address a " +
    "INNER JOIN Loan l ON l.address.addressId = a.addressId AND l.user.userid = u.userid")
List<UserLoanDTO> fetchUserLoanDetails();
}