package com.example.demo.repository;

import com.example.demo.repository.dto.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
    Book findByIsbn(String isbn);
}
