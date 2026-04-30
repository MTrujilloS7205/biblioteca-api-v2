package com.michael.biblioteca.service.impl;

import com.michael.biblioteca.dto.request.LibroRequestDTO;
import com.michael.biblioteca.dto.response.LibroResponseDTO;
import com.michael.biblioteca.entity.AutorEntity;
import com.michael.biblioteca.entity.LibroEntity;
import com.michael.biblioteca.exception.RecursoDuplicadoException;
import com.michael.biblioteca.exception.RecursoNoEncontradoException;
import com.michael.biblioteca.mapper.LibroMapper;
import com.michael.biblioteca.repository.AutorRepository;
import com.michael.biblioteca.repository.LibroRepository;
import com.michael.biblioteca.service.LibroService;
import static com.michael.biblioteca.util.StringUtils.limpiarTexto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class LibroServiceImpl implements LibroService {

  private final AutorRepository autorRepository;
  private final LibroRepository libroRepository;

  /* /////////////////// LISTA TODOS LOS LIBROS //////////////////////////// */
  @Override
  public List<LibroResponseDTO> listarLibros() {

    log.info("Listando todos los libros");

    List<LibroEntity> libros = libroRepository.findAll();

    log.info("Se encontraron {} libros", libros.size());

    return libros.stream()
      .map(LibroMapper::toDTO)
      .toList();
  }
  
  /* /////////////////// LISTA TODOS LOS LIBROS - PARA REPORTE ////////////// */
  @Override
  public List<LibroEntity> listarLibrosEntity(){
    log.info("Listando todos los libros de la Entidad");

    List<LibroEntity> libros = libroRepository.findAll();

    log.info("Se encontraron {} libros desde la Entidad", libros.size());

    return libros;
  }

  /* /////////////////// GUARDAR LIBRO NUEVO //////////////////////////////// */
  @Override
  public LibroResponseDTO guardarLibro(LibroRequestDTO dto) {

    String titulo = limpiarTexto(dto.getTitulo());
    if (titulo == null) {
      log.warn("Título del libro inválido");
      throw new IllegalArgumentException("El título del libro es obligatorio");
    }

    String isbn = limpiarTexto(dto.getIsbn());
    if (isbn == null) {
      log.warn("ISBN del libro inválido");
      throw new IllegalArgumentException("El ISBN del libro es obligatorio");
    }
    log.info("Intentando guardar libro: '{}' - ISBN: {}", titulo, isbn);

    if (existeTitulo(titulo)) {
      log.warn("No se pudo guardar el libro, título duplicado: '{}'", titulo);
      throw new RecursoDuplicadoException("El título del libro ya existe");
    }
    if (existeIsbn(isbn)) {
      log.warn("No se pudo guardar el libro, ISBN duplicado: '{}'", isbn);
      throw new RecursoDuplicadoException("El código ISBN del libro ya existe");
    }

    LibroEntity libro = LibroMapper.toEntity(dto);
    libro.setTitulo(titulo);
    libro.setIsbn(isbn);

    if (dto.getAutorId() != null) {
      AutorEntity autor = obtenerAutorEntity(dto.getAutorId());
      libro.setAutor(autor);
    } else {
      libro.setAutor(null);
    }

    LibroEntity guardado = libroRepository.save(libro);
    log.info("Libro guardado con ID {}", guardado.getId());
    return LibroMapper.toDTO(guardado);
  }

  /* /////////////////// OBTENER LIBRO POR ID /////////////////////////////// */
  @Override
  public LibroResponseDTO obtenerLibro(Long id) {

    log.info("Obteniendo libro con ID {}", id);

    LibroEntity libro = obtenerLibroEntity(id);

    return LibroMapper.toDTO(libro);
  }

  /* /////////////////// MODIFICA LIBRO ///////////////////////////////////// */
  @Override
  public LibroResponseDTO modificarLibro(Long id, LibroRequestDTO dto) {
    log.info("Modificando libro con ID {}", id);
    LibroEntity libro = obtenerLibroEntity(id);

    String titulo = limpiarTexto(dto.getTitulo());
    if (titulo == null) {
      log.warn("Título del libro inválido");
      throw new IllegalArgumentException("El título del libro es obligatorio");
    }

    String isbn = limpiarTexto(dto.getIsbn());
    if (isbn == null) {
      log.warn("ISBN del libro inválido");
      throw new IllegalArgumentException("El ISBN del libro es obligatorio");
    }

    if (!libro.getTitulo().equalsIgnoreCase(titulo)
      && existeTitulo(titulo)) {
      log.warn("No se pudo modificar el libro, título duplicado: '{}'", titulo);
      throw new RecursoDuplicadoException("El título del libro ya existe");
    }

    if (!libro.getIsbn().equals(isbn)
      && existeIsbn(isbn)) {
      log.warn("No se pudo modificar el libro, ISBN duplicado: {}", isbn);
      throw new RecursoDuplicadoException("El ISBN del libro ya existe");
    }

    libro.setTitulo(titulo);
    libro.setIsbn(isbn);
    libro.setFechaPublicacion(dto.getFechaPublicacion());
    libro.setEstado(dto.getEstado());

    if (dto.getAutorId() != null) {
      AutorEntity autor = obtenerAutorEntity(dto.getAutorId());

      libro.setAutor(autor);
    } else {
      libro.setAutor(null);
    }

    LibroEntity modificado = libroRepository.save(libro);
    log.info("Libro modificado con ID {}", modificado.getId());

    return LibroMapper.toDTO(modificado);
  }

  /* //////////////////////////////////////////////////////////////////////// */
  @Override
  public void eliminarLibro(Long id) {
    log.info("Eliminando libro con ID {}", id);
    LibroEntity libro = obtenerLibroEntity(id);
    libroRepository.delete(libro);
    log.info("Libro eliminado con ID {}", id);
  }

  /* //////////////////////////////////////////////////////////////////////// */
  @Override
  public List<LibroResponseDTO> listarLibrosPorAutor(Long autorId) {
    log.info("Listando libros del autor con ID {}", autorId);
    AutorEntity autor = obtenerAutorEntity(autorId);

    List<LibroEntity> libros = libroRepository.findByAutorId(autor.getId());
    log.info("Se encontraron {} libros para el autor '{}'", libros.size(), autor.getNombre());
    return libros.stream()
      .map(LibroMapper::toDTO)
      .toList();
  }

  /* //////////////////////////////////////////////////////////////////////// */
  private boolean existeIsbn(String isbn) {
    boolean existe = libroRepository.existsByIsbn(isbn);
    log.info("Validando la existencia de ISBN '{}': {}", isbn, existe);
    return existe;
  }

  /* //////////////////////////////////////////////////////////////////////// */
  private boolean existeTitulo(String titulo) {
    boolean existe = libroRepository.existsByTituloIgnoreCase(titulo);
    log.info("Validando la existencia del título '{}': {}", titulo, existe);
    return existe;
  }

  /* //////////////////////////////////////////////////////////////////////// */
  private LibroEntity obtenerLibroEntity(Long id) {
    return libroRepository.findById(id)
      .orElseThrow(() -> {
        log.error("Libro no encontrado con ID {}", id);
        return new RecursoNoEncontradoException("Libro no encontrado");
      });
  }

  /* //////////////////////////////////////////////////////////////////////// */
  private AutorEntity obtenerAutorEntity(Long autorId) {
    return autorRepository.findById(autorId)
      .orElseThrow(() -> {
        log.error("Autor no encontrado con ID {}", autorId);
        return new RecursoNoEncontradoException("El autor no existe");
      });
  }
}
