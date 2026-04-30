package com.michael.biblioteca.mapper;

import com.michael.biblioteca.dto.reporte.AutorReporteDTO;
import com.michael.biblioteca.dto.reporte.LibroReporteDTO;
import com.michael.biblioteca.dto.request.AutorRequestDTO;
import com.michael.biblioteca.dto.response.AutorResponseDTO;
import com.michael.biblioteca.entity.AutorEntity;
import com.michael.biblioteca.entity.LibroEntity;
import java.util.List;

public class AutorMapper {

  /* ===================== DTO → ENTITY ===================== */
  public static AutorEntity toEntity(AutorRequestDTO dto) {

    if (dto == null) {
      return null;
    }

    AutorEntity autor = new AutorEntity();
    autor.setNombre(dto.getNombre());
    autor.setNacionalidad(dto.getNacionalidad());
    autor.setEstado(dto.getEstado());

    return autor;
  }

  /* ===================== ENTITY → DTO ===================== */
  public static AutorResponseDTO toDTO(AutorEntity entity) {

    if (entity == null) {
      return null;
    }

    AutorResponseDTO dto = new AutorResponseDTO();
    dto.setId(entity.getId());
    dto.setNombre(entity.getNombre());
    dto.setNacionalidad(entity.getNacionalidad());
    dto.setEstado(entity.isEstado());

    return dto;
  }

  /* ===================== PARA ACTUALIZAR ===================== */
  public static void updateEntity(AutorEntity entity, AutorRequestDTO dto) {
    entity.setNombre(dto.getNombre());
    entity.setNacionalidad(dto.getNacionalidad());
    entity.setEstado(dto.getEstado());
  }

  /* ===================== ENTITY → REPORTE DTO ============================ */
  public static AutorReporteDTO toReporteDTO(AutorEntity entity) {
    if (entity == null) {
      return null;
    }

    return new AutorReporteDTO(
      entity.getId(),
      entity.getNombre(),
      entity.getNacionalidad(),
      entity.isEstado()
    );
  }
  
  /* ===================== LISTA REPORTE ==================================== */
  public static List<AutorReporteDTO> toReporteDTOList(List<AutorEntity> lista) {
    return lista.stream()
      .map(AutorMapper::toReporteDTO)
      .toList();
  }

}
