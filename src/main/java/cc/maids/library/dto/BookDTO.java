package cc.maids.library.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

  @Schema(hidden = true)
  private Long id;

  @Schema(example = "Book Title")
  @NotBlank(message = "Title can not be blank")
  private String title;

  @Schema(example = "Book Author")
  @NotBlank(message = "Author can not be blank")
  private String author;

  @Schema(example = "2021")
  @NotNull(message = "Publication year can not be Null")
  private Integer publicationYear;

  @Schema(example = "978-1-56619-909-4")
  @NotBlank(message = "Isbn can not be blank")
  private String isbn;
}

