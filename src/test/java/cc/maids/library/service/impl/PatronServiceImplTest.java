package cc.maids.library.service.impl;

import cc.maids.library.exception.ResourceNotFoundException;
import cc.maids.library.model.Patron;
import cc.maids.library.repository.PatronRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class PatronServiceImplTest {

  @Mock
  private PatronRepository patronRepository;

  @InjectMocks
  private PatronServiceImpl patronService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void addPatron() {
    Patron patron = new Patron();
    when(patronRepository.save(any(Patron.class))).thenReturn(patron);
    assertEquals(patron, patronService.addPatron(patron));
  }

  @Test
  void getAllPatrons() {
    when(patronRepository.findAll()).thenReturn(List.of(new Patron()));
    assertEquals(1, patronService.getAllPatrons().size());
  }

  @Test
  void getPatronById() {
    Patron patron = new Patron();
    when(patronRepository.findById(anyLong())).thenReturn(Optional.of(patron));
    assertEquals(patron, patronService.getPatronById(1L));
  }

  @Test
  void getPatronByIdNotFound() {
    when(patronRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class, () -> patronService.getPatronById(1L));
  }

  @Test
  void updatePatron() {
    Patron patron = new Patron();
    when(patronRepository.existsById(anyLong())).thenReturn(true);
    when(patronRepository.save(any(Patron.class))).thenReturn(patron);
    assertEquals(patron, patronService.updatePatron(1L, patron));
  }

  @Test
  void updatePatronNotFound() {
    when(patronRepository.existsById(anyLong())).thenReturn(false);
    assertThrows(ResourceNotFoundException.class, () -> patronService.updatePatron(1L, new Patron()));
  }

  @Test
  void deletePatron() {
    when(patronRepository.existsById(anyLong())).thenReturn(true);
    doNothing().when(patronRepository).deleteById(anyLong());
    assertDoesNotThrow(() -> patronService.deletePatron(1L));
  }

  @Test
  void deletePatronNotFound() {
    when(patronRepository.existsById(anyLong())).thenReturn(false);
    assertThrows(ResourceNotFoundException.class, () -> patronService.deletePatron(1L));
  }
}
