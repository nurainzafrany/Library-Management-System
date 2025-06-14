package com.example.csc3402_lab.librarymanagement.service;

import com.example.csc3402_lab.librarymanagement.model.Book;
import com.example.csc3402_lab.librarymanagement.repository.BookRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepo;


    public BookServiceImpl(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }


    @Override
    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }


    @Override
    public Book addBook(Book book) {
        return bookRepo.save(book);
    }


    @Override
    public Optional<Book> getBookById(Integer id) {
        return bookRepo.findById(id);
    }


    @Override
    public Book updateBook(Book book) {
        return bookRepo.save(book);
    }


    @Override
    public void deleteBook(Book book) {
        bookRepo.delete(book);
    }

    public Book save(Book book) {
        return bookRepo.save(book);
    }

    public Book getBooksByISBN(String isbn) {
        return bookRepo.findByBookISBN(isbn);
    }

}


