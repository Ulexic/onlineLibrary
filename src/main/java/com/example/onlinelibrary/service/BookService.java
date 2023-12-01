package com.example.onlinelibrary.service;

import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.model.LoanRequest;

import java.util.List;

public interface BookService {
    List<Book> findAllByTitleIgnoreCase(String title);
    List<Book> findAllByAuthorIgnoreCase(String author);
    List<Book> findAll();
    List<Book> findAllAvailable();
    Book addBook(Book book);
    Book findById(Long id);
    void borrowBook(Book book, LoanRequest loanRequest);

}
