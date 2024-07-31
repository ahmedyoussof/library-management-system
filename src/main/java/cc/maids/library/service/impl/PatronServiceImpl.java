package cc.maids.library.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import cc.maids.library.model.Patron;
import cc.maids.library.repository.PatronRepository;
import cc.maids.library.service.PatronService;

@Service
@RequiredArgsConstructor
public class PatronServiceImpl implements PatronService {

  private final PatronRepository patronRepository;


  @Transactional
  @Override
  public Patron addPatron(Patron patron) {
    return patronRepository.save(patron);
  }

  @Transactional(readOnly = true)
  @Override
  public List<Patron> getAllPatrons() {
    return patronRepository.findAll();
  }

}

