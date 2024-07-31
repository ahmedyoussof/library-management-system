package cc.maids.library.controller;

import cc.maids.library.config.TestSecurityConfig;
import cc.maids.library.service.BorrowingService;

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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BorrowingController.class)
@Import(TestSecurityConfig.class)
class BorrowingControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BorrowingService borrowingService;

  @Test
  void testBorrowBook() throws Exception {
    Mockito.when(borrowingService.borrowBook(Mockito.anyLong(), Mockito.anyLong())).thenReturn(true);

    mockMvc.perform(MockMvcRequestBuilders.post("/api/borrow/1/patron/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("Book borrowed successfully"));
  }

  @Test
  void testReturnBook() throws Exception {
    Mockito.when(borrowingService.returnBook(Mockito.anyLong(), Mockito.anyLong())).thenReturn(true);

    mockMvc.perform(MockMvcRequestBuilders.put("/api/borrow/return/1/patron/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("Book returned successfully"));
  }
}
