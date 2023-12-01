package com.example.onlinelibrary.repository;

import com.example.onlinelibrary.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// The @Repository annotation is used to indicate that the class provides the mechanism for storage, retrieval,
// search, update and delete operation on objects.
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByAuthorIgnoreCase(String author);
    List<Book> findAllByTitleIgnoreCase(String title);
}
