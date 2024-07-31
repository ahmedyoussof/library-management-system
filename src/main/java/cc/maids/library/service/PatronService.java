package cc.maids.library.service;

import java.util.List;

import cc.maids.library.model.Patron;

public interface PatronService {

  List<Patron> getAllPatrons();

  Patron addPatron(Patron patron);

  Patron getPatronById(Long id);

  Patron updatePatron(Long id, Patron book);

  void deletePatron(Long id);

}
