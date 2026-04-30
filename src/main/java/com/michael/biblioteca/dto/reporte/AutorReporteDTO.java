package com.michael.biblioteca.dto.reporte;

import com.itextpdf.layout.properties.TextAlignment;
import com.michael.biblioteca.util.pdf.annotation.PdfColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AutorReporteDTO {

  @PdfColumn(header = "ID", order = 1, width = 1, align = TextAlignment.CENTER)
  private Long id;

  @PdfColumn(header = "Nombre", order = 2, width = 4)
  private String nombre;

  @PdfColumn(header = "Nacionalidad", order = 3, width = 3)
  private String nacionalidad;

  @PdfColumn(header = "Estado", order = 4, width = 2, align = TextAlignment.CENTER)
  private Boolean estado;
}
