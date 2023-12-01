package com.example.onlinelibrary.service.implementation;

import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.model.LoanRequest;
import com.example.onlinelibrary.repository.BookRepository;
import com.example.onlinelibrary.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

// The BookServiceImpl class is a service class and it will be used by the BookController class
// to perform database operations
@Service
public class BookServiceImpl implements BookService {
    // The instance of the BookRepository will be created and passed to the constructor of the BookServiceImpl class
    // This is called Dependency Injection
    private final BookRepository bookRepository;
    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    public BookServiceImpl(BookRepository bookRepository)
    {
        this.bookRepository = bookRepository;
    }

    private boolean checkDate(String date){
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
        try {
            if (today.after(sdf.parse(date)))
                return false;
            return date.matches("^(0[1-9]|[12][0-9]|3[01])\\/(0[1-9]|1[012])\\/\\d{4}$");
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public List<Book> findAllByTitleIgnoreCase(String title) {
        return bookRepository.findAllByTitleIgnoreCase(title);
    }

    @Override
    public List<Book> findAllByAuthorIgnoreCase(String author) {
        return bookRepository.findAllByAuthorIgnoreCase(author);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    // The date format should be dd/mm/yyyy
    // It will be validated before saving the book to the database
    // If the date is invalid, an IllegalArgumentException will be thrown
    @Override
    public Book addBook(Book book) {
        if(!checkDate(book.getPublicationDate()))
            throw new IllegalArgumentException("Invalid publicationDate");

        return bookRepository.save(book);
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public void borrowBook(Book book, LoanRequest loanRequest) {
        if (!checkDate(loanRequest.getReturnDate()))
            throw new IllegalArgumentException("Invalid returnDate");

        if (book.getAvailableCopies() > 0) {
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            bookRepository.save(book);
        } else {
            throw new IllegalArgumentException("No available copies");
        }
    }

    @Override
    public List<Book> findAllAvailable() {
        List<Book> books = bookRepository.findAll();

        books = books.stream().filter(book -> book.getAvailableCopies() > 0).collect(Collectors.toList());
        return books;
    }
}
