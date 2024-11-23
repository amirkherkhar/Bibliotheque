package com.amir.library.service;

import com.amir.library.Repos.BookRepository;
import com.amir.library.dto.BookDTO;
import com.amir.library.mapper.BookMapper;
import com.amir.library.model.Book;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    public BookDTO addBook(BookDTO bookDTO) {
        Book book = bookMapper.bookDTOToBook(bookDTO);
        book = bookRepository.save(book);
        return bookMapper.bookToBookDTO(book);
    }

    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setTitle(bookDTO.getTitle());
            book.setPublishedYear(bookDTO.getPublishedYear());
            book.setGenre(bookDTO.getGenre());
            book.setAvailable(bookDTO.getAvailable());
            bookRepository.save(book);
            return bookMapper.bookToBookDTO(book);
        }
        throw new EntityNotFoundException("Book not found with id " + id);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }



    public List<BookDTO> getBooksByGenre(String genre) {
        List<Book> books = bookRepository.findAvailableBooksByGenre(genre);
        return books.stream()
                .map(bookMapper::bookToBookDTO)
                .collect(Collectors.toList());
    }




    public Page<BookDTO> getBooks(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(bookMapper::bookToBookDTO);
    }


}

