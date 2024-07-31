package cc.maids.library.service;


import java.util.List;

import cc.maids.library.model.Book;

public interface BookService {

  Book addBook(Book book);

  List<Book> getAllBooks();

  Book getBookById(Long id);

  Book updateBook(Long id, Book book);

  void deleteBook(Long id);
}
