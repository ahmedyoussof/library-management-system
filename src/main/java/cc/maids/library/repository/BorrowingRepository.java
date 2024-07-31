package cc.maids.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import cc.maids.library.model.BorrowingRecord;

public interface BorrowingRepository extends JpaRepository<BorrowingRecord, Long> {

  Boolean existsByBookIdAndReturnDateIsNull(Long bookId);

  Optional<BorrowingRecord> findByBookIdAndPatronIdAndReturnDateIsNull(Long bookId, Long patronId);
}
