package cc.maids.library.service;

public interface BorrowingService {

  Boolean borrowBook(Long bookId, Long patronId);

  Boolean returnBook(Long bookId, Long patronId);
}

