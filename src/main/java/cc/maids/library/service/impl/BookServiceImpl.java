package cc.maids.library.service.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.maids.library.exception.ResourceNotFoundException;
import cc.maids.library.model.Book;
import cc.maids.library.repository.BookRepository;
import cc.maids.library.service.BookService;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;

  @Transactional
  @Override
  public Book addBook(Book book) {
    return bookRepository.save(book);
  }

  @Transactional(readOnly = true)
  @Override
  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }

  @Cacheable(value = "books", key = "#id")
  @Transactional(readOnly = true)
  @Override
  public Book getBookById(Long id) {
    return bookRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Book with id: " + id + " not found"));
  }


  @CachePut(value = "books", key = "#id")
  @Transactional
  @Override
  public Book updateBook(Long id, Book book) {
    if (bookRepository.existsById(id)) {
      book.setId(id);
      return bookRepository.save(book);
    }
    throw new ResourceNotFoundException("Book not found with id " + id);
  }

  @CacheEvict(value = "books", key = "#id")
  @Transactional
  @Override
  public void deleteBook(Long id) {
    if (bookRepository.existsById(id)) {
      bookRepository.deleteById(id);
    } else {
      throw new ResourceNotFoundException("Book not found with id " + id);
    }
  }

}

