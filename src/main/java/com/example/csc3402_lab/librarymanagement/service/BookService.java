package com.example.csc3402_lab.librarymanagement.service;

import com.example.csc3402_lab.librarymanagement.model.Book;
import com.example.csc3402_lab.librarymanagement.model.Borrower;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAllBooks();
    Book addBook(Book book);
    Optional<Book> getBookById(Integer id);
    Book updateBook(Book book);
    void deleteBook(Book book);
    Book save(Book book);
    Book getBooksByISBN(String isbn);
}
