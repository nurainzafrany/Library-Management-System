package com.example.csc3402_lab.librarymanagement.service;

import com.example.csc3402_lab.librarymanagement.model.Borrow;
import com.example.csc3402_lab.librarymanagement.model.Borrower;
import com.example.csc3402_lab.librarymanagement.repository.BorrowRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BorrowServiceImpl implements BorrowService {

    private final BorrowRepository borrowRepo;

    public BorrowServiceImpl(BorrowRepository borrowRepo) {
        this.borrowRepo = borrowRepo;
    }

    @Override
    public List<Borrow> getAllBorrows() {
        return borrowRepo.findAll();
    }

    @Override
    public Borrow addBorrow(Borrow borrow) {
        return borrowRepo.save(borrow);
    }

    @Override
    public Optional<Borrow> getBorrowById(Long id) {
        return borrowRepo.findById(id);
    }

    @Override
    public Borrow updateBorrow(Borrow borrow) {
        return borrowRepo.save(borrow);
    }

    @Override
    public void deleteBorrow(Borrow borrow) {
        borrowRepo.delete(borrow);
    }

    @Override
    public int getTotalBooksBorrowedByBorrower(Integer borrowerId) {
        return borrowRepo.countCurrentlyBorrowedBooksByBorrowerId(borrowerId);
    }

    public void save(Borrow borrow) {
        borrowRepo.save(borrow);
    }

    @Override
    public List<Borrow> findByBorrower(Borrower borrower) {
        return borrowRepo.findByBorrower_BorrowerId(borrower.getBorrowerId());
    }
}