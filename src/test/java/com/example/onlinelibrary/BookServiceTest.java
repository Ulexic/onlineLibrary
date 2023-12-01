package com.example.onlinelibrary;

import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.repository.BookRepository;
import com.example.onlinelibrary.service.BookService;
import jakarta.annotation.Resource;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles("test")
@SpringBootTest
public class BookServiceTest {
    @Resource
    private BookRepository bookRepository;
    @Resource
    private BookService service;

    @Test
    void testFindAllByTitleRightCase() {
        bookRepository.deleteAll();
        Book book = new Book(1L, "Title", "Author test", "2021-01-01", 3);
        bookRepository.save(book);

        List<Book> books = service.findAllByTitle("Title");
        assertEquals(1, books.size());
    }

    @Test
    void testFindAllByTitleWrongCase() {
        bookRepository.deleteAll();
        Book book = new Book(1L, "Title", "Author test", "2021-01-01", 3);
        bookRepository.save(book);

        List<Book> books = service.findAllByTitle("title");
        assertEquals(1, books.size());
    }

    @Test
    void testFindAllByTitleNotFound() {
        bookRepository.deleteAll();
        Book book = new Book(1L, "Title", "Author test", "2021-01-01", 3);
        bookRepository.save(book);

        List<Book> books = service.findAllByTitle("Test");
        assertEquals(0, books.size());
    }

    @Test
    void testFindAllByAuthorRightCase() {
        bookRepository.deleteAll();
        Book book = new Book(1L, "Title", "Author test", "2021-01-01", 3);
        bookRepository.save(book);

        List<Book> books = service.findAllByAuthor("Author test");
        assertEquals(1, books.size());
    }

    @Test
    void testFindAllByAuthorWrongCase() {
        bookRepository.deleteAll();
        Book book = new Book(1L, "Title", "Author test", "2021-01-01", 3);
        bookRepository.save(book);

        List<Book> books = service.findAllByAuthor("author test");
        assertEquals(1, books.size());
    }

    @Test
    void testFindAllByAuthorNotFound() {
        bookRepository.deleteAll();
        Book book = new Book(1L, "Title", "Author test", "2021-01-01", 3);
        bookRepository.save(book);

        List<Book> books = service.findAllByAuthor("Test");
        assertEquals(0, books.size());
    }

    @Test
    void findAll() {
        bookRepository.deleteAll();
        Book book = new Book(1L, "Title", "Author test", "2021-01-01", 3);
        bookRepository.save(book);
        book = new Book(2L, "Title2", "Author test2", "2021-01-01", 1);
        bookRepository.save(book);

        List<Book> books = service.findAll();
        assertEquals(2, books.size());
    }

    @Test
    void findAllAvailableAllBooksAvailable() {
        bookRepository.deleteAll();
        Book book = new Book(1L, "Title", "Author test", "2021-01-01", 3);
        bookRepository.save(book);
        book = new Book(2L, "Title2", "Author test2", "2021-01-01", 1);
        bookRepository.save(book);

        List<Book> books = service.findAllAvailable();
        assertEquals(2, books.size());
    }

    @Test
    void findAllAvailableNotAllBooksAvailable() {
        bookRepository.deleteAll();
        Book book = new Book(1L, "Title", "Author test", "2021-01-01", 3);
        bookRepository.save(book);
        book = new Book(2L, "Title2", "Author test2", "2021-01-01", 0);
        bookRepository.save(book);

        List<Book> books = service.findAllAvailable();
        assertEquals(1, books.size());
    }

    @Test
    void addBook() {
        bookRepository.deleteAll();
        Book book = new Book(1L, "Title", "Author test", "2021-01-01", 3);
        Book addedBook = service.addBook(book);
        assertNotNull(addedBook);
    }

    @Test
    void addBookIncorrectDate() {
        bookRepository.deleteAll();
        Book book = new Book(1L, "Title", "Author test", "20-01-01", 3);

        assertThrows(IllegalArgumentException.class, () -> { service.addBook(book); });
    }

    @Test
    void findByIdExists() {
        bookRepository.deleteAll();
        Book book = new Book(1L, "Title", "Author test", "2021-01-01", 3);
        book = bookRepository.save(book);

        Book foundBook = service.findById(book.getId());
        assertNotNull(foundBook);
    }

    @Test
    void findByIdNotExists() {
        bookRepository.deleteAll();
        Book book = new Book(1L, "Title", "Author test", "2021-01-01", 3);
        book = bookRepository.save(book);

        Book foundBook = service.findById(book.getId() + 1);
        assertEquals(null, foundBook);
    }

    @Test
    void getBooksByIds() {
        bookRepository.deleteAll();
        Book book1 = new Book(1L, "Title", "Author test", "2021-01-01", 1);
        bookRepository.save(book1);
        Book book2 = new Book(2L, "Title2", "Author test2", "2021-01-01", 1);
        bookRepository.save(book2);

        List<Book> test = service.findAll();
        List<Book> books = service.getBooksByIds(List.of(book1.getId(), book2.getId()));
        assertEquals(2, books.size());
    }

    @Test
    void getBooksByIdsOneNotFound() {
        bookRepository.deleteAll();
        Book book1 = new Book(1L, "Title", "Author test", "2021-01-01", 1);
        bookRepository.save(book1);
        Book book2 = new Book(2L, "Title2", "Author test2", "2021-01-01", 1);
        bookRepository.save(book2);

        List<Book> books = service.getBooksByIds(List.of(book1.getId() + 1, book2.getId()));
        assertEquals(1, books.size());
    }

    @Test
    void getBooksByIdsAllNotFound() {
        bookRepository.deleteAll();
        Book book1 = new Book(1L, "Title", "Author test", "2021-01-01", 1);
        bookRepository.save(book1);
        Book book2 = new Book(2L, "Title2", "Author test2", "2021-01-01", 1);
        bookRepository.save(book2);

        List<Book> books = service.getBooksByIds(List.of(book1.getId() + 1, book2.getId() + 1));
        assertEquals(1, books.size());
    }

    @Test
    void getBooksByIdsEmptyList() {
        bookRepository.deleteAll();
        Book book = new Book(1L, "Title", "Author test", "2021-01-01", 1);
        bookRepository.save(book);
        book = new Book(2L, "Title2", "Author test2", "2021-01-01", 1);
        bookRepository.save(book);

        List<Book> books = service.getBooksByIds(List.of());
        assertEquals(0, books.size());
    }
}