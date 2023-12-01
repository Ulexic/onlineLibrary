package com.example.onlinelibrary.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String author;
    private String publicationDate;
    private int availableCopies;
}
