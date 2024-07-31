package cc.maids.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatronDTO {

  private Long id;

  @Schema(example = "Patron Name")
  @NotBlank(message = "Name cannot be blank")
  private String name;

  @Schema(example = "ahmed@maids.cc")
  @NotBlank(message = "Contact information cannot be blank")
  @Email(message = "Contact information must be a valid email address")
  private String contactInformation;


}

