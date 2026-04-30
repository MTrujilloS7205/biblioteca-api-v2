package com.michael.biblioteca.service;

import com.michael.biblioteca.dto.request.LibroRequestDTO;
import com.michael.biblioteca.dto.response.LibroResponseDTO;
import com.michael.biblioteca.entity.LibroEntity;
import java.util.List;

public interface LibroService {

  List<LibroResponseDTO> listarLibros();
  
  List<LibroEntity> listarLibrosEntity();

  LibroResponseDTO guardarLibro(LibroRequestDTO dto);

  LibroResponseDTO obtenerLibro(Long id);

  LibroResponseDTO modificarLibro(Long id, LibroRequestDTO dto);

  void eliminarLibro(Long id);

  List<LibroResponseDTO> listarLibrosPorAutor(Long autorId);

}
