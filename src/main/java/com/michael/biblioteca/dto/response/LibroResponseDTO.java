package com.michael.biblioteca.dto.response;

import lombok.Data;
import java.time.LocalDate;

@Data
public class LibroResponseDTO {

  private Long id;
  private String titulo;
  private String isbn;
  private LocalDate fechaPublicacion;
  private Boolean estado;

  private Long autorId;       // 🔥 necesario
  private String autorNombre; // 🔥 para mostrar

}