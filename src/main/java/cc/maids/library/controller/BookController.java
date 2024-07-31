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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Tag(name = "Book Controller", description = "Endpoints handling operations for Books")
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

  @GetMapping("/{id}")
  @Operation(
      summary = "Get a book by ID",
      description = "Retrieve details of a specific book by ID",
      parameters = {
          @Parameter(name = "id", description = "ID of the book to retrieve", example = "1")
      }
  )
  public ResponseEntity<BookDTO> getBookById(
      @Parameter(description = "ID of the book to retrieve", example = "1") @PathVariable Long id) {

    return ResponseEntity.ok(bookMapper.convertValue(bookService.getBookById(id), BookDTO.class));
  }

  @PutMapping("/{id}")
  @Operation(
      summary = "Update a book",
      description = "Update an existing book's information",
      parameters = {
          @Parameter(name = "id", description = "ID of the book to be updated", example = "1")
      }
  )
  public ResponseEntity<BookDTO> updateBook(
      @Parameter(description = "ID of the book to be updated", example = "1") @PathVariable Long id,
      @Valid @RequestBody BookDTO book) {

    Book updatedBook = bookService.updateBook(id, bookMapper.convertValue(book, Book.class));
    return ResponseEntity.ok(bookMapper.convertValue(updatedBook, BookDTO.class));
  }

  @DeleteMapping("/{id}")
  @Operation(
      summary = "Delete a book",
      description = "Remove a book from the library",
      parameters = {
          @Parameter(name = "id", description = "ID of the book to be deleted", example = "1")
      }
  )
  public ResponseEntity<Void> deleteBook(
      @Parameter(description = "ID of the book to be deleted", example = "1") @PathVariable Long id) {
    bookService.deleteBook(id);
    return ResponseEntity.noContent().build();
  }
}
