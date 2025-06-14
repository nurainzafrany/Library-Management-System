package com.example.csc3402_lab.librarymanagement.repository;

import com.example.csc3402_lab.librarymanagement.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findByBookISBN(String bookISBN);
}


