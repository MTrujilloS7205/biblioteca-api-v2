package com.michael.biblioteca.service;

import com.michael.biblioteca.dto.request.AutorRequestDTO;
import com.michael.biblioteca.dto.response.AutorResponseDTO;
import com.michael.biblioteca.entity.AutorEntity;
import java.util.List;

public interface AutorService {

  List<AutorResponseDTO> listarAutores();
  
  public List<AutorEntity> listarAutoresEntity();

  AutorResponseDTO guardarAutor(AutorRequestDTO autorRequestDTO);

  AutorResponseDTO obtenerAutor(Long id);

  AutorResponseDTO modificarAutor(Long id, AutorRequestDTO autorRequestDTO);

  void eliminarAutor(Long id);
}
