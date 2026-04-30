package com.michael.biblioteca.mapper;

import com.michael.biblioteca.dto.reporte.LibroReporteDTO;
import com.michael.biblioteca.dto.request.LibroRequestDTO;
import com.michael.biblioteca.dto.response.LibroResponseDTO;
import com.michael.biblioteca.entity.LibroEntity;
import java.util.List;

public class LibroMapper {

  /* ===================== DTO → ENTITY ==================================== */
  public static LibroEntity toEntity(LibroRequestDTO dto) {

    if (dto == null) {
      return null;
    }

    LibroEntity entity = new LibroEntity();

    entity.setTitulo(dto.getTitulo());
    entity.setIsbn(dto.getIsbn());
    entity.setFechaPublicacion(dto.getFechaPublicacion());
    entity.setEstado(dto.getEstado());

    // ⚠️ NO seteamos autor aquí
    return entity;
  }

  /* ===================== ENTITY → DTO ==================================== */
  public static LibroResponseDTO toDTO(LibroEntity entity) {

    if (entity == null) {
      return null;
    }

    LibroResponseDTO dto = new LibroResponseDTO();

    dto.setId(entity.getId());
    dto.setTitulo(entity.getTitulo());
    dto.setIsbn(entity.getIsbn());
    dto.setFechaPublicacion(entity.getFechaPublicacion());
    dto.setEstado(entity.isEstado());

    // 🔥 aquí sí usamos autor
    if (entity.getAutor() != null) {
      dto.setAutorId(entity.getAutor().getId());
      dto.setAutorNombre(entity.getAutor().getNombre());
    }

    return dto;
  }

  /* ===================== PARA ACTUALIZAR ================================== */
  public static void updateEntity(LibroEntity entity, LibroRequestDTO dto) {
    entity.setTitulo(dto.getTitulo());
    entity.setIsbn(dto.getIsbn());
    entity.setFechaPublicacion(dto.getFechaPublicacion());
    entity.setEstado(dto.getEstado());

  }

  /* ===================== ENTITY → REPORTE DTO ============================ */
  public static LibroReporteDTO toReporteDTO(LibroEntity entity) {

    if (entity == null) {
      return null;
    }

    return new LibroReporteDTO(
      entity.getTitulo(),
      entity.getId(),
      entity.isEstado(),
      entity.getAutor() != null
      ? entity.getAutor().getNombre()
      : null
    );
  }

  /* ===================== LISTA REPORTE ==================================== */
  public static List<LibroReporteDTO> toReporteDTOList(List<LibroEntity> lista) {
    return lista.stream()
      .map(LibroMapper::toReporteDTO)
      .toList();
  }
}
