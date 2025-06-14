package com.example.csc3402_lab.librarymanagement.model;

import jakarta.persistence.*;

@Entity
public class Borrower {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "borrower_Id")
    private Integer borrowerId;

    @Column(name = "borrowerUsername", unique = true)
    private String borrowerUsername;

    @Column(name = "borrowerEmail")
    private String borrowerEmail;

    @Column(name = "borrowerPhoneNo")
    private String borrowerPhoneNo;

    @Column(name = "borrowerPass")
    private String borrowerPass;

    public Borrower() {
    }

    public Borrower(String borrowerUsername, String borrowerEmail, String borrowerPhoneNo, String borrowerPass) {
        this.borrowerUsername = borrowerUsername;
        this.borrowerEmail = borrowerEmail;
        this.borrowerPhoneNo = borrowerPhoneNo;
        this.borrowerPass= borrowerPass;
    }

    public Integer getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Integer borrowerId) {
        this.borrowerId = borrowerId;
    }

    public String getBorrowerPass() {
        return borrowerPass;
    }

    public void setBorrowerPass(String borrowerPass) {
        this.borrowerPass = borrowerPass;
    }

    public String getBorrowerUsername() {
        return borrowerUsername;
    }

    public void setBorrowerUsername(String borrowerUsername) {
        this.borrowerUsername = borrowerUsername;
    }

    public String getBorrowerEmail() {
        return borrowerEmail;
    }

    public void setBorrowerEmail(String borrowerEmail) {
        this.borrowerEmail = borrowerEmail;
    }

    public String getBorrowerPhoneNo() {
        return borrowerPhoneNo;
    }

    public void setBorrowerPhoneNo(String borrowerPhoneNo) {
        this.borrowerPhoneNo = borrowerPhoneNo;
    }

    @Override
    public String toString() {
        return "Borrower{" +
                "borrowerId=" + borrowerId +
                ", borrowerUsername='" + borrowerUsername + '\'' +
                ", borrowerEmail='" + borrowerEmail + '\'' +
                ", borrowerPhoneNo=" + borrowerPhoneNo +
                ", borrowerPass='" + borrowerPass + '\'' +
                '}';
    }

}