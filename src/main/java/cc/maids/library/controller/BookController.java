package cc.maids.library.controller;


import java.util.List;

import lombok.RequiredArgsConstructor;

import cc.maids.library.dto.BookDTO;
import cc.maids.library.model.Book;
import cc.maids.library.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;



@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;
  private final ObjectMapper bookMapper ;


  @PostMapping
  @Operation(
    summary = "Add a new book",
    description = "Add a new book to the library"
  )
  public ResponseEntity<BookDTO> addBook(@Valid @RequestBody BookDTO bookDTO) {

    Book createdBook = bookService.addBook(bookMapper.convertValue(bookDTO, Book.class));
    return new ResponseEntity<>(bookMapper.convertValue(createdBook, BookDTO.class),
        HttpStatus.CREATED);
  }

  @GetMapping
  @Operation(
      summary = "Get all books",
      description = "Retrieve a list of all books"
  )
  public ResponseEntity<List<BookDTO>> getAllBooks() {

    List<BookDTO> foundBooks = bookService.getAllBooks().stream()
        .map(book -> bookMapper.convertValue(book, BookDTO.class)).toList();
    return ResponseEntity.ok(foundBooks);
  }

}
