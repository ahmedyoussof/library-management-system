package cc.maids.library.service.impl;


import cc.maids.library.exception.ResourceNotFoundException;
import cc.maids.library.model.Book;
import cc.maids.library.repository.BookRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

  @Mock
  private BookRepository bookRepository;

  @InjectMocks
  private BookServiceImpl bookService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void addBook() {
    Book book = new Book();
    when(bookRepository.save(any(Book.class))).thenReturn(book);
    assertEquals(book, bookService.addBook(book));
  }

  @Test
  void getAllBooks() {
    when(bookRepository.findAll()).thenReturn(List.of(new Book()));
    assertEquals(1, bookService.getAllBooks().size());
  }

  @Test
  void getBookById() {
    Book book = new Book();
    when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
    assertEquals(book, bookService.getBookById(1L));
  }

  @Test
  void getBookByIdNotFound() {
    when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class, () -> bookService.getBookById(1L));
  }

  @Test
  void updateBook() {
    Book book = new Book();
    when(bookRepository.existsById(anyLong())).thenReturn(true);
    when(bookRepository.save(any(Book.class))).thenReturn(book);
    assertEquals(book, bookService.updateBook(1L, book));
  }

  @Test
  void updateBookNotFound() {
    when(bookRepository.existsById(anyLong())).thenReturn(false);
    assertThrows(ResourceNotFoundException.class, () -> bookService.updateBook(1L, new Book()));
  }

  @Test
  void deleteBook() {
    when(bookRepository.existsById(anyLong())).thenReturn(true);
    doNothing().when(bookRepository).deleteById(anyLong());
    assertDoesNotThrow(() -> bookService.deleteBook(1L));
  }

  @Test
  void deleteBookNotFound() {
    when(bookRepository.existsById(anyLong())).thenReturn(false);
    assertThrows(ResourceNotFoundException.class, () -> bookService.deleteBook(1L));
  }
}

