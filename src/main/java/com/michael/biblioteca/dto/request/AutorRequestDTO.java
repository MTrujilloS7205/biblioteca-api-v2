package com.michael.biblioteca.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AutorRequestDTO {

  @NotBlank(message = "El nombre del autor es obligatorio")
  @Size(max = 50, message = "El nombre no puede tener mas de 50 caracteres")
  private String nombre;

  private String nacionalidad;

  private Boolean estado;

}
