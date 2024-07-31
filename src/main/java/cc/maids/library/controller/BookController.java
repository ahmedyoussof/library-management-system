package cc.maids.library.controller;


import lombok.RequiredArgsConstructor;

import cc.maids.library.model.Book;
import cc.maids.library.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;



@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;


  @PostMapping
  @Operation(
    summary = "Add a new book",
    description = "Add a new book to the library"
  )
  public ResponseEntity<Book> addBook(
      @RequestBody Book book) {
    Book createdBook = bookService.addBook(book);
    return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
  }

}
