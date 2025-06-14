package com.example.csc3402_lab.librarymanagement.repository;

import com.example.csc3402_lab.librarymanagement.model.Borrow;
import com.example.csc3402_lab.librarymanagement.model.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    @Query("SELECT COUNT(b) FROM Borrow b WHERE b.borrower.borrowerId = :borrowerId AND b.returnDate IS NULL")
    int countCurrentlyBorrowedBooksByBorrowerId(@Param("borrowerId") Integer borrowerId);
    List<Borrow> findByBorrower(Borrower borrower);
    List<Borrow> findByBorrower_BorrowerId(Integer borrowerId);

}

