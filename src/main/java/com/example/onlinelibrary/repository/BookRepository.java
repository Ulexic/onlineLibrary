package com.example.onlinelibrary.repository;

import com.example.onlinelibrary.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByAuthorIgnoreCase(String author);
    List<Book> findAllByTitleIgnoreCase(String title);
}
