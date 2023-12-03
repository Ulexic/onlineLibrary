package com.example.onlinelibrary.service.implementation;

import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.repository.BookRepository;
import com.example.onlinelibrary.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    // The repository is injected into the constructor
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAllByTitle(String title) {
        List<Book> books = bookRepository.findAllByTitleIgnoreCase(title);
        if(books.isEmpty())
            throw new IllegalArgumentException("No book found");

        return books;
    }

    @Override
    public List<Book> findAllByAuthor(String author) {
        List<Book> books = bookRepository.findAllByAuthorIgnoreCase(author);
        if(books.isEmpty())
            throw new IllegalArgumentException("No book found");

        return books;
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = bookRepository.findAll();
        if(books.isEmpty())
            throw new IllegalArgumentException("No book found");
        return books;
    }

    @Override
    public Book addBook(Book book) {
        // The book is saved only if the publicationDate is in the format yyyy-MM-dd
        if(!book.getPublicationDate().matches("^\\d{4}-\\d{2}-\\d{2}$"))
            throw new IllegalArgumentException("Invalid publicationDate");

        return bookRepository.save(book);
    }

    @Override
    public Book findById(Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null)
            throw new IllegalArgumentException("No book found");
        return book;
    }

    @Override
    public List<Book> findAllAvailable() {
        List<Book> books = bookRepository.findAll();
        books = books.stream().filter(book -> book.getAvailableCopies() > 0).collect(Collectors.toList());
        if (books.isEmpty())
            throw new IllegalArgumentException("No book found");
        return books;
    }

    @Override
    public List<Book> getBooksByIds(List<Long> ids) {
        return bookRepository.findAllById(ids);
    }
}
