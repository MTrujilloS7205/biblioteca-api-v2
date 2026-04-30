package com.michael.biblioteca.exception;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

  // Método privado para centralizar la creación de la respuesta
  private ResponseEntity<ErrorResponse> crearRespuesta(int status, String error, String mensaje, HttpServletRequest request) {
    ErrorResponse response = new ErrorResponse(
      LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
      status,
      error,
      mensaje,
      request.getRequestURI()
    );
    return ResponseEntity.status(status).body(response);
  }

  /* ======================= ERROR 400 =======================================*/
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> manejar400(IllegalArgumentException ex,
    HttpServletRequest request) {

    log.warn("Solicitud inválida: {}", ex.getMessage());
    return crearRespuesta(400, "Bad Request", ex.getMessage(), request);
  }

  /* ======================= ERROR 404 =======================================*/
  @ExceptionHandler(RecursoNoEncontradoException.class)
  public ResponseEntity<ErrorResponse> manejar404(RecursoNoEncontradoException ex,
    HttpServletRequest request) {

    log.warn("Recurso no encontrado: {}", ex.getMessage());
    return crearRespuesta(404, "Not Found", ex.getMessage(), request);
  }

  /* ======================= ERROR 409 =======================================*/
  @ExceptionHandler(RecursoDuplicadoException.class)
  public ResponseEntity<ErrorResponse> manejar409(RecursoDuplicadoException ex,
    HttpServletRequest request) {

    log.warn("Conflicto de datos: {}", ex.getMessage());
    return crearRespuesta(409, "Conflict", ex.getMessage(), request);
  }

  /* ======================= ERROR 400 =======================================*/
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponse> handleTypeMismatch(
    MethodArgumentTypeMismatchException ex,
    HttpServletRequest request) {

    log.warn("Dato inválido: {}", ex.getMessage());
    String campo = ex.getName();
    return crearRespuesta(400, "Bad Request", "Valor inválido para: " + campo, request);
  }

  /* ======================= ERROR 400 =======================================*/
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleInvalidJson(
    HttpMessageNotReadableException ex,
    HttpServletRequest request) {

    String mensajeLog = "JSON mal formado";
    String mensajeUsuario = "El JSON enviado es inválido";

    Throwable causa = ex.getCause();
    if (causa != null) {
      mensajeLog = causa.getMessage();

      if (mensajeLog.contains("Cannot construct instance")) {
        mensajeUsuario = "Estructura de JSON incorrecta";
      }
    }

    log.warn("Error JSON: {}", mensajeLog);

    return crearRespuesta(400, "Bad Request", mensajeUsuario, request);
  }

  /* ======================= ERROR 500 =======================================*/
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> manejar500(Exception ex,
    HttpServletRequest request) {

    log.error("Error inesperado", ex);
    return crearRespuesta(500, "Internal Server Error", "Error interno del servidor", request);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidation(
    MethodArgumentNotValidException ex,
    HttpServletRequest request) {

    String mensaje = ex.getBindingResult()
      .getFieldErrors()
      .stream()
      .map(err -> err.getField() + ": " + err.getDefaultMessage())
      .findFirst()
      .orElse("Error de validación");

    log.warn("Validación fallida: {}", mensaje);

    return crearRespuesta(400, "Bad Request", mensaje, request);
  }

  /* ======================= ERROR CON FAVICON ===============================*/
  @ExceptionHandler(NoResourceFoundException.class)
  public void ignorarFavicon() {
    // no hacer nada
  }
}
