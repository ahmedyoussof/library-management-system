package cc.maids.library.controller;

import cc.maids.library.config.TestSecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import cc.maids.library.dto.PatronDTO;
import cc.maids.library.model.Patron;
import cc.maids.library.service.PatronService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatronController.class)
@Import(TestSecurityConfig.class)
class PatronControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PatronService patronService;

  @Autowired
  private ObjectMapper objectMapper;

  private PatronDTO patronDTO;

  private Patron patron;

  @BeforeEach
  void setup() {
    patronDTO = PatronDTO.builder()
        .name("Patron Name")
        .contactInformation("patron@example.com")
        .build();
    patron = new Patron();
    patron.setId(1L);
  }

  @Test
  void testAddPatron() throws Exception {
    Mockito.when(patronService.addPatron(Mockito.any(Patron.class))).thenReturn(patron);

    mockMvc.perform(MockMvcRequestBuilders.post("/api/patrons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(patronDTO)))
        .andExpect(status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
  }

  @Test
  void testGetAllPatrons() throws Exception {
    Mockito.when(patronService.getAllPatrons()).thenReturn(Collections.singletonList(patron));

    mockMvc.perform(MockMvcRequestBuilders.get("/api/patrons")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists());
  }

  @Test
  void testGetPatronById() throws Exception {
    Mockito.when(patronService.getPatronById(Mockito.anyLong())).thenReturn(patron);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/patrons/1")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
  }

  @Test
  void testUpdatePatron() throws Exception {
    Mockito.when(patronService.updatePatron(Mockito.anyLong(), Mockito.any(Patron.class))).thenReturn(patron);

    mockMvc.perform(MockMvcRequestBuilders.put("/api/patrons/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(patronDTO)))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
  }

  @Test
  void testDeletePatron() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/patrons/1"))
        .andExpect(status().isNoContent());
  }
}
