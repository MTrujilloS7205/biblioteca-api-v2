package com.michael.biblioteca.dto.reporte;

import com.michael.biblioteca.util.pdf.annotation.PdfColumn;
import java.time.LocalDate;
import lombok.Setter;

@Setter
public class LibroAutorReporteDTO {
  @PdfColumn(header = "Autor", order = 1, width = 4)
  private String autor;

  @PdfColumn(header = "Libro", order = 2, width = 4)
  private String titulo;

  @PdfColumn(header = "Fecha", order = 3, width = 2)
  private LocalDate fechaPublicacion;
}
