package com.michael.biblioteca.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;

@Data
public class LibroRequestDTO {

  @NotBlank(message = "El título es obligatorio")
  @Size(max = 150, message = "El título no puede superar 150 caracteres")
  private String titulo;

  @NotBlank(message = "El ISBN es obligatorio")
  @Size(max = 30)
  private String isbn;

  private LocalDate fechaPublicacion;

  private Boolean estado;

  private Long autorId; // 🔥 CLAVE

}