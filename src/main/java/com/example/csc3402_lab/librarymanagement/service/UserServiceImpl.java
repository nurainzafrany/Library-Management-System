package com.example.csc3402_lab.librarymanagement.service;

import com.example.csc3402_lab.librarymanagement.model.Borrower;
import com.example.csc3402_lab.librarymanagement.repository.BorrowerRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService {

    private final BorrowerRepository borrowerRepository;

    public UserServiceImpl(BorrowerRepository borrowerRepository) {
        this.borrowerRepository = borrowerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String borrowerUsername) throws UsernameNotFoundException {
        Borrower borrower = borrowerRepository.findByBorrowerUsername(borrowerUsername);
        if (borrower == null) {
            throw new UsernameNotFoundException("Borrower not found with username: " + borrowerUsername);
        }
        return new CustomUserDetails(borrower);
    }
}
