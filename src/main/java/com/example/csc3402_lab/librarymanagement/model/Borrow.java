package com.example.csc3402_lab.librarymanagement.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long borrowId;

    @ManyToOne
    @JoinColumn(name = "borrower_id", nullable = false)
    private Borrower borrower;

    @ManyToOne
    @JoinColumn(name = "book_ISBN", referencedColumnName = "book_ISBN")
    private Book book;

    @Column(name = "borrow_date", nullable = false)
    private LocalDate borrowDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    // --- Derived Attributes ---

    @Transient
    public long getDaysOverdue() {
        if (returnDate != null || getDueDate() == null) return 0;
        long days = ChronoUnit.DAYS.between(getDueDate(), LocalDate.now());
        return days > 0 ? days : 0;
    }

    @Transient
    public long getDaysRemaining() {
        if (returnDate != null || getDueDate() == null) return 0;
        long days = ChronoUnit.DAYS.between(LocalDate.now(), getDueDate());
        return days > 0 ? days : 0;
    }

    // Optional: total books borrowed by a user, can be injected from service
    @Transient
    private int totalBooks;

    public int getTotalBooks() {
        return totalBooks;
    }

    public void setTotalBooks(int totalBooks) {
        this.totalBooks = totalBooks;
    }

    // Assuming 7 days loan period, for example
    @Transient
    public LocalDate getDueDate() {
        if (borrowDate == null) return null;
        return borrowDate.plusDays(7); // Change 7 to your library's loan duration
    }

    public Borrow() {
    }

    public Borrow(Borrower borrower, Book book, LocalDate borrowDate, LocalDate returnDate) {
        this.borrower = borrower;
        this.book = book;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public Borrow(Long borrowId, Borrower borrower, Book book, LocalDate borrowDate, LocalDate returnDate) {
        this.borrowId = borrowId;
        this.borrower = borrower;
        this.book = book;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public Long getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(Long borrowId) {
        this.borrowId = borrowId;
    }

    public Borrower getBorrower() {
        return borrower;
    }

    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Borrow{" +
                "borrowId=" + borrowId +
                ", borrowerId=" + (borrower != null ? borrower.getBorrowerId() : null) +
                ", bookISBN=" + (book != null ? book.getBookISBN() : null) +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                '}';
    }

}