package cc.maids.library.service.impl;

import cc.maids.library.exception.ResourceNotFoundException;
import cc.maids.library.model.Book;
import cc.maids.library.model.BorrowingRecord;
import cc.maids.library.model.Patron;
import cc.maids.library.repository.BookRepository;
import cc.maids.library.repository.BorrowingRepository;
import cc.maids.library.repository.PatronRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class BorrowingServiceImplTest {

  @Mock
  private BookRepository bookRepository;

  @Mock
  private PatronRepository patronRepository;

  @Mock
  private BorrowingRepository borrowingRepository;

  @InjectMocks
  private BorrowingServiceImpl borrowingService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void borrowBook() {
    Book book = new Book();
    Patron patron = new Patron();
    when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
    when(patronRepository.findById(anyLong())).thenReturn(Optional.of(patron));
    when(borrowingRepository.existsByBookIdAndReturnDateIsNull(anyLong())).thenReturn(false);
    BorrowingRecord borrowingRecord = new BorrowingRecord();
    when(borrowingRepository.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);
    assertTrue(borrowingService.borrowBook(1L, 1L));
  }

  @Test
  void borrowBookBookOrPatronNotFound() {
    when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class, () -> borrowingService.borrowBook(1L, 1L));
  }

  @Test
  void returnBook() {
    BorrowingRecord borrowingRecord = new BorrowingRecord();
    when(borrowingRepository.findByBookIdAndPatronIdAndReturnDateIsNull(anyLong(), anyLong())).thenReturn(Optional.of(borrowingRecord));
    when(borrowingRepository.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);
    assertTrue(borrowingService.returnBook(1L, 1L));
  }

  @Test
  void returnBookNotFound() {
    when(borrowingRepository.findByBookIdAndPatronIdAndReturnDateIsNull(anyLong(), anyLong())).thenReturn(Optional.empty());
    assertFalse(borrowingService.returnBook(1L, 1L));
  }
}
