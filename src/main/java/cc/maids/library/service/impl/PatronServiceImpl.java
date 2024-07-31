package cc.maids.library.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import cc.maids.library.exception.ResourceNotFoundException;
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

  @Cacheable(value = "patrons", key = "#id")
  @Transactional(readOnly = true)
  @Override
  public Patron getPatronById(Long id) {
    return patronRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Patron with id: " + id + " not found"));
  }

  @CachePut(value = "patrons", key = "#id")
  @Transactional
  @Override
  public Patron updatePatron(Long id, Patron patron) {
    if (patronRepository.existsById(id)) {
      patron.setId(id);
      return patronRepository.save(patron);
    }
    throw new ResourceNotFoundException("Patron not found with id " + id);
  }

  @CacheEvict(value = "patrons", key = "#id")
  @Transactional
  @Override
  public void deletePatron(Long id) {
    if (patronRepository.existsById(id)) {
      patronRepository.deleteById(id);
    } else {
      throw new ResourceNotFoundException("Patron not found with id " + id);
    }
  }

}

