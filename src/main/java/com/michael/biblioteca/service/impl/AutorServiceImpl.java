package com.michael.biblioteca.service.impl;

import com.michael.biblioteca.dto.request.AutorRequestDTO;
import com.michael.biblioteca.dto.response.AutorResponseDTO;
import com.michael.biblioteca.entity.AutorEntity;
import com.michael.biblioteca.exception.RecursoDuplicadoException;
import com.michael.biblioteca.exception.RecursoNoEncontradoException;
import com.michael.biblioteca.mapper.AutorMapper;
import com.michael.biblioteca.repository.AutorRepository;
import com.michael.biblioteca.service.AutorService;
import static com.michael.biblioteca.util.StringUtils.limpiarTexto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class AutorServiceImpl implements AutorService {

  private final AutorRepository autorRepository;

  /* /////////////// LISTA TODOS LOS AUTORES //////////////////////////////// */
  @Override
  public List<AutorResponseDTO> listarAutores() {
    log.info("Listando todos los autores");

    List<AutorEntity> autores = autorRepository.findAll();

    return autores.stream()
      .map(AutorMapper::toDTO)
      .toList();
  }

  /* /////////////// LISTA TODOS LOS AUTORES PARA EL REPORTE //////////////// */
  @Override
  public List<AutorEntity> listarAutoresEntity(){
    log.info("Listando todos los autores desde la entidad");

    List<AutorEntity> autores = autorRepository.findAll();

    log.info("Se encontraron {} autores desde la entidad", autores.size());

    return autores;
    
  }

  /* ////////////////////////////////////////////////////////////////////////// */
  @Override
  public AutorResponseDTO guardarAutor(AutorRequestDTO dto) {

    AutorEntity autorEntity = AutorMapper.toEntity(dto);

    String nombre = limpiarTexto(autorEntity.getNombre());
    if (nombre == null) {
      log.warn("Nombre de autor inválido");
      throw new IllegalArgumentException("El nombre del autor es obligatorio");
    }

    log.info("Intentando guardar autor con nombre '{}'", nombre);

    if (existeAutorPorNombre(nombre)) {
      log.warn("Nombre duplicado: '{}'", nombre);
      throw new RecursoDuplicadoException("El nombre del autor ya existe");
    }

    autorEntity.setNombre(nombre);

    String nacionalidad = limpiarTexto(autorEntity.getNacionalidad());
    autorEntity.setNacionalidad(nacionalidad);

    AutorEntity guardado = autorRepository.save(autorEntity);

    log.info("Autor guardado con ID {}", guardado.getId());

    return AutorMapper.toDTO(guardado);
  }

  /* ////////////////////////////////////////////////////////////////////////// */
  @Override
  public AutorResponseDTO obtenerAutor(Long id) {
    log.info("Obteniendo autor con ID {}", id);

    AutorEntity autor = obtenerAutorEntity(id);

    return AutorMapper.toDTO(autor);
  }

  /* ////////////////////////////////////////////////////////////////////////// */
  @Override
  public AutorResponseDTO modificarAutor(Long id, AutorRequestDTO dto) {

    log.info("Modificando autor con ID {}", id);

    AutorEntity autor = obtenerAutorEntity(id);

    String nombre = limpiarTexto(dto.getNombre());
    if (nombre == null) {
      log.warn("Nombre inválido");
      throw new IllegalArgumentException("El nombre del autor es obligatorio");
    }

    log.info("Intentando modificar con nombre '{}'", nombre);

    if (!autor.getNombre().equalsIgnoreCase(nombre)
      && existeAutorPorNombre(nombre)) {
      log.warn("Nombre duplicado: '{}'", nombre);
      throw new RecursoDuplicadoException("El nombre del autor ya existe");
    }

    autor.setNombre(nombre);
    autor.setNacionalidad(limpiarTexto(dto.getNacionalidad()));
    autor.setEstado(dto.getEstado());

    AutorEntity modificado = autorRepository.save(autor);

    log.info("Autor modificado ID {}", modificado.getId());

    return AutorMapper.toDTO(modificado);
  }

  /* ////////////////////////////////////////////////////////////////////////// */
  @Override
  public void eliminarAutor(Long id) {
    log.info("Eliminando autor con ID {}", id);

    AutorEntity autor = obtenerAutorEntity(id);

    autorRepository.delete(autor);

    log.info("Autor eliminado ID {}", id);
  }

  /* ////////////////////////////////////////////////////////////////////////// */
  private AutorEntity obtenerAutorEntity(Long id) {
    return autorRepository.findById(id)
      .orElseThrow(() -> {
        log.error("Autor con ID {} no encontrado", id);
        return new RecursoNoEncontradoException("Autor no encontrado");
      });
  }

  /* ////////////////////////////////////////////////////////////////////////// */
  private boolean existeAutorPorNombre(String nombre) {
    boolean existe = autorRepository.existsByNombreIgnoreCase(nombre);
    log.info("Validando existencia de '{}': {}", nombre, existe);
    return existe;
  }
}
