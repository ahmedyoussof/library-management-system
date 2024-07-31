package cc.maids.library.controller;

import cc.maids.library.service.BorrowingService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/borrow")
@RequiredArgsConstructor
public class BorrowingController {

  private final BorrowingService borrowingService;

  @PostMapping("/{bookId}/patron/{patronId}")
  @Operation(
    summary = "Borrow a book",
    description = "Allow a patron to borrow a book",
    parameters = {
        @Parameter(name = "bookId", description = "ID of the book to borrow", example = "1"),
        @Parameter(name = "patronId", description = "ID of the patron borrowing the book", example = "1")
    }
  )
  public ResponseEntity<String> borrowBook(
      @PathVariable Long bookId,
      @PathVariable Long patronId) {

    boolean isAvailableToBorrow = borrowingService.borrowBook(bookId, patronId);
    if (isAvailableToBorrow) {
      return ResponseEntity.ok("Book borrowed successfully");
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Unable to borrow the book");
    }
  }

  @PutMapping("/return/{bookId}/patron/{patronId}")
  @Operation(
    summary = "Return a borrowed book",
    description = "Record the return of a borrowed book by a patron",
    parameters = {
        @Parameter(name = "bookId", description = "ID of the book to borrow", example = "1"),
        @Parameter(name = "patronId", description = "ID of the patron borrowing the book", example = "1")
    }
  )
  public ResponseEntity<String> returnBook(
      @PathVariable Long bookId,
      @PathVariable Long patronId) {

    boolean isReturned = borrowingService.returnBook(bookId, patronId);
    if (isReturned) {
      return ResponseEntity.ok("Book returned successfully");
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Unable to return the book");
    }
  }
}

