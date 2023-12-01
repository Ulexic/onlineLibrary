package com.example.onlinelibrary.service;

import com.example.onlinelibrary.model.Book;

import java.util.List;

public interface BookService {
    List<Book> findAllByTitle(String title);
    List<Book> findAllByAuthor(String author);
    List<Book> findAll();
    List<Book> findAllAvailable();
    Book addBook(Book book);
    Book findById(Long id);
    List<Book> getBooksByIds(List<Long> ids);
}
