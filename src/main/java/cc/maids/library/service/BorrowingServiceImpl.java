package cc.maids.library.service;

import java.time.LocalDate;
import java.util.Optional;

import cc.maids.library.exception.ResourceNotFoundException;
import cc.maids.library.model.Book;
import cc.maids.library.model.BorrowingRecord;
import cc.maids.library.model.Patron;
import cc.maids.library.repository.BookRepository;
import cc.maids.library.repository.BorrowingRepository;
import cc.maids.library.repository.PatronRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BorrowingServiceImpl implements BorrowingService {

  private final BookRepository bookRepository;
  private final PatronRepository patronRepository;
  private final BorrowingRepository borrowingRepository;

  @Transactional
  @Override
  public Boolean borrowBook(Long bookId, Long patronId) {

    Optional<Book> book = bookRepository.findById(bookId);
    Optional<Patron> patron = patronRepository.findById(patronId);

    if (book.isPresent() && patron.isPresent()) {
      if (Boolean.FALSE.equals(borrowingRepository.existsByBookIdAndReturnDateIsNull(bookId))) {
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book.get());
        borrowingRecord.setPatron(patron.get());
        borrowingRecord.setBorrowDate(LocalDate.now());
        borrowingRepository.save(borrowingRecord);
        return true;
      }
      return false;
    } else {
      throw new ResourceNotFoundException("Book or Patron not found");
    }
  }

  @Transactional
  @Override
  public Boolean returnBook(Long bookId, Long patronId) {
    Optional<BorrowingRecord> borrowingRecordOptional = borrowingRepository.findByBookIdAndPatronIdAndReturnDateIsNull(
        bookId, patronId);

    if (borrowingRecordOptional.isPresent()) {
      BorrowingRecord borrowingRecord = borrowingRecordOptional.get();
      borrowingRecord.setReturnDate(LocalDate.now());
      borrowingRepository.save(borrowingRecord);
      return true;
    }
    return false;
  }
}

