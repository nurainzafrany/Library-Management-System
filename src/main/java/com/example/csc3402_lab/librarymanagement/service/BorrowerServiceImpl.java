package com.example.csc3402_lab.librarymanagement.service;

import com.example.csc3402_lab.librarymanagement.model.Borrower;
import com.example.csc3402_lab.librarymanagement.repository.BorrowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
public class BorrowerServiceImpl implements BorrowerService {

    private final BorrowerRepository borrowerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Constructor Injection
    public BorrowerServiceImpl(BorrowerRepository borrowerRepository) {
        this.borrowerRepository = borrowerRepository;
    }

    @Override
    public List<Borrower> listAllBorrower() {
        return borrowerRepository.findAll();
    }

    @Override
    public Borrower addNewBorrower(Borrower borrower) {
        return borrowerRepository.save(borrower);
    }

    @Override
    public Borrower findByUsername(String username) {
        return borrowerRepository.findByBorrowerUsername(username);
    }

    @Override
    public Borrower updateBorrower(Borrower borrower) {
        return borrowerRepository.save(borrower);
    }

    @Override
    public void deleteBorrower(Borrower borrower) {
        borrowerRepository.delete(borrower);
    }

    @Override
    public boolean isUsernameTaken(String username) {
        return borrowerRepository.existsByBorrowerUsername(username);
    }

    @Override
    public boolean isIdTaken(Integer id) {
        return borrowerRepository.existsById(id);
    }

    @Override
    public Borrower findByBorrowerIdAndBorrowerUsername(Integer borrowerId, String borrowerUsername) {
        return borrowerRepository.findByBorrowerIdAndBorrowerUsername(borrowerId, borrowerUsername).orElse(null);
    }

    @Override
    public Borrower saveBorrower(Borrower borrower) {
        String rawPassword = borrower.getBorrowerPass();

        // Only encode if not already encoded
        if (!rawPassword.startsWith("$2a$")) { // BCrypt hashes always start with this
            rawPassword = passwordEncoder.encode(rawPassword);
            borrower.setBorrowerPass(rawPassword);
        }

        return borrowerRepository.save(borrower);
    }

}