package com.amir.library.Repos;

import com.amir.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE b.authorId = :authorId")
    List<Book> findBooksByAuthorId(Long authorId);


    @Query("SELECT b FROM Book b WHERE b.genre = :genre AND b.available = true")
    List<Book> findAvailableBooksByGenre(String genre);
}
