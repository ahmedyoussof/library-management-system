package cc.maids.library.repository;

import cc.maids.library.model.Patron;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PatronRepository extends JpaRepository<Patron, Long> {
}


