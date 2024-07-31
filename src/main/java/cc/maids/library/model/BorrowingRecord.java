package cc.maids.library.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class BorrowingRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDate borrowDate;

  private LocalDate returnDate;

  @ManyToOne
  @JoinColumn(name = "book_id", nullable = false)
  private Book book;

  @ManyToOne
  @JoinColumn(name = "patron_id", nullable = false)
  private Patron patron;

}

