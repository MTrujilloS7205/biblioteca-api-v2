package com.michael.biblioteca.dto.reporte;

import com.itextpdf.layout.properties.TextAlignment;
import com.michael.biblioteca.util.pdf.annotation.PdfColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LibroReporteDTO {

  @PdfColumn(header = "Título", order = 1, width = 6)
  private String titulo;

  @PdfColumn(header = "ID", order = 2, width = 1, align = TextAlignment.RIGHT)
  private Long id;

  @PdfColumn(header = "Estado", order = 3, width = 2, align = TextAlignment.CENTER)
  private Boolean estado;

  @PdfColumn(header = "Autor", order = 4, width = 5)
  private String autor;
}