package com.example.csc3402_lab.librarymanagement.service;

import com.example.csc3402_lab.librarymanagement.model.Borrower;

import java.util.List;

public interface BorrowerService {

    List<Borrower> listAllBorrower();
    Borrower addNewBorrower(Borrower borrower);

    Borrower findByUsername(String username);
    Borrower findByBorrowerIdAndBorrowerUsername(Integer borrowerId, String borrowerUsername);
    Borrower updateBorrower(Borrower borrower);
    void deleteBorrower(Borrower borrower);

    Borrower saveBorrower(Borrower borrower);

    boolean isUsernameTaken(String username);
    boolean isIdTaken(Integer id);

}