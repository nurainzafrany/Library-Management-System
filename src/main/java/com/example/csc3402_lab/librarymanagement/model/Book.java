package com.example.csc3402_lab.librarymanagement.model;

import jakarta.persistence.*;

@Entity
public class Book {

    @Transient
    private Borrow borrow;

    @Id
    @Column(name = "book_ISBN")
    private String bookISBN;


    @Column(name = "book_title")
    private String bookTitle;


    @Column(name = "book_author")
    private String bookAuthor;


    public Book() {
    }

    public Book(String bookISBN, String bookTitle, String bookAuthor) {
        this.bookISBN = bookISBN;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
    }

    public String getBookISBN() {
        return bookISBN;
    }


    public void setBookISBN(String bookISBN) {
        this.bookISBN = bookISBN;
    }


    public String getBookTitle() {
        return bookTitle;
    }


    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }


    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    @Override
    public String toString() {
        return "Book{" +
                ", bookISBN='" + bookISBN + '\'' +
                ", bookTitle='" + bookTitle + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                '}';
    }
}
