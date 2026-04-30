package com.michael.biblioteca.mapper;

import com.michael.biblioteca.dto.reporte.LibroAutorReporteDTO;
import com.michael.biblioteca.entity.LibroEntity;
import java.util.List;

public class LibroAutorMapper {

  public static List<LibroAutorReporteDTO> toReporteDTO(List<LibroEntity> lista) {

    return lista.stream().map(libro -> {
      LibroAutorReporteDTO dto = new LibroAutorReporteDTO();

      dto.setAutor(
        libro.getAutor() != null
        ? libro.getAutor().getNombre()
        : "Sin autor"
      );
      dto.setTitulo(libro.getTitulo());
      dto.setFechaPublicacion(libro.getFechaPublicacion());

      return dto;
    }).toList();
  }
}
