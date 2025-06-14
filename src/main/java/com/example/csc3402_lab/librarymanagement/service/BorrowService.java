package com.example.csc3402_lab.librarymanagement.service;

import com.example.csc3402_lab.librarymanagement.model.Borrow;
import com.example.csc3402_lab.librarymanagement.model.Borrower;

import java.util.List;
import java.util.Optional;

public interface BorrowService {
    List<Borrow> getAllBorrows();
    Borrow addBorrow(Borrow borrow);
    Optional<Borrow> getBorrowById(Long id);
    Borrow updateBorrow(Borrow borrow);
    void deleteBorrow(Borrow borrow);
    void save(Borrow borrow);
    List<Borrow> findByBorrower(Borrower borrower);
    int getTotalBooksBorrowedByBorrower(Integer borrowerId);

}
