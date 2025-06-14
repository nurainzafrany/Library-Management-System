package com.example.csc3402_lab.librarymanagement.repository;

import com.example.csc3402_lab.librarymanagement.model.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, Integer> {
    Borrower findByBorrowerUsername(String borrowerUsername);
    boolean existsByBorrowerUsername(String username);
    Optional<Borrower> findByBorrowerIdAndBorrowerUsername(Integer borrowerId, String borrowerUsername);
}

