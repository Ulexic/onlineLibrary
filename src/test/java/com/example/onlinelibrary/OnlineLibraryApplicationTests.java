package com.example.onlinelibrary;

import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.repository.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@RunWith(SpringRunner.class)
public class OnlineLibraryApplicationTests {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private BookRepository repository;

    @Before
    public void before() {
        entityManager.persist(new Book(1l, "The secret history", "Donna Tartt", "01/09/1992", 4));
        entityManager.persist(new Book(2l, "Gideon the Ninth", "Tamsyn Muir", "10/09/2019", 2));
    }

    @Test
    public void testFindAllByAuthor() {
        List<Book> books = repository.findAllByAuthorIgnoreCase("Donna Tartt");
        assertEquals(1, books.size());
        assertEquals("Donna Tartt", books.get(0).getAuthor());
    }

    @Test
    public void testFindAllByTitle() {
        List<Book> books = repository.findAllByTitleIgnoreCase("Gideon the Ninth");
        assertEquals(1, books.size());
        assertEquals("Gideon the Ninth", books.get(0).getTitle());
    }

    @Test
    public void testFindAll() {
        List<Book> books = repository.findAll();
        assertEquals(2, books.size());
    }
}


