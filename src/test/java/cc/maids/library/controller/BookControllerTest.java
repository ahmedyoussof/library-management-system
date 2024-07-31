package cc.maids.library.controller;

import cc.maids.library.config.TestSecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import cc.maids.library.dto.BookDTO;
import cc.maids.library.model.Book;
import cc.maids.library.service.BookService;
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

@WebMvcTest(BookController.class)
@Import(TestSecurityConfig.class)
class BookControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BookService bookService;

  @Autowired
  private ObjectMapper objectMapper;

  private BookDTO bookDTO;

  private Book book;

  @BeforeEach
  void setup() {
    bookDTO = BookDTO.builder()
        .author("Author")
        .title("Title")
        .isbn("ISBN")
        .publicationYear(2021)
        .build();
    book = new Book();
    book.setId(1L);
  }

  @Test
  void testAddBook() throws Exception {
    Mockito.when(bookService.addBook(Mockito.any(Book.class))).thenReturn(book);

    mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(bookDTO)))
        .andExpect(status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
  }

  @Test
  void testGetAllBooks() throws Exception {
    Mockito.when(bookService.getAllBooks()).thenReturn(Collections.singletonList(book));

    mockMvc.perform(MockMvcRequestBuilders.get("/api/books")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists());
  }

  @Test
  void testGetBookById() throws Exception {
    Mockito.when(bookService.getBookById(Mockito.anyLong())).thenReturn(book);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/books/1")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
  }

  @Test
  void testUpdateBook() throws Exception {
    Mockito.when(bookService.updateBook(Mockito.anyLong(), Mockito.any(Book.class))).thenReturn(book);

    mockMvc.perform(MockMvcRequestBuilders.put("/api/books/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(bookDTO)))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
  }

  @Test
  void testDeleteBook() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/1"))
        .andExpect(status().isNoContent());
  }
}
