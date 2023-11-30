package com.example.onlinelibrary.service;

import com.example.onlinelibrary.model.Book;

import java.util.List;

public interface BookService {
    public Book findByTitle(String title);
    public Book findByAuthor(String author);
    public List<Book> findAll();
    public Book addBook(Book book);
}
