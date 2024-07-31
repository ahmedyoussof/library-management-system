package cc.maids.library.service;


import java.util.List;

import cc.maids.library.model.Book;

public interface BookService {

  Book addBook(Book book);
  List<Book> getAllBooks();

}
