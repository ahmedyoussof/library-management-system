package cc.maids.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.maids.library.model.Book;
import cc.maids.library.repository.BookRepository;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;

  @Transactional
  @Override
  public Book addBook(Book book) {
    return bookRepository.save(book);
  }

}
